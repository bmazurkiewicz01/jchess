package com.bmazurkiewicz01.jchess.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.bmazurkiewicz01.jchess.engine.TileUtils.*;

public class Tile extends Rectangle {
    private Piece piece;
    private int x, y;

    public Tile(int x, int y, boolean isLight) {
        this.x = x;
        this.y = y;
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
