package br.com.zup.edu.biblioteca;

import br.com.zup.edu.biblioteca.controller.payment.CachedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntitySerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(CachedResponse responseEntity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(responseEntity);
    }

    public CachedResponse deserialize(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, CachedResponse.class);
    }
}
