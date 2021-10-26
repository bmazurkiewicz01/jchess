package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
    protected final PieceColor pieceColor;
    protected int pieceX, pieceY;
    protected Tile[][] board;

    public Piece(int x, int y, PieceColor pieceColor, Tile[][] board) {
        this.pieceColor = pieceColor;
        this.pieceX = x;
        this.pieceY = y;
        this.board = board;

        setFitWidth(TileUtils.TILE_WIDTH);
        setFitHeight(TileUtils.TILE_HEIGHT);
        managedProperty().bind(visibleProperty());
    }

    public abstract boolean isValidMove(int x, int y, Tile tile);

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public int getPieceX() {
        return pieceX;
    }

    public int getPieceY() {
        return pieceY;
    }

    public void setPieceX(int pieceX) {
        this.pieceX = pieceX;
    }

    public void setPieceY(int pieceY) {
        this.pieceY = pieceY;
    }
}
