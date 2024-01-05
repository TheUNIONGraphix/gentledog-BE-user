package gentledog.members.members.members.entity;

import gentledog.members.global.common.enums.BaseEnum;
import gentledog.members.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // BaseEnum의 method를 impl하는것을 -> Getter로 대체
@AllArgsConstructor // 생성자를 직접 만들지 않고 -> AllArgs로 대체
public enum MemberGenderStatus implements BaseEnum<Integer, String> {
    /**
     * 1. 코드 작성
     * 2. field 선언
     * 3. converter 구현
     */

    NONE(0,"선택안함"),
    MAN(1,"남자"),
    FEMALE(2,"여자");

    // 2. field 선언
    private final Integer code;
    private final String description;


    // 3. converter 구현
    @Converter(autoApply = true)
    static class thisConverter extends BaseEnumConverter<MemberGenderStatus, Integer, String> {
        public thisConverter() {
            super(MemberGenderStatus.class);
        }
    }
}
