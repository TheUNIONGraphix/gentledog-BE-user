package gentledog.members.orders.orders.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gentledog.members.orders.orders.dto.out.members.OrderDetailOutResponseDto;
import gentledog.members.orders.orders.dto.out.members.VendorsOrderSummaryOutResponseDto;
import gentledog.members.orders.orders.entity.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static gentledog.members.orders.orders.entity.QVendorsOrderList.vendorsOrderList;

@Slf4j
@RequiredArgsConstructor
public class VendorsOrderListRepositoryImpl implements VendorsOrderListRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final DeliveryRepository deliveryRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<VendorsOrderSummaryOutResponseDto> findByFilter(String membersEmail, Long groupId) {
        // 주문서 요약 정보 조회
        List<VendorsOrderSummaryOutResponseDto> results = queryFactory
                .select(
                        Projections.fields(
                                VendorsOrderSummaryOutResponseDto.class,
                                vendorsOrderList.id.as("vendorsOrderId"),
                                vendorsOrderList.groupId,
                                vendorsOrderList.orderNumber,
                                vendorsOrderList.brandName,
                                vendorsOrderList.brandLogoImageUrl,
                                vendorsOrderList.vendorEmail,
                                vendorsOrderList.createdAt,
                                vendorsOrderList.totalPrice,
                                vendorsOrderList.vendorsOrderListStatus
                        )
                )
                .from(vendorsOrderList)
                .where(
                        ltGroupId(groupId, membersEmail),
                        vendorsOrderList.orderDeleteStatus.eq(1)
                )
                .orderBy(vendorsOrderList.id.desc())
                .fetch();

        // 연관된 OrderDetail 엔터티를 가져와서 DTO에 설정 및 나머지 로직 처리
        results.forEach(result -> {
            // 하나씩 builder 패턴으로 생성해서 넣어줘야 함
            List<OrderDetail> orderDetails = orderDetailRepository.findByVendorsOrderListIdOrderByCreatedAtDesc(result.getVendorsOrderId());
            List<OrderDetailOutResponseDto> orderDetailOutResponseDtoList = new ArrayList<>();
            orderDetails.forEach(orderDetail -> {
                OrderDetailOutResponseDto orderDetailOutResponseDto = OrderDetailOutResponseDto.builder()
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
                orderDetailOutResponseDtoList.add(orderDetailOutResponseDto);
            });
            result.setOrderDetailList(orderDetailOutResponseDtoList);
            result.updateProductImageUrl();
            result.updateProductNameAndTotalCount();
            result.updateVendorsOrderListStatusDescription();

        });

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
            return vendorsOrderList.groupId.eq(
                    queryFactory
                            .select(vendorsOrderList.groupId.max())
                            .from(vendorsOrderList)
                            .where(vendorsOrderList.membersEmail.eq(membersEmail))
                            .fetchOne()
            );
        } else {
            return vendorsOrderList.groupId.eq(groupId);
        }
    }
}