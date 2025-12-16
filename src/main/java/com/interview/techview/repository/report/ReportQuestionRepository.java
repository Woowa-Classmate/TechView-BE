package com.interview.techview.repository.report;

import com.interview.techview.domain.report.Report;
import com.interview.techview.domain.report.ReportQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportQuestionRepository extends JpaRepository<ReportQuestion, Long> {

    List<ReportQuestion> findByReport(Report report);

    List<ReportQuestion> findByReportId(Long reportId);

    Optional<ReportQuestion> findByReportIdAndQuestionId(
            Long reportId,
            Long questionId
    );
}
