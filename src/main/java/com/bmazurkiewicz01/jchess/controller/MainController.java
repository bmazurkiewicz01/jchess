package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.engine.Pawn;
import com.bmazurkiewicz01.jchess.engine.Piece;
import com.bmazurkiewicz01.jchess.engine.PieceColor;
import com.bmazurkiewicz01.jchess.engine.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public Pane board;

    List<Tile> tiles;
    List<Piece> pieces;

    public void initialize() {
        tiles = new ArrayList<>();
        pieces = new ArrayList<>();

        for (int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Tile tile = new Tile(x, y, (x + y) % 2 == 0);
                tiles.add(tile);
                if (x == 1) {
                    Pawn pawn = new Pawn(x, y, PieceColor.DARK);
                    pieces.add(pawn);
                    tile.setPiece(pawn);
                }
            }
        }

        board.getChildren().addAll(tiles);
        board.getChildren().addAll(pieces);

        board.getChildren().forEach(System.out::println);
    }
}