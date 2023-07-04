package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;

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

    @Bean
    public SimpleModule MpaDeserialize() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Mpa.class, new JsonDeserializer<Mpa>() {
            @Override
            public Mpa deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                JsonNode node = jsonParser.getCodec().readTree(jsonParser);

                Integer id = node.get("id").asInt();

                for (Mpa mpa : Mpa.values()) {

                    if (mpa.getId() == id) {
                        return mpa;
                    }
                }
                return null;
            }
        });
        return simpleModule;
    }

    @Bean
    public SimpleModule GenreDeserialize() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Genres.class, new JsonDeserializer<Genres>() {
            @Override
            public Genres deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                JsonNode node = jsonParser.getCodec().readTree(jsonParser);

                Integer id = node.get("id").asInt();

                for (Genres genre : Genres.values()) {

                    if (genre.getId() == id) {
                        return genre;
                    }
                }
                return null;
            }
        });
        return simpleModule;
    }
}
