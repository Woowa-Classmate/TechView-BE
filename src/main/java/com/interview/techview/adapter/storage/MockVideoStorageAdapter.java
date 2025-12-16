package com.interview.techview.adapter.storage;

import com.interview.techview.port.storage.VideoStoragePort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MockVideoStorageAdapter implements VideoStoragePort {
    public String upload(MultipartFile file, Long reportId, Long questionId) {
        return "http://localhost/videos/" + reportId + "/" + questionId + ".mp4";
    }
}

