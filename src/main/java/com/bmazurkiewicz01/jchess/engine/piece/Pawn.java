package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import javafx.scene.image.Image;

import java.util.Objects;

public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn(int x, int y, PieceColor pieceColor, Tile[][] board) {
        super(x, y, pieceColor, board);
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-pawn.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-pawn.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y, Tile tile) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);


        if (isMovingForward(y) && pathClear(y)) {
            if (tile.getPiece() != null && tile.getPiece().getPieceColor() != pieceColor) {
                if (diffY == 1 && diffX == 1) {
                    tile.getPiece().setVisible(false);
                    return true;
                }
            } else {
                if (firstMove) {
                    if (diffY > 0 && diffY <= 2 && diffX == 0) {
                        firstMove = false;
                        return true;
                    }
                } else {
                    return diffY == 1 && diffX == 0;
                }
            }
        }
        return false;
    }

    private boolean pathClear(int endY) {
        if (getPieceColor() == PieceColor.WHITE) {
            for (int boardY = pieceY - 1; boardY > endY; boardY--) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }
        } else {
            for (int boardY = pieceY + 1; boardY < endY; boardY++) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMovingForward(int y) {
        if (getPieceColor() == PieceColor.WHITE) {
            return y < pieceY;
        } else {
            return y > pieceY;
        }
    }
}
