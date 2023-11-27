package chess;

import com.google.gson.*;
import reqRes.ListGamesRes;
import reqRes.Response;

import java.lang.reflect.Type;

public class ResponseAdapter implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ListGamesRes.class, new ListGamesResAdapter());
        return builder.create().fromJson(el, Response.class);
    }
}
