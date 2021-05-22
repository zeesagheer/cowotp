package com.zeeshan.cowinsms.controllers;

import com.zeeshan.cowinsms.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class SmsController {
    private static Response responseSingleton = new Response();

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        log.info("its working");
        return new ResponseEntity<>("working...", HttpStatus.OK);
    }

    @GetMapping(value = "/postotp")
    public ResponseEntity<Boolean> postOtp(@RequestParam String message, @RequestParam Long chatId) {
        log.info(message);
        String regex = "\\b\\d{6}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            responseSingleton.setOtp(matcher.group());
            responseSingleton.setTime(new Date().getTime());
            responseSingleton.setChatId(chatId);
            log.info("otp updated to : {}", responseSingleton);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }


    @GetMapping(value = "/getotp")
    public ResponseEntity<Response> getotp() {
        return new ResponseEntity<>(responseSingleton, HttpStatus.OK);
    }
}
