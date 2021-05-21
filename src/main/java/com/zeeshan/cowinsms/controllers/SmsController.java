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
    public ResponseEntity<Boolean> postOtp(@RequestParam String message) {
        String regex = "[0-9]{1,6}";
        Pattern pattern = Pattern.compile(regex);
        //Creating a Matcher object
        Matcher matcher = pattern.matcher(message);
        System.out.println("Words in the given String: ");
        Response response = null;
        while (matcher.find()) {
            responseSingleton.setOtp(matcher.group());
            responseSingleton.setTime(new Date().getTime());
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }


    @GetMapping(value = "/getotp")
    public ResponseEntity<Response> getotp() {
        return new ResponseEntity<>(responseSingleton, HttpStatus.OK);
    }
}
