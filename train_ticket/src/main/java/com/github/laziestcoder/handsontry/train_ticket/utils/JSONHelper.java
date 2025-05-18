package com.github.laziestcoder.handsontry.train_ticket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * @author TOWFIQUL ISLAM
 * @since 27/11/24
 */

@Slf4j
@UtilityClass
public class JSONHelper {

    public static <T> T extractValueFromResponseData(String responseBody, String parentKey, String findableObjectKey) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        if (!parentKey.isEmpty()) {
            return (T) jsonResponse.getJSONObject(parentKey).getString(findableObjectKey);
        }
        return (T) jsonResponse.get(findableObjectKey);
    }

    public static String extractJsonFromResponseData(String responseBody, String findableObjectKey) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        return jsonResponse.get(findableObjectKey).toString();
    }

    public static String extractValueFromResponseData(String jsonString, String parentKey, String dataKey, String findableObjectKey) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);

            JsonNode dataArray = rootNode.at("/" + parentKey);

            if (dataArray.isArray()) {
                for (JsonNode train : dataArray) {
                    if (train.has(dataKey) && train.get(dataKey).asText().equalsIgnoreCase(findableObjectKey)) {
                        return train.toString();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while extracting value from response: {}", e.getMessage(), e);
        }

        return "";
    }

    public static String extractValueFromResponseDataList(String jsonString, String parentKey, String dataKey, String subKey, String findableObjectKey) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);

            JsonNode dataArray = rootNode.at("/" + parentKey + "/" + dataKey);

            if (dataArray.isArray()) {
                for (JsonNode train : dataArray) {
                    if (train.has(subKey) && train.get(subKey).asText().equalsIgnoreCase(findableObjectKey)) {
                        return train.toString();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while extracting value from response: {}", e.getMessage(), e);
        }

        return "";
    }

    public static Boolean checkAvailableValueFromResponseDataList(String jsonString, String parentKey, String dataKey, String subKey, String value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonString);

            JsonNode dataArray = rootNode.at("/" + parentKey + "/" + dataKey);

            if (dataArray.isArray()) {
                for (JsonNode train : dataArray) {
                    if (train.has(subKey) && train.get(subKey).asText().equals(value)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while extracting value from response: {}", e.getMessage(), e);
        }

        return false;
    }

    public static <T> T convertJsonStringToObjectValue(String jsonString, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to object", e);
        }
    }
}
