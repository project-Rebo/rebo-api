package hello.reboapi.domain.kakao.controller;

import hello.reboapi.domain.kakao.dto.KakaoGeocodeResponse;
import hello.reboapi.domain.kakao.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kakaos")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @Operation(summary = "카테고리 검색", description = "카테고리로 좌표값을 계산합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좌표변환 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 요청 키워드 잘못됨")
    })
    @PostMapping
    public ResponseEntity<KakaoGeocodeResponse> searchKeyword(@RequestBody String keyword) {
        KakaoGeocodeResponse response = kakaoService.searchKeyword(keyword);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
