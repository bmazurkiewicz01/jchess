package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import javafx.scene.image.Image;

import java.util.Objects;

public class Bishop extends Piece {

    public Bishop(int x, int y, PieceColor pieceColor, Tile[][] board) {
        super(x, y, pieceColor, board);
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-bishop.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-bishop.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);

        if (pathClear(x, y)) {
            return diffX == diffY && pieceX != x && pieceY != y;
        }

        return false;
    }

    private boolean pathClear(int endX, int endY) {
        if (pieceX < endX && pieceY < endY) {
            int boardX = pieceX + 1;
            int boardY = pieceY + 1;
            while (boardX < endX && boardY < endY) {
                if (board[boardX][boardY].getPiece() != null) {
                    return false;
                }
                boardX++;
                boardY++;
            }
        } else if (pieceX > endX && pieceY > endY){
            int boardX = pieceX - 1;
            int boardY = pieceY - 1;
            while (boardX > endX && boardY > endY) {
                if (board[boardX][boardY].getPiece() != null) {
                    return false;
                }
                boardX--;
                boardY--;
            }
        } else if (pieceX < endX && pieceY > endY) {
            int boardX = pieceX + 1;
            int boardY = pieceY - 1;
            while (boardX < endX && boardY > endY) {
                if (board[boardX][boardY].getPiece() != null) {
                    return false;
                }
                boardX++;
                boardY--;
            }
        } else if (pieceX > endX && pieceY < endY) {
            int boardX = pieceX - 1;
            int boardY = pieceY + 1;
            while (boardX > endX && boardY < endY) {
                if (board[boardX][boardY].getPiece() != null) {
                    return false;
                }
                boardX--;
                boardY++;
            }
        }
        return true;
    }
}
