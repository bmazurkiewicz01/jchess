package com.bmazurkiewicz01.jchess.engine;

import com.bmazurkiewicz01.jchess.App;
import javafx.scene.image.Image;

public class Pawn extends Piece {

    public Pawn(int x, int y, PieceColor pieceColor) {
        super(x, y, pieceColor);
        setImage(new Image(pieceColor == PieceColor.LIGHT ? App.class.getResource("images/white-pawn.png").toString() : App.class.getResource("images/black-pawn.png").toString()));
    }

    @Override
    public void move() {

    }
}
