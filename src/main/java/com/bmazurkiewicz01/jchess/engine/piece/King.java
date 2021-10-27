package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import javafx.scene.image.Image;

import java.util.Objects;

public class King extends Piece {

    public King(int x, int y, PieceColor pieceColor, Tile[][] board) {
        super(x, y, pieceColor, board);
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-king.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-king.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y, Tile tile) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);

        if ((diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 1)) {
            if (tile.getPiece() != null) {
                tile.getPiece().setVisible(false);
            }
            return true;
        }

        return false;
    }

}
