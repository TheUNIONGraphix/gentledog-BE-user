package gentledog.members.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),

    /**
     * 900: 기타 에러
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "Internal server error"),

    /**
     * 1000 : Order Service Error
     */
    ALREADY_PAID_ORDER_ID(HttpStatus.BAD_REQUEST, false, 1000, "이미 결제된 주문번호입니다"),
    DOSE_NOT_EXIST_PAYMENT(HttpStatus.BAD_REQUEST, false, 1001, "결제내역이 존재하지 않습니다"),
    CANCELED_AMOUNT_EXCEEDED(HttpStatus.BAD_REQUEST, false, 1002, "취소 금액 한도를 초과하였습니다"),
    PAYMENT_DATA_TRANSFER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 1003, "결제 정산 정보 전송에 실패하였습니다"),

    /**
     * 2000 : members Service Error
     */
    // Token, Code
    TokenExpiredException(HttpStatus.UNAUTHORIZED ,false, 2001, "토큰이 만료되었습니다."),
    TokenInvalidException(HttpStatus.UNAUTHORIZED , false, 2002, "토큰이 유효하지 않습니다."),
    TokenNullException(HttpStatus.UNAUTHORIZED , false, 2003, "토큰이 존재하지 않습니다."),
    JWT_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR , false, 2004, "토큰 생성에 실패했습니다."),
    JWT_VALID_FAILED(HttpStatus.UNAUTHORIZED , false, 2005, "토큰 검증에 실패했습니다."),
    EXPIRED_AUTH_CODE(HttpStatus.UNAUTHORIZED , false, 2006, "인증번호가 만료되었습니다."),
    WRONG_AUTH_CODE(HttpStatus.UNAUTHORIZED , false, 2007, "인증번호가 일치하지 않습니다."),

    // Members
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, false, 2100, "사용중인 이메일입니다."),
    DUPLICATED_MEMBERS(HttpStatus.CONFLICT, false, 2101, "이미 가입된 유저입니다."),
    MASSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 2102, "인증번호 전송에 실패했습니다."),
    MASSAGE_VALID_FAILED(HttpStatus.UNAUTHORIZED, false, 2103, "인증번호가 일치하지 않습니다."),
    FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED , false, 2104, "없는 아이디거나 비밀번호가 틀렸습니다."),
    WITHDRAWAL_MEMBERS(HttpStatus.FORBIDDEN, false, 2105, "탈퇴한 회원입니다."),
    NO_EXIST_MEMBERS(HttpStatus.NOT_FOUND, false, 2106, "존재하지 않는 유저 정보입니다."),
    MEMBERS_STATUS_IS_NOT_FOUND(HttpStatus.NOT_FOUND, false, 2107, "존재하지 않는 유저 상태입니다."),
    PASSWORD_SAME_FAILED(HttpStatus.BAD_REQUEST, false, 2108, "현재 사용중인 비밀번호 입니다."),
    PASSWORD_CONTAIN_NUM_FAILED(HttpStatus.BAD_REQUEST, false, 2109, "휴대폰 번호를 포함한 비밀번호 입니다."),
    PASSWORD_CONTAIN_EMAIL_FAILED(HttpStatus.BAD_REQUEST, false, 2110, "이메일이 포함된 비밀번호 입니다."),

    // Dog
    NO_EXIST_DOG_BREED(HttpStatus.NOT_FOUND, false, 2200, "존재하지 않는 품종입니다."),
    NO_EXIST_DOG(HttpStatus.NOT_FOUND, false, 2201, "존재하지 않는 반려견입니다."),
    NO_EXIST_DOG_BREED_INFO(HttpStatus.OK, false, 2202, "해당 품종에 대한 다른 유저의 정보가 존재하지 않습니다."),

    // Address
    NO_EXIST_ADDRESS(HttpStatus.NOT_FOUND, false, 2300, "존재하지 않는 주소입니다."),


    /**
     * 5000 : Cart & WishProductList Service Error
     */
    NO_DATA(HttpStatus.BAD_REQUEST, false, 6001, "존재하지 않는 정보입니다"),
    ALREADY_ADDED_PRODUCT(HttpStatus.CONFLICT, false, 6002, "이미 장바구니에 존재하는 상품입니다"),
    ALREADY_ADDED_WISH_PRODUCT(HttpStatus.CONFLICT, false, 6003, "이미 찜한 상품입니다"),
    ;


    private final HttpStatusCode httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private String message;

}
