package chess;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Used to deserialize chess games (and chessboards/chess pieces nested) from json
 */
public class ChessGameAdapter implements JsonDeserializer<ChessGame> {
    @Override
    public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        return builder.create().fromJson(el, CGame.class);
//        return new Gson().fromJson(el, CGame.class);
    }
}
