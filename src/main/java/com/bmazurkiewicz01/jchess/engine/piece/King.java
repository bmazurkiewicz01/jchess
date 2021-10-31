package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.scene.image.Image;

import java.util.Objects;

public class King extends Piece {
    private boolean firstMove;

    public King(int x, int y, PieceColor pieceColor, Tile[][] board) {
        super(x, y, pieceColor, board);
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-king.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-king.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);

        if (isSafe(x, y) && ((diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 1))) {
            firstMove = false;
            return true;
        }
        return false;
    }

    public boolean isSafe(int x, int y) {
        for (int boardY = 0; boardY < TileUtils.ROW_SIZE; boardY++) {
            for (int boardX = 0; boardX < TileUtils.COLUMN_SIZE; boardX++) {
                Piece piece = board[boardX][boardY].getPiece();
                if (piece != null && piece.getPieceColor() != pieceColor) {
                    if (piece instanceof King) {
                        int diffX = Math.abs(piece.getPieceX() - x);
                        int diffY = Math.abs(piece.getPieceY() - y);

                        if ((diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 1)) {
                            return false;
                        }
                    } else if(piece instanceof Pawn) {
                        int diffX = Math.abs(piece.getPieceX() - x);
                        int diffY = Math.abs(piece.getPieceY() - y);

                        if (diffY == 1 && diffX == 1) {
                            return false;
                        }
                    } else if (piece.isValidMove(x, y)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
