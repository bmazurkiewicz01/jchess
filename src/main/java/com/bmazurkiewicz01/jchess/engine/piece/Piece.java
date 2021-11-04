package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class Piece extends ImageView {
    protected PieceColor pieceColor;
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

    public boolean move(Tile tile, Tile clickedTile, King king, boolean testMove) {
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        if (tile.getPiece() == null || tile.getPiece().getPieceColor() != getPieceColor()) {
            if (isValidMove(column, row)) {

                int oldPieceX = getPieceX();
                int oldPieceY = getPieceY();

                Piece oldTilePiece = null;
                if (tile.getPiece() != null) {
                    oldTilePiece = tile.getPiece();
                    oldTilePiece.setVisible(false);
                }

                GridPane.setColumnIndex(this, GridPane.getColumnIndex(tile));
                GridPane.setRowIndex(this, GridPane.getRowIndex(tile));

                toFront();
                setPieceX(column);
                setPieceY(row);

                clickedTile.toBack();
                clickedTile.setPiece(null);
                tile.setPiece(this);

                if (king != null) {
                    if (king.isSafe(king.getPieceX(), king.getPieceY())) {
                        if (testMove) revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                        return true;
                    } else {
                        revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                    }
                }
            }
        }
        return false;
    }

    public void revertMove(Tile tile, Tile clickedTile, int oldPieceX, int oldPieceY, Piece oldTilePiece) {
        GridPane.setColumnIndex(this, oldPieceX);
        GridPane.setRowIndex(this, oldPieceY);

        setPieceX(oldPieceX);
        setPieceY(oldPieceY);
        setVisible(true);

        if (this instanceof Pawn) {
            Pawn pawn = (Pawn) this;
            if (!pawn.isFirstMove()) {
                pawn.setFirstMove(true);
            }
        } else if (this instanceof King) {
            King finalKing = (King) this;
            if (!finalKing.isFirstMove()) {
                finalKing.setFirstMove(true);
            }
        }

        clickedTile.setPiece(this);

        if (oldTilePiece != null) {
            tile.setPiece(oldTilePiece);
            oldTilePiece.setVisible(true);
        } else {
            tile.setPiece(null);
        }
    }

    public abstract boolean isValidMove(int x, int y);

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
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
