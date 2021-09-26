package ru.diasoft.micro.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import org.springframework.stereotype.Component;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.exception.FeignClientException;

import java.io.IOException;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    private static final DSLogger logger = DSLogManager.getLogger(CustomErrorDecoder.class);
    private final StringDecoder stringDecoder = new StringDecoder();

    @Override
    public RuntimeException decode(final String methodKey, Response response) {
        String message = "Null Response Body.";
        if (response.body() != null) {
            try {
                message = stringDecoder.decode(response, String.class).toString();
            } catch (IOException e) {
                logger.error(String.format("%s Error Deserializing response body from failed feign request response.", methodKey), e);
            }
        }
        return new FeignClientException(response.status(), message, response.headers());
    }
}
