package com.zeeshan.cowinsms.controllers;

import com.zeeshan.cowinsms.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class SmsController {
    private static final Map<String, Response> map = new ConcurrentHashMap<>();

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        log.info("its working");
        return new ResponseEntity<>("working...", HttpStatus.OK);
    }

    @GetMapping(value = "/postotp")
    public ResponseEntity<Boolean> postOtp(@RequestParam String message, @RequestParam String userId) {
        log.info(message);
        String regex = "\\b\\d{6}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            Response response = map.computeIfAbsent(userId, k -> new Response());
            response.setOtp(matcher.group());
            response.setTime(new Date().getTime());
            response.setUserId(userId);
            log.info("otp updated to : {}", response);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping(value = "/getotp")
    public ResponseEntity<Map<String, Response>> getOtp() {
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
