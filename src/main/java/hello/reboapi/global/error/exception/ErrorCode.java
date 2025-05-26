package hello.reboapi.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자를 찾을 수 없습니다."),
    USER_LIST_EMPTY(HttpStatus.NOT_FOUND, "USER_404", "조회할 사용자가 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_409", "이미 존재하는 사용자입니다."),
    INVALID_USER_INPUT(HttpStatus.BAD_REQUEST, "USER_400", "사용자 입력값이 올바르지 않습니다."),
    // USER
    KAKAO_NOT_FOUND(HttpStatus.NOT_FOUND, "KAKAO_404", "카테고리를 찾을 수 없습니다."),
    INVALID_KAKAO_INPUT(HttpStatus.BAD_REQUEST, "KAKAO_400", "사용자 입력값이 올바르지 않습니다."),

    // STORE
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_404", "매장을 찾을 수 없습니다."),
    STORE_LIST_EMPTY(HttpStatus.NOT_FOUND, "STORE_404", "조회할 매장이 없습니다."),
    STORE_ALREADY_EXISTS(HttpStatus.CONFLICT, "STORE_409", "이미 존재하는 매장입니다."),
    INVALID_STORE_INPUT(HttpStatus.BAD_REQUEST, "STORE_400", "매장 정보가 올바르지 않습니다."),
    UNAUTHORIZED_STORE_ACCESS(HttpStatus.FORBIDDEN, "STORE_403", "매장에 대한 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
