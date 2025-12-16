package com.interview.techview.port.storage;

import org.springframework.web.multipart.MultipartFile; // 내부적으로 스트리밍 처리

public interface VideoStoragePort {
    String upload(MultipartFile file, Long reportId, Long questionId);
}
