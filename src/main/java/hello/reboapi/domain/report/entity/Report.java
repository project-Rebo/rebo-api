package hello.reboapi.domain.report.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "store_input_id")
    private Long storeInputId;

    @Column(name = "total_store_count")
    private Integer totalStoreCount;

    @Column(name = "density")
    private Double density;

    @Column(name = "score")
    private Double score;

    @Column(name = "status")
    private String status;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String content;

    @Column(name = "analysis_data", columnDefinition = "TEXT")
    private String analysisData;

    @Column(name = "request_data", columnDefinition = "TEXT")
    private String requestData;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Report(Long memberId, Long storeInputId, Integer totalStoreCount, Double density, 
                  Double score, String status, String content, String analysisData, 
                  String requestData, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberId = memberId;
        this.storeInputId = storeInputId;
        this.totalStoreCount = totalStoreCount;
        this.density = density;
        this.score = score;
        this.status = status;
        this.content = content;
        this.analysisData = analysisData;
        this.requestData = requestData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
