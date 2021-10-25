package com.bmazurkiewicz01.jchess.engine;

import com.bmazurkiewicz01.jchess.App;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class Pawn extends Piece {
    private boolean firstMove;

    public Pawn(int x, int y, PieceColor pieceColor, Node[][] board, GridPane gridPane) {
        super(x, y, pieceColor, board, gridPane);
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.LIGHT ? App.class.getResource("images/white-pawn.png").toString() : App.class.getResource("images/black-pawn.png").toString()));
    }

    @Override
    public void move(int x, int y) {

    }

    private boolean isMovingForward(int y) {
        if (getPieceColor() == PieceColor.LIGHT) {
            return y < pieceY;
        } else {
            return y > pieceY;
        }
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);

        if (isMovingForward(y)) {
            if (firstMove) {
                if (diffY > 0 && diffY <= 2 && diffX == 0) {
                    firstMove = false;
                    return true;
                }
            } else {
                return diffY == 1 && diffX == 0;
            }
            return false;
        }

        return false;
    }
}
