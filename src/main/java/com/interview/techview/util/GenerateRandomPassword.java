package com.interview.techview.util;

import java.security.SecureRandom;

public final class GenerateRandomPassword {
    private static final SecureRandom random = new SecureRandom();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    // 생성자 private → 유틸 클래스 인스턴스화 방지
    private GenerateRandomPassword() {}

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

}
