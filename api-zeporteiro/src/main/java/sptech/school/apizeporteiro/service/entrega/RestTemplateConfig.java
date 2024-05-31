package sptech.school.apizeporteiro.service.entrega;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(300_000); // 300000 ms = 5 minutes
        factory.setReadTimeout(300_000); // 300000 ms = 5 minutes
        return new RestTemplate(factory);
    }
}
