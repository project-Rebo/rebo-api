package hello.reboapi.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.reboapi.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByMemberIdOrderByCreatedAtDesc(Long memberId);
}
