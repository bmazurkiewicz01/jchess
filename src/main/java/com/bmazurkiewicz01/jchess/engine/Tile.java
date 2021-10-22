package com.bmazurkiewicz01.jchess.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.bmazurkiewicz01.jchess.engine.TileUtils.*;

public class Tile extends Rectangle {
    private Piece piece;

    public Tile(int x, int y, boolean isLight) {
        setWidth(TILE_WIDTH);
        setHeight(TILE_HEIGHT);

        setTranslateX(x * TILE_WIDTH);
        setTranslateY(y * TILE_HEIGHT);


        if (isLight) setFill(Color.valueOf(LIGHT_COLOR));
        else setFill(Color.valueOf(DARK_COLOR));
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
