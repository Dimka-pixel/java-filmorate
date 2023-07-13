package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class ObjectMapperConfiguration {
    @Bean
    public SimpleModule durationSerialize() throws IOException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new JsonSerializer<Duration>() {
            @Override
            public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                long minute = duration.toSeconds();
                jsonGenerator.writeNumber(minute);
            }

            @Override
            public Class<Duration> handledType() {
                return Duration.class;
            }
        });
        return module;

    }

}
