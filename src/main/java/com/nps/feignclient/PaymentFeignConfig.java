package com.nps.feignclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class PaymentFeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder feignEncoder() {
        HttpMessageConverter<Object> jacksonConverter
                = new MappingJackson2HttpMessageConverter(app03ObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory
                = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverter<Object> jackson2HttpMessageConverter
                = new MappingJackson2HttpMessageConverter(app03ObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory
                = () -> new HttpMessageConverters(jackson2HttpMessageConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory, new ObjectProvider<HttpMessageConverterCustomizer>() {
            @Override
            public HttpMessageConverterCustomizer getObject(Object... args) throws BeansException {
                return null;
            }

            @Override
            public HttpMessageConverterCustomizer getIfAvailable() throws BeansException {
                return null;
            }

            @Override
            public HttpMessageConverterCustomizer getIfUnique() throws BeansException {
                return null;
            }

            @Override
            public HttpMessageConverterCustomizer getObject() throws BeansException {
                return null;
            }

            @Override
            public void forEach(Consumer action) {
                // do nothing
            }
        }));
    }

    @Bean
    public FeignFormatterRegistrar feignFormatterRegistrar() {
        return formatterRegistry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(false);
            registrar.setDateFormatter(DateTimeFormatter.ISO_DATE_TIME);
            registrar.registerFormatters(formatterRegistry);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new App03ErrorDecoder(app03ObjectMapper());
    }

    private ObjectMapper app03ObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.registerModule(new JavaTimeModule());
        return om;
    }
}
