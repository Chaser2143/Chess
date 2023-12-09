package chess;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Used to deserialize chess games (and chessboards/chess pieces nested) from json
 */
public class ChessMoveAdapter implements JsonDeserializer<ChessMove> {
    @Override
    public ChessMove deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessPosition.class, new ChessPositionAdapter());
        return builder.create().fromJson(el, CMove.class);
    }
}