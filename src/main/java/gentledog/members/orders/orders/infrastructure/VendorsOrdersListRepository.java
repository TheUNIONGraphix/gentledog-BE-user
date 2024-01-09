package gentledog.members.orders.orders.infrastructure;

import gentledog.members.orders.orders.entity.VendorsOrdersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendorsOrdersListRepository extends JpaRepository<VendorsOrdersList, Long>, VendorsOrdersListRepositoryCustom {
    Boolean existsByOrderNumber(String orderNumber);

    @Query("select v from VendorsOrdersList v " +
            "where v.membersEmail = :membersEmail " +
            "order by v.groupId desc " +
            "limit 1")
    VendorsOrdersList findMaxGroupId(@Param("membersEmail") String membersEmail);

    @Query("select v from VendorsOrdersList v " +
            "where v.membersEmail = :membersEmail " +
            "and v.groupId < :groupId " +
            "order by v.groupId desc " +
            "limit 1")
    VendorsOrdersList findByNextGroupId(@Param("membersEmail") String membersEmail,
                                        @Param("groupId") Long groupId);

    List<VendorsOrdersList> findByMembersEmailAndOrderNumber(String membersEmail, String orderNumber);

}
