package game.ai.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
    public static String toJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true)
        ;
        try {
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
