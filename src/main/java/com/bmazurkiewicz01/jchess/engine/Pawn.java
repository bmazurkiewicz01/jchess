package com.bmazurkiewicz01.jchess.engine;

import com.bmazurkiewicz01.jchess.App;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn(int x, int y, PieceColor pieceColor, Tile[][] board, GridPane gridPane) {
        super(x, y, pieceColor, board, gridPane);
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.LIGHT ? Objects.requireNonNull(App.class.getResource("images/white-pawn.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-pawn.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y, Tile tile) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);


        if (isMovingForward(y) && pathClear(y)) {
            if (tile.getPiece() != null) {
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
        if (getPieceColor() == PieceColor.LIGHT) {
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
        if (getPieceColor() == PieceColor.LIGHT) {
            return y < pieceY;
        } else {
            return y > pieceY;
        }
    }
}
