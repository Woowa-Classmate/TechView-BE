package com.interview.techview.domain.user;

public enum LockLevel {
    NONE,              // 잠금 없음
    TEMPORARY_5MIN,    // 5분 임시 잠금
    TEMPORARY_10MIN,   // 10분 임시 잠금
    PERMANENT          // 영구 잠금
}


