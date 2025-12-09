package com.interview.techview.repository.report;

import com.interview.techview.domain.report.Report;
import com.interview.techview.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUser(User user);
}
