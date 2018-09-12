package ar.edu.unlp.pasae.tp_integrador.services;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MapToCoupleArraySerializer extends JsonSerializer<Map<?, ?>> {
    @Override
    public void serialize(Map<?, ?> value, JsonGenerator generator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        generator.writeStartArray();

        for (Entry<?, ?> entry : value.entrySet()) {
            generator.writeStartObject();
            generator.writeObjectField("id", entry.getKey());
            generator.writeObjectField("value", entry.getValue());
            generator.writeEndObject();
        }

        generator.writeEndArray();
    }
}