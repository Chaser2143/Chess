package chess;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Used to deserialize Chess Pieces from Json
 */
public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        switch (el.getAsJsonObject().get("type").getAsString()){
            case "KING" -> {
                return new Gson().fromJson(el, King.class);
            }
            case "QUEEN" -> {
                return new Gson().fromJson(el, Queen.class);
            }
            case "PAWN" -> {
                return new Gson().fromJson(el, Pawn.class);
            }
            case "BISHOP" -> {
                return new Gson().fromJson(el, Bishop.class);
            }
            case "ROOK" -> {
                return new Gson().fromJson(el, Rook.class);
            }
            case "KNIGHT" -> {
                return new Gson().fromJson(el, Knight.class);
            }
            default -> {
                throw new JsonParseException("Chess Type Not Found");
            }
        }
    }
}