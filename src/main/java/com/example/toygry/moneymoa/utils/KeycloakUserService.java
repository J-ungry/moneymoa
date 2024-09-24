package com.example.toygry.moneymoa.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeycloakUserService {

    @Value("${keycloak.admin-uri}")
    private String KEYCLOAK_ADMIN_URI;

    public String getUserList(String accessToken) {
        String url = KEYCLOAK_ADMIN_URI + "/users";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.replace("Bearer",""));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody();
    }
}
