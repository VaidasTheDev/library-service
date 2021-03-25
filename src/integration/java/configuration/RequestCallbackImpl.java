package configuration;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

@AllArgsConstructor
@Setter
public class RequestCallbackImpl implements RequestCallback {

    private HttpHeaders requestHeaders;

    @Override
    public void doWithRequest(ClientHttpRequest request) {
        this.requestHeaders = request.getHeaders();
    }
}