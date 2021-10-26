package com.bmazurkiewicz01.jchess.engine.tile;

import com.bmazurkiewicz01.jchess.engine.piece.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.bmazurkiewicz01.jchess.engine.tile.TileUtils.*;

public class Tile extends Rectangle {
    private Piece piece;

    public Tile(boolean isLight) {
        setWidth(TILE_WIDTH);
        setHeight(TILE_HEIGHT);

        if (isLight) setFill(Color.valueOf(LIGHT_COLOR));
        else setFill(Color.valueOf(DARK_COLOR));
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
