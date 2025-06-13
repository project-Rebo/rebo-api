package hello.reboapi.domain.deepseek.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hello.reboapi.global.config.cache.dto.StoreAnalysisCache;

@Slf4j
@Service
public class DeepSeekService {
    
    private final RestTemplate restTemplate;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.model}")
    private String model;

    public DeepSeekService(@Qualifier("deepseekRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // text prompt
    public String textTransFormScript(StoreAnalysisCache storeAnalysisCache) {

        String prompt = String.format("""
            당신은 상권분석 전문가입니다. 아래 형식을 정확히 따라 분석 리포트를 작성해주세요.

            입력 데이터:
            지역: %s
            업종: %s
            분석 반경: %d미터
            점포 밀도: %.2f개/km²
            총 점포 수: %d개

            다음 양식을 그대로 사용하여 응답해주세요. 각 섹션의 제목과 형식을 정확히 유지해야 합니다:

            === 상권 분석 리포트 ===

            [기본 정보]
            - 분석 지역: %s
            - 분석 업종: %s
            - 분석 반경: %d미터
            - 점포 밀도: %.2f개/km²
            - 총 점포 수: %d개

            [종합 평가]
            - 최종 점수: %%s
            - 상권 상태: %%s

            [상세 분석]
            1. 상권 현황
            - 총 점포 수: %d개
            - 점포 밀도: %.2f개/km²

            2. 경쟁 강도
            - 점포 밀집도: %%s
            - 시장 포화도: %%s
            - 진입 장벽: %%s

            3. 성장성 평가
            - 상권 발전 단계: %%s
            - 성장 가능성: %%s
            - 위험도: %%s

            [전략적 제언]
            1. 진입 전략
                - %%s
                - %%s
                - %%s

            2. 주의사항
                - %%s
                - %%s
                - %%s

            3. 차별화 포인트
                - %%s
                - %%s
                - %%s

            [결론]
            - 이 상권은 %%s

            위 형식을 정확히 따라 응답해주세요. 각 섹션의 글머리 기호와 들여쓰기를 유지하고, 빈 칸을 채워주세요.
            최종 점수는 1~100점 사이의 점수를 입력해주세요. 점수가 높을수록 창업하기 좋은 상권입니다.
            상권 상태는 레드오션, 중립, 블루오션{레드오션: 창업하기 좋은 상권, 중립: 창업하기 보통 상권, 블루오션: 창업하기 좋지 않은 상권} 중 하나를 선택해주세요.
            [결론]에 대한 내용은 상권 분석가의 종합적인 의견을 고정 300자로 포함해주세요. 종합적인 의견은 [기본정보], [종합평가], [상세분석], [전략적 제언] 섹션을 참고하여 근거를 두고 작성해주세요.
            """,
            storeAnalysisCache.getRegion(),     // 1. 지역
            storeAnalysisCache.getCategory(),   // 2. 업종
            storeAnalysisCache.getRadius(),     // 3. 분석 반경
            storeAnalysisCache.getDesity(),     // 4. 점포 밀도
            storeAnalysisCache.getTotalStores(),// 5. 총 점포 수
            storeAnalysisCache.getRegion(),     // 6. 분석 지역
            storeAnalysisCache.getCategory(),   // 7. 분석 업종
            storeAnalysisCache.getRadius(),     // 8. 분석 반경
            storeAnalysisCache.getDesity(),     // 9. 점포 밀도
            storeAnalysisCache.getTotalStores(),// 10. 총 점포 수
            storeAnalysisCache.getTotalStores(),// 11. 총 점포 수 (상권 현황)
            storeAnalysisCache.getDesity()      // 12. 점포 밀도 (상권 현황)
        );

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", prompt
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, String> message = (Map<String, String>) firstChoice.get("message");
        return message.get("content");
    }
}
