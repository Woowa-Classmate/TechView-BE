package com.interview.techview.domain.report;

import com.interview.techview.domain.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_report_question",
                        columnNames = {"report_id", "question_id"}
                )
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReportQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    // S3 영상 URL
    @Column(name = "video_url", length = 255)
    private String videoUrl;

    // STT 텍스트
    @Lob
    @Column(name = "reply")
    private String sttReply;

    // 분석 결과 텍스트
    @Lob
    @Column(name = "analysis")
    private String analysis;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // update
    public void updateSttReply(String stt) {
        this.sttReply = stt;
    }

    public void updateAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public void updateVideoUrl(String url) {
        this.videoUrl = url;
    }
}
