package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaidas.library.BookLibraryApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@CucumberContextConfiguration
@SpringBootTest(classes = BookLibraryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegration {

    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
    private static final String URI_PREFIX = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();
    public static ObjectMapper objectMapper = new ObjectMapper();

    public <R, T> ResponseEntity<R> executePost(String endpoint, T body) {
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

        return restTemplate
                .exchange(requestEntity, new ParameterizedTypeReference<>() {});
    }

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }
}
