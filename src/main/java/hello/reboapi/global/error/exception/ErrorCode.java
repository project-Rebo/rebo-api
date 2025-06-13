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
    UNAUTHORIZED_STORE_ACCESS(HttpStatus.FORBIDDEN, "STORE_403", "매장에 대한 권한이 없습니다."),
    STORE_ANALYSIS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORE_500", "상권 분석 처리 중 오류가 발생했습니다."),

    // KAKAO API 관련
    KAKAO_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KAKAO_500", "카카오 API 호출 중 오류가 발생했습니다."),
    KAKAO_GEOCODING_FAILED(HttpStatus.BAD_REQUEST, "KAKAO_400", "주소 좌표 변환에 실패했습니다."),
    KAKAO_INVALID_RESPONSE(HttpStatus.BAD_REQUEST, "KAKAO_400", "카카오 API 응답이 올바르지 않습니다."),
    KAKAO_API_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "KAKAO_429", "카카오 API 호출 한도를 초과했습니다."),

    // DeepSeek AI 관련
    DEEPSEEK_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DEEPSEEK_500", "DeepSeek API 호출 중 오류가 발생했습니다."),
    DEEPSEEK_ANALYSIS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DEEPSEEK_500", "AI 분석 처리 중 오류가 발생했습니다."),
    DEEPSEEK_INVALID_RESPONSE(HttpStatus.BAD_REQUEST, "DEEPSEEK_400", "DeepSeek API 응답이 올바르지 않습니다."),
    DEEPSEEK_API_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "DEEPSEEK_429", "DeepSeek API 호출 한도를 초과했습니다."),

    // 캐시 관련
    CACHE_NOT_FOUND(HttpStatus.NOT_FOUND, "CACHE_404", "캐시된 데이터를 찾을 수 없습니다."),
    CACHE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CACHE_500", "캐시 업데이트 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
