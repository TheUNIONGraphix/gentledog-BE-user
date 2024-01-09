package gentledog.members.orders.orders.application.members;

import gentledog.members.orders.orders.dto.in.members.CreateOrdersDetailInDto;
import gentledog.members.orders.orders.dto.in.members.CreateOrdersInDto;
import gentledog.members.orders.orders.dto.in.members.CreateVendorsOrdersListInDto;
import gentledog.members.orders.orders.dto.out.members.GetOrdersNumberOutDto;
import gentledog.members.orders.orders.dto.out.members.GetVendorsOrdersOutDto;
import gentledog.members.orders.orders.dto.out.members.GetOrdersSummaryOutDto;
import gentledog.members.orders.orders.entity.Delivery;
import gentledog.members.orders.orders.entity.OrdersDetail;
import gentledog.members.orders.orders.entity.VendorsOrdersList;
import gentledog.members.orders.orders.entity.enums.DeliveryStatus;
import gentledog.members.orders.orders.entity.enums.VendorsOrderListStatus;
import gentledog.members.orders.orders.infrastructure.DeliveryRepository;
import gentledog.members.orders.orders.infrastructure.OrdersDetailRepository;
import gentledog.members.orders.orders.infrastructure.VendorsOrdersListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MembersOrdersServiceImple implements MembersOrdersService {

    private final ModelMapper modelMapper;
    private final OrdersDetailRepository ordersDetailRepository;
    private final VendorsOrdersListRepository vendorsOrdersListRepository;
    private final DeliveryRepository deliveryRepository;


    public String createdCode() {
        int leftLimit = 65; // alphabet 'A'
        int rightLimit = 90; // alphabet 'Z'
        int targetStringLength = 7;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i >=65) && (i <= 90))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public GetOrdersNumberOutDto createOrders(String membersEmail, CreateOrdersInDto createOrdersInDto) {
        log.info("createOrdersInDto : {}", createOrdersInDto);
        /**
         * 주문 들어올 시 생성(연월일(YYMMDD) + 영문자 7자리 예 : 201011A1B2C3) 중복체크
         */
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        // 랜덤 문자열 생성
        String randomString = createdCode();
        // 주문 번호 생성
        String orderNumber = formattedDate + randomString;
        // 주문번호 유효성 검사
        while (vendorsOrdersListRepository.existsByOrderNumber(orderNumber)) {
            randomString = createdCode();
            orderNumber = formattedDate + randomString;
        }

        // 최신 그룹 ID 조회
        VendorsOrdersList vendorsOrderList = vendorsOrdersListRepository.findMaxGroupId(membersEmail);
        Long groupId;

        // VendorsOrderList에 주문 내역이 있는지 확인
        if (vendorsOrderList == null) {
            groupId = 1L;
        } else if (vendorsOrderList.getCreatedAt().toLocalDate().equals(currentDate)) {
            groupId = vendorsOrderList.getGroupId();

        } else {
            groupId = vendorsOrderList.getGroupId()+1;
        }

        // 배송지 테이블 생성
        Delivery delivery = modelMapper.map(createOrdersInDto.getDeliveryOrders(), Delivery.class);
        delivery = delivery.toBuilder()
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        log.info("delivery : {}", delivery);
        deliveryRepository.save(delivery);

        for(CreateVendorsOrdersListInDto vendorsOrdersListInDto :
                createOrdersInDto.getVendorsOrdersList()) {
            log.info("vendorsOrderListInRequestDto getmembersname: {}", vendorsOrdersListInDto.getMembersName());
            log.info("vendorsOrderListInRequestDto getOrdersDetailList: {}", vendorsOrdersListInDto.getOrdersDetailList());
            List<CreateOrdersDetailInDto> ordersDetailInDtoList = vendorsOrdersListInDto.getOrdersDetailList();

            // 주문 상세 테이블 생성
            List<OrdersDetail> ordersDetails = new ArrayList<>();
            ordersDetailInDtoList.forEach(item -> {
                log.info("item : {}", item);
                OrdersDetail ordersDetail = modelMapper.map(item, OrdersDetail.class);
                log.info("orderDetail : {}", ordersDetail);
                ordersDetail = ordersDetail.toBuilder()
                        .build();
                ordersDetailRepository.save(ordersDetail);
                ordersDetails.add(ordersDetail);

            });
            log.info("orderDetails : {}", ordersDetails);

            // 벤더주문리스트 테이블 생성 후, 주문 상세 테이블을 업데이트하고 저장
            VendorsOrdersList vendorsOrdersList = modelMapper.map(vendorsOrdersListInDto, VendorsOrdersList.class);

            log.info("vendorsOrdersList : {}", vendorsOrdersList.getMembersName());
            vendorsOrdersList = vendorsOrdersList.toBuilder()
                    .groupId(groupId)
                    .membersEmail(membersEmail)
                    .orderNumber(orderNumber)
                    .vendorsOrderListStatus(VendorsOrderListStatus.READY)
                    .delivery(delivery)
                    .ordersDetailList(ordersDetails)
                    .orderDeleteStatus(1)
                    .build();

            log.info("vendorsOrdersList : {}", vendorsOrdersList.getMembersName());
            vendorsOrdersListRepository.save(vendorsOrdersList);

            // 주문 상세 테이블에 벤더주문리스트 테이블을 업데이트
            for (OrdersDetail item : ordersDetails) {
                item.updateVendorsOrderList(vendorsOrdersList);
                ordersDetailRepository.save(item);
            }
        }

        return GetOrdersNumberOutDto.builder()
                .orderNumber(orderNumber)
                .build();
    }

    // 주문 요약 조회
    @Override
    public GetVendorsOrdersOutDto getOrdersSummary(String membersEmail, Long groupId) {
        List<GetOrdersSummaryOutDto> getOrdersSummaryOutDto =
                vendorsOrdersListRepository.findByFilter(membersEmail, groupId);

        // vendorsOrderSummaryOutResponseDto 에서 GroupId는 모두 같기때문에 첫 값에서 가져온다.
        Long lastElement = getOrdersSummaryOutDto.get(0).getGroupId();

        VendorsOrdersList vendorsOrdersList = vendorsOrdersListRepository.
                findByNextGroupId(membersEmail, lastElement);

        // vendorsOrderList가 null이라면 nextGroupId는 null로 전달
        Long nextGroupId = vendorsOrdersList == null ? null : vendorsOrdersList.getGroupId();

        // vendorsOrderSummaryOutResponseDto의 마지막 요소의 cursorId를 nextCursorId로 전달
        return GetVendorsOrdersOutDto.builder()
                .ordersSummaryList(getOrdersSummaryOutDto)
                .nextGroupId(nextGroupId)
                .build();
    }

    /**
     * @param membersEmail
     * @param orderNumber
     */
    @Override
    public void deleteOrder(String membersEmail, String orderNumber) {
        List<VendorsOrdersList> vendorsOrdersList = vendorsOrdersListRepository.findByMembersEmailAndOrderNumber(membersEmail, orderNumber);

        vendorsOrdersList.forEach(VendorsOrdersList::updateOrderDeleteStatus);
    }
}