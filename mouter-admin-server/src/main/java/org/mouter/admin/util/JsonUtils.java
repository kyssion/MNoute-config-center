package org.mouter.admin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {
    }

    public static String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("encode(Object)", e); //$NON-NLS-1$
        }
        return null;
    }

    /**
     * 将json string反序列化成对象
     *
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }

    /**
     * 将json array反序列化为对象
     *
     * @param json
     * @param typeReference
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T decode(String json, TypeReference<T> typeReference) {
        try {
            return (T) objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }

}

