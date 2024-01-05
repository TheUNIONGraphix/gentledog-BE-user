package gentledog.members.members.members.entity;


import gentledog.members.global.common.enums.BaseEnumConverter;

public class MembersGenderConverter extends BaseEnumConverter<MemberGenderStatus, Integer, String> {
    public MembersGenderConverter() { super(MemberGenderStatus.class); }
}
