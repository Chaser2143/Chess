package chess;

import com.google.gson.*;
import reqRes.ListGamesRes;
import reqRes.Response;

import java.lang.reflect.Type;

public class ListGamesResAdapter implements JsonDeserializer<ListGamesRes> {

    @Override
    public ListGamesRes deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(CGame.class, new ChessGameAdapter());
        return builder.create().fromJson(el, ListGamesRes.class);
    }
}
