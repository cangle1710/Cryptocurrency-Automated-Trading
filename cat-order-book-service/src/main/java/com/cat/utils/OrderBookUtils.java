package com.cat.utils;

import com.cat.common.Constants;
import com.cat.common.Pair;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

import static com.cat.common.Constants.*;

public class OrderBookUtils {
    /**
     * Example subscribe message
     * {
     *     "type": "subscribe",
     *     "product_ids": [
     *         "BTC-USD"
     *     ],
     *     "channels": [
     *         "level2"
     *     ]
     * }
     *
     * @return
     */
    public static String constructJsonMessage(Constants.Type type, String productId, Constants.Channel channel){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE, type.getType());

        // product ids json array
        JSONArray productIds = new JSONArray();
        productIds.add(productId);
        jsonObject.put(PRODUCT_IDS, productIds);

        // channel json array
        JSONArray channels = new JSONArray();
        channels.add(channel.getChannel());
        jsonObject.put(CHANNELS, channels);

        return jsonObject.toString();
    }

    public static class ResponseDeserializer extends JsonDeserializer<Pair> {
        @Override
        public Pair deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            final Object[] array = jsonParser.readValueAs(Object[].class);
            if (array.length >= 3) {
                return new Pair(array[0], new Pair(array[1], array[2]));
            }
            else{
                return new Pair(array[0], array[1]);
            }
        }
    }

    public static class ResponseSerializer extends JsonSerializer<Pair> {
        @Override
        public void serialize(
                Pair pair,
                JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartArray(2);
            jsonGenerator.writeObject(pair.getKey());
            jsonGenerator.writeObject(pair.getValue());
            jsonGenerator.writeEndArray();
        }
    }

}