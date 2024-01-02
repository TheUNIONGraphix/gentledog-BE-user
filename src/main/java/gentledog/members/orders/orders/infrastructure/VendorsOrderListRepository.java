package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.entity.VendorsOrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendorsOrderListRepository extends JpaRepository<VendorsOrderList, Long>, VendorsOrderListRepositoryCustom {
    Boolean existsByOrderNumber(String orderNumber);

    @Query("select v from VendorsOrderList v " +
            "where v.membersEmail = :membersEmail " +
            "order by v.groupId desc " +
            "limit 1")
    VendorsOrderList findMaxGroupId(@Param("membersEmail") String membersEmail);

    @Query("select v from VendorsOrderList v " +
            "where v.membersEmail = :membersEmail " +
            "and v.groupId < :groupId " +
            "order by v.groupId desc " +
            "limit 1")
    VendorsOrderList findByNextGroupId(@Param("membersEmail") String membersEmail,
                                       @Param("groupId") Long groupId);

    List<VendorsOrderList> findBymembersEmailAndOrderNumber(String membersEmail, String orderNumber);

}
