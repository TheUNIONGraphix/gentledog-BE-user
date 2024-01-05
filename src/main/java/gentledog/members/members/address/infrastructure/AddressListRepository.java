package gentledog.members.members.address.infrastructure;

import gentledog.members.members.address.entity.AddressList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressListRepository extends JpaRepository<AddressList, Long> {

    @Query("SELECT a FROM AddressList a " +
            "WHERE a.members.id = :membersId " +
            "ORDER BY a.defaultAddress DESC")
    List<AddressList> findByMembersId(@Param("membersId") Long membersId);
    AddressList findByMembersIdAndDefaultAddress(Long membersId, Boolean defaultAddress);
    AddressList findByMembersIdAndAddressId(Long membersId, Long addressId);
    AddressList findByAddressId(Long addressId);
    AddressList findDefaultAddressByMembersId(Long membersId);

}
