package gentledog.members.global.common.exception;


import gentledog.members.global.common.response.BaseResponse;
import gentledog.members.global.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {
    /**
     * 발생한 예외 처리
     * 1. 등록된 에러
     * 2. 런타임 에러
     * 3. @Valid로 처리되는 에러
     */

    // 등록된 에러
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<?> BaseError(BaseException e) {
        // BaseException의 BaseResponseStatus를 가져와서 BaseResponse를 만들어서 return해줌
        BaseResponse response = new BaseResponse(e.getStatus());
        log.info("BaseException: " + e.getStatus().getMessage());
        return new ResponseEntity<>(response, response.httpStatus());
    }

    // 런타임 에러
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> RuntimeError(RuntimeException e) {
        // BaseException으로 잡히지 않는 RuntimeError는, INTERNAL_SEBVER_ERROR로 처리해줌
        BaseResponse response = new BaseResponse(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        log.info("RuntimeException: " + e.getMessage());
        return new ResponseEntity<>(response, response.httpStatus());
    }

    // @Valid 를 통해 발생한 에러처리 MethodArgumentNotValidException이 발생 함
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processVaildationError(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        // 여러개의 값을 잘못입력했을 때는 message를 어떻게 넘겨줄지 고민 중
        // front에서 혹시나 잘못 줄 경우의 처리라서 일단 나중에 고민
        StringBuilder builder = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            builder.append(fieldError.getDefaultMessage());
            builder.append(" / ");
        }
        BaseResponse response = new BaseResponse(e, builder.toString());
        return new ResponseEntity<>(response, response.httpStatus());
    }

}