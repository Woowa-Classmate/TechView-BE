package com.interview.techview.adapter.analysis;

import com.interview.techview.port.analysis.AiAnalysisPort;
import org.springframework.stereotype.Component;

@Component
public class MockAiAnalysisAdapter implements AiAnalysisPort {
    public String analyze(String sttText) {
        return "AI 분석 결과입니다.";
    }
}

