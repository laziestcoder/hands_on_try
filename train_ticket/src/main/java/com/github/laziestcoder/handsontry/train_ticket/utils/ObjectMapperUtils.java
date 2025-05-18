package com.github.laziestcoder.handsontry.train_ticket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author TOWFIQUL ISLAM
 * @since 9/12/24
 */

@Slf4j
@AllArgsConstructor
@Component
public class ObjectMapperUtils {
    private final ObjectMapper objectMapper;

    public String objectToJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T convertToType(Map<?, ?> data, Class<T> tClass) {
        return objectMapper.convertValue(data, tClass);
    }

    public JsonNode convertStringToJson(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T convertToType(Object data, Class<T> tClass) {
        log.debug("Converting object {} to type {}", data, tClass);
        return objectMapper.convertValue(data, tClass);
    }

    public <T> T convertToType(InputStream data, Class<T> tClass) throws IOException {
        try {
            return objectMapper.readValue(data, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
