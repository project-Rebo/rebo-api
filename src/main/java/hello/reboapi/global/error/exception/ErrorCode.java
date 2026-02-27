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

    // DeepSeek AI 관련
    DEEPSEEK_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DEEPSEEK_500", "DeepSeek API 호출 중 오류가 발생했습니다."),
    DEEPSEEK_ANALYSIS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DEEPSEEK_500", "AI 분석 처리 중 오류가 발생했습니다."),
    DEEPSEEK_INVALID_RESPONSE(HttpStatus.BAD_REQUEST, "DEEPSEEK_400", "DeepSeek API 응답이 올바르지 않습니다."),
    DEEPSEEK_API_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "DEEPSEEK_429", "DeepSeek API 호출 한도를 초과했습니다."),

    // Search Service 관련
    SEARCH_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SEARCH_500", "검색 서비스 호출 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
