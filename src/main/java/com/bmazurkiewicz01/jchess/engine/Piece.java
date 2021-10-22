package com.bmazurkiewicz01.jchess.engine;

import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
    private final PieceColor pieceColor;

    public Piece(int x, int y, PieceColor pieceColor) {
        this.pieceColor = pieceColor;

        setFitWidth(TileUtils.TILE_WIDTH);
        setFitHeight(TileUtils.TILE_HEIGHT);
        setTranslateX(x * TileUtils.TILE_WIDTH);
        setTranslateY(y * TileUtils.TILE_HEIGHT);
    }

    public abstract void move();

    public PieceColor getPieceColor() {
        return pieceColor;
    }


}
