package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import javafx.scene.image.Image;

import java.util.Objects;

public class Rook extends Piece {

    public Rook(int x, int y, PieceColor pieceColor, Tile[][] board) {
        super(x, y, pieceColor, board);
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-rook.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-rook.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y, Tile tile) {
        if (pathClear(x, y)) {
            if (pieceX == x || pieceY == y) {
                if (tile.getPiece() != null) {
                    tile.getPiece().setVisible(false);
                }
                return true;
            }
        }

        return false;
    }

    private boolean pathClear(int endX, int endY) {
        if (endX == pieceX && endY > pieceY) {
            for (int boardY = pieceY + 1; boardY < endY; boardY++) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }
        } else if (endX == pieceX && endY < pieceY) {
            for (int boardY = pieceY - 1; boardY > endY; boardY--) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }

        } else if (endY == pieceY && endX > pieceX) {
            for (int boardX = pieceX + 1; boardX < endX; boardX++) {
                if (board[boardX][pieceY].getPiece() != null) {
                    return false;
                }
            }
        } else if (endY == pieceY && endX < pieceX) {
            for (int boardX = pieceX - 1; boardX > endX; boardX--) {
                if (board[boardX][pieceY].getPiece() != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
