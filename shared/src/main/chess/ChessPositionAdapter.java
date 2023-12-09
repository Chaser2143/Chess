package chess;

import com.google.gson.*;

import java.lang.reflect.Type;


/**
 * Used to deserialize chess boards (and chess pieces nested) from json
 */
public class ChessPositionAdapter implements JsonDeserializer<ChessPosition> {
    @Override
    public ChessPosition deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
        return builder.create().fromJson(el, CPosition.class);
//        return new Gson().fromJson(el, CBoard.class);
    }
}