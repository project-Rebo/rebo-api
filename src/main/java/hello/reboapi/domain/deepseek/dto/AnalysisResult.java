package hello.reboapi.domain.deepseek.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalysisResult {
    
    private final String content;        // AI 생성 리포트 텍스트
    private final Integer score;         // AI가 매긴 점수
    private final String status;   // AI가 판단한 상권 상태
}
