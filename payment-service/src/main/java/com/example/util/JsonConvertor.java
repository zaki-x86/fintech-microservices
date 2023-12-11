package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonConvertor {

    private final ObjectWriter objectWriter;

    public String toJson(Object obj) {
        try {
            return objectWriter.writeValueAsString(obj);
        } catch (Exception ex) {
            log.error("Exception jsonConvertor obj: {} ex: {}", obj, ex);
            throw new RuntimeException();
        }
    }


}
