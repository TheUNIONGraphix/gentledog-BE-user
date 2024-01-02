package gentledog.members.members.members.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Members extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "members_email", length = 320)
    private String membersEmail;
    @Column(name = "password", length = 100)
    private String password;
    @Column(name = "members_name", length = 20)
    private String membersName;
    @Column(name = "members_age")
    private Integer membersAge;
    @Convert(converter = MembersGenderConverter.class)
    @Column(name = "members_gender", columnDefinition = "tinyint")
    private MemberGenderStatus membersGender;
    @Column(name = "members_phone_number", length = 15, nullable = false)
    private String membersPhoneNumber;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * 도메인 로직
     * 1. 비밀번호 암호화
     * 2. 회원정보 수정 : membersEmail, membersName, membersPhoneNumber, membersAge, membersGender 변경
     * 3. 유저 탈퇴: deletedAt 변경
     *
     */

    // 1. 비밀번호 암호화
    public void hashPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    // 2. 회원 정보 수정
    public void updateMembersInfo(String membersEmail,
                                  String membersName,
                                  String membersPhoneNumber,
                                  Integer membersAge,
                                  MemberGenderStatus membersGender) {

        this.membersEmail = membersEmail;
        this.membersName = membersName;
        this.membersPhoneNumber = membersPhoneNumber;
        this.membersAge = membersAge;
        this.membersGender = membersGender;
    }

    // 3. 유저 탈퇴
    public void deactivateMembers() {
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return membersEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
