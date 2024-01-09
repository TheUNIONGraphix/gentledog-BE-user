package gentledog.members.orders.orders.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gentledog.members.orders.orders.dto.out.members.GetOrdersDetailOutDto;
import gentledog.members.orders.orders.dto.out.members.GetOrdersSummaryOutDto;
import gentledog.members.orders.orders.entity.OrdersDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static gentledog.members.orders.orders.entity.QVendorsOrdersList.vendorsOrdersList;

@Slf4j
@RequiredArgsConstructor
public class VendorsOrdersListRepositoryImpl implements VendorsOrdersListRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final DeliveryRepository deliveryRepository;
    private final OrdersDetailRepository ordersDetailRepository;

    @Override
    public List<GetOrdersSummaryOutDto> findByFilter(String membersEmail, Long groupId) {
        // 주문서 요약 정보 조회
        List<GetOrdersSummaryOutDto> results = queryFactory
                .select(
                        Projections.fields(
                                GetOrdersSummaryOutDto.class,
                                vendorsOrdersList.id.as("vendorsOrderId"),
                                vendorsOrdersList.groupId,
                                vendorsOrdersList.orderNumber,
                                vendorsOrdersList.brandName,
                                vendorsOrdersList.brandLogoImageUrl,
                                vendorsOrdersList.vendorEmail,
                                vendorsOrdersList.createdAt,
                                vendorsOrdersList.totalPrice,
                                vendorsOrdersList.vendorsOrderListStatus
                        )
                )
                .from(vendorsOrdersList)
                .where(
                        ltGroupId(groupId, membersEmail),
                        vendorsOrdersList.orderDeleteStatus.eq(1)
                )
                .orderBy(vendorsOrdersList.id.desc())
                .fetch();

        // 연관된 OrderDetail 엔터티를 가져와서 DTO에 설정 및 나머지 로직 처리
        results.forEach(result -> {
            // 하나씩 builder 패턴으로 생성해서 넣어줘야 함
            List<OrdersDetail> ordersDetails = ordersDetailRepository.findByVendorsOrdersListIdOrderByCreatedAtDesc(result.getVendorsOrderId());
            List<GetOrdersDetailOutDto> ordersDetailOutDtoList = new ArrayList<>();
            ordersDetails.forEach(orderDetail -> {
                GetOrdersDetailOutDto getOrdersDetailOutDto = GetOrdersDetailOutDto.builder()
                        .productId(orderDetail.getProductId())
                        .productName(orderDetail.getProductName())
                        .productImageUrl(orderDetail.getProductImageUrl())
                        .productPrice(orderDetail.getProductPrice())
                        .productSize(orderDetail.getProductSize())
                        .productColor(orderDetail.getProductColor())
                        .productStock(orderDetail.getProductStock())
                        .productDiscountRate(orderDetail.getProductDiscountRate())
                        .couponId(orderDetail.getCouponId())
                        .couponDiscountPrice(orderDetail.getCouponDiscountPrice())
                        .build();
                ordersDetailOutDtoList.add(getOrdersDetailOutDto);
            });
            result.setOrderDetailList(ordersDetailOutDtoList);
//            result.updateProductImageUrl();
//            result.updateProductNameAndTotalCount();
//            result.updateVendorsOrderListStatusDescription();

            // 이미지 url을 가져와서 넣어준다.
            result.setProductImageUrl(ordersDetailOutDtoList.get(0).getProductImageUrl());

            // 배송 정보 조회
            result.setVendorsOrderListStatusDescription(result.getVendorsOrderListStatus().getDescription());

            // orderDetailList에서 첫 상품명과 List<OrderDetail>에서의 상품 수량을 합쳐서 productNameAndTotalCount 필드를 업데이트합니다.
            // orderDetailList.size()가 1보다 크면 외 개수 표시 (ex. 외 2개)
            if (ordersDetailOutDtoList.size() > 1) {
                result.setProductNameAndTotalCount(ordersDetailOutDtoList.get(0).getProductName() + " 외 " +
                        (ordersDetailOutDtoList.size() - 1) + "개");
            } else {
                result.setProductNameAndTotalCount(ordersDetailOutDtoList.get(0).getProductName());
            }

        });
        log.info("results: {}", results.get(0).getBrandLogoImageUrl());
        log.info("results: {}", results.get(0).getOrderDetailList().get(0).getProductImageUrl());
        return results;
    }
        /**
         * BooleanExpression을 사용하는 이유
         * 1) 가독성이 뛰어남
         * 2) null 반환 시 Where절에서 무시되기 때문에 안전
         * 3) and, or같은 메소드들을 이용해 BooleanExpression을 조합해 새로운 BooleanExpression를 만들 수 있어
         * 재사용성이 좋기 때문에 !
         *
         */

    // groupId가 null이 아니면 groupId가 가장 큰 값을 조회 하고
    // null이 아니라면 groupId가 가장 큰 값보다 작은 값 1개 조회

    private BooleanExpression ltGroupId(Long groupId, String membersEmail) {
        // null 이면 가장 membersEmail에서 groupId가 가장 큰 값 조회
        if (groupId == null) {
            return vendorsOrdersList.groupId.eq(
                    queryFactory
                            .select(vendorsOrdersList.groupId.max())
                            .from(vendorsOrdersList)
                            .where(vendorsOrdersList.membersEmail.eq(membersEmail))
                            .fetchOne()
            );
        } else {
            return vendorsOrdersList.groupId.eq(groupId);
        }
    }
}