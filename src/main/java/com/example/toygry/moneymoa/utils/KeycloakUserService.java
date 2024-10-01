package com.example.toygry.moneymoa.utils;

import com.example.toygry.moneymoa.Friends.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class KeycloakUserService {

    @Value("${keycloak.admin-uri}")
    private String KEYCLOAK_ADMIN_URI;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> getUserList(String accessToken) throws JsonProcessingException {
        String url = KEYCLOAK_ADMIN_URI + "/users";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.replace("Bearer",""));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // Convert JSON string to List<User>
        return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
    }
}
