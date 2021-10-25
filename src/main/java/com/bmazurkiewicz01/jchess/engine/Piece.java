package com.bmazurkiewicz01.jchess.engine;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class Piece extends ImageView {
    private final PieceColor pieceColor;
    protected int pieceX, pieceY;
    protected Node[][] board;
    protected GridPane gridPane;

    public Piece(int x, int y, PieceColor pieceColor, Node[][] board, GridPane gridPane) {
        this.pieceColor = pieceColor;
        this.pieceX = x;
        this.pieceY = y;
        this.board = board;
        this.gridPane = gridPane;

        setFitWidth(TileUtils.TILE_WIDTH);
        setFitHeight(TileUtils.TILE_HEIGHT);
        managedProperty().bind(visibleProperty());
    }

    public abstract void move(int x, int y);

    public abstract boolean isValidMove(int x, int y);

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
