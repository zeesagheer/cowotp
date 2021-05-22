package com.zeeshan.cowinsms.dto;

import lombok.Data;

@Data
public class Response {
    private String otp;
    private Long time;
    private String userId;
}
