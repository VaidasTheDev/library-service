package configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaidas.library.BookLibraryApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.vaidas.library.model.GlobalConstants.DEFAULT_DATE_FORMAT;

@CucumberContextConfiguration
@SpringBootTest(classes = BookLibraryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegration {

    private static final String URI_PREFIX = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<Object> executePost(String endpoint, T body) throws JsonProcessingException {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        RequestEntity<Object> requestEntity = new RequestEntity<>(
                body,
                headers,
                HttpMethod.POST,
                URI.create(URI_PREFIX + endpoint)
        );

        try {
            return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException e) {
            Map<String, String> map = SpringIntegration.objectMapper()
                    .readValue(e.getResponseBodyAsString(), new TypeReference<>() { });
            return ResponseEntity.status(e.getStatusCode()).body(map);
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ObjectMapper objectMapper() {
        return createObjectMapper();
    }
}
