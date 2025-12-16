package com.interview.techview.adapter.stt;

import com.interview.techview.port.stt.SpeechToTextPort;
import org.springframework.stereotype.Component;

@Component
public class MockSpeechToTextAdapter implements SpeechToTextPort {
    public String transcribe(String videoUrl) {
        return "이것은 STT 테스트 결과입니다.";
    }
}