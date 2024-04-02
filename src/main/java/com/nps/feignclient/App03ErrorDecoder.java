package com.nps.feignclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nps.exception.App03Exception;
import com.nps.exception.BusinessError;
import com.nps.exception.BusinessErrorCode;
import com.nps.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

public class App03ErrorDecoder implements ErrorDecoder {

    private static  final Logger logger = LoggerFactory.getLogger(App03ErrorDecoder.class);
    private final ObjectMapper objectMapper;

    public App03ErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ApiErrorMessage apiErrorMessage = objectMapper.readValue(copyToString(response), ApiErrorMessage.class);
            logger.error("App 03 return error response: {}", apiErrorMessage);
            String errorDescription = "";
            if (apiErrorMessage != null && apiErrorMessage.getDescription() != null) {
                errorDescription = apiErrorMessage.getDescription();
            }

            if (response.status() == 400) {
                throw new BusinessException(BusinessErrorCode.bad_request);
            }

            if (response.status() == 409) {
                throw new BusinessException(new BusinessError(BusinessErrorCode.bad_request, errorDescription));
            }

            return new App03Exception();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String copyToString(Response response) throws IOException {
        return StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
    }


    @Data
    private static class ApiErrorMessage {
        private final ZonedDateTime timestamp = ZonedDateTime.now();
        private String code;
        private String title;
        private String description;
    }
}

