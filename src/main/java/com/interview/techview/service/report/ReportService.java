package com.interview.techview.service.report;

import com.interview.techview.domain.question.Question;
import com.interview.techview.domain.report.Report;
import com.interview.techview.domain.report.ReportQuestion;
import com.interview.techview.domain.user.User;
import com.interview.techview.dto.report.ReportCreateRequest;
import com.interview.techview.dto.report.ReportQuestionResponse;
import com.interview.techview.dto.report.ReportResponse;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.port.analysis.AiAnalysisPort;
import com.interview.techview.port.storage.VideoStoragePort;
import com.interview.techview.port.stt.SpeechToTextPort;
import com.interview.techview.repository.question.QuestionRepository;
import com.interview.techview.repository.report.ReportQuestionRepository;
import com.interview.techview.repository.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportQuestionRepository reportQuestionRepository;
    private final QuestionRepository questionRepository;

    private final VideoStoragePort videoStoragePort;
    private final SpeechToTextPort speechToTextPort;
    private final AiAnalysisPort aiAnalysisPort;

    // 리포트 생성
    public ReportResponse create(Long userId, ReportCreateRequest request) {

        User user = User.builder().id(userId).build();

        Report report = reportRepository.save(
                Report.builder()
                        .user(user)
                        .build()
        );

        List<Question> questions = questionRepository.findAllById(request.getQuestionIds());
        if (questions.size() != request.getQuestionIds().size()) {
            throw new CustomException(ErrorCode.QUESTION_NOT_FOUND);
        }

        for (Question q : questions) {
            reportQuestionRepository.save(
                    ReportQuestion.builder()
                            .report(report)
                            .question(q)
                            .build()
            );
        }

        return getReport(report.getId());
    }

    // 질문 목록 조회
    @Transactional(readOnly = true)
    public ReportResponse getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));

        List<ReportQuestionResponse> responses =
                reportQuestionRepository.findByReportId(reportId)
                        .stream()
                        .map(ReportQuestionResponse::from)
                        .toList();

        return ReportResponse.builder()
                .id(report.getId())
                .questions(responses)
                .build();
    }

    // 영상 URL 조회
    public String getVideoUrl(Long reportId, Long questionId) {
        return getReportQuestion(reportId, questionId).getVideoUrl();
    }

    // 영상 업로드
    public void uploadVideo(Long reportId, Long questionId, MultipartFile file) {
        ReportQuestion rq = getReportQuestion(reportId, questionId);

        String videoUrl = videoStoragePort.upload(file, reportId, questionId);
        rq.updateVideoUrl(videoUrl);
    }

    // STT 조회
    public String getSttResult(Long reportId, Long questionId) {
        return getReportQuestion(reportId, questionId).getSttReply();
    }

    // STT 요청
    public void requestStt(Long reportId, Long questionId) {
        ReportQuestion rq = getReportQuestion(reportId, questionId);

        String stt = speechToTextPort.transcribe(rq.getVideoUrl());
        rq.updateSttReply(stt);
    }

    // AI 분석 조회
    public String getAnalysisResult(Long reportId, Long questionId) {
        return getReportQuestion(reportId, questionId).getAnalysis();
    }

    // AI 분석 요청
    public void requestAnalysis(Long reportId, Long questionId) {
        ReportQuestion rq = getReportQuestion(reportId, questionId);

        String result = aiAnalysisPort.analyze(rq.getSttReply());
        rq.updateAnalysis(result);
    }

    // 면접 결과 보고서 조회 (질문)
    private ReportQuestion getReportQuestion(Long reportId, Long questionId) {
        return reportQuestionRepository
                .findByReportIdAndQuestionId(reportId, questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_QUESTION_NOT_FOUND));
    }
}
