package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.engine.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MainController {
    @FXML
    public GridPane gridPane;

    private Tile[][] board;

    private Piece clickedPiece;
    private Tile clickedTile;

    public void initialize() {
        board = new Tile[TileUtils.ROW_SIZE][TileUtils.COLUMN_SIZE];

        for (int y = 0; y < TileUtils.ROW_SIZE; y++) {
            for (int x = 0; x < TileUtils.COLUMN_SIZE; x++) {

                Tile tile = new Tile((x + y) % 2 == 0);
                tile.setStyle("-fx-stroke-width: 1; -fx-stroke: black");
                tile.setOnMousePressed(e -> handleOnTilePressed(tile));

                board[x][y] = tile;
                gridPane.add(tile, x, y);
                Pawn pawn = null;
                if (y == 1) {
                    pawn = new Pawn(x, y, PieceColor.DARK, board, gridPane);
                } else if (y == 6) {
                    pawn = new Pawn(x, y, PieceColor.LIGHT, board, gridPane);
                }
                if (pawn != null) {
                    Pawn finalPawn = pawn;
                    pawn.setOnMousePressed(e -> handleOnTilePressed(board[finalPawn.getPieceX()][finalPawn.getPieceY()]));
                    tile.setPiece(pawn);
                    gridPane.add(pawn, x, y);
                }
            }
        }
    }

    public void handleOnTilePressed(Tile tile) {
        if (clickedPiece != null) {
            if (tile.getPiece() == null || tile.getPiece().getPieceColor() != clickedPiece.getPieceColor()) {

                int row = GridPane.getRowIndex(tile);
                int column = GridPane.getColumnIndex(tile);

                System.out.printf("row %s column %s\n", row, column);

                if (clickedPiece.isValidMove(column, row, tile)) {
                    if (tile.getPiece() != null) {
                        tile.getPiece().setVisible(false);
                    }

                    GridPane.setColumnIndex(clickedPiece, GridPane.getColumnIndex(tile));
                    GridPane.setRowIndex(clickedPiece, GridPane.getRowIndex(tile));

                    clickedPiece.toFront();
                    clickedPiece.setPieceX(column);
                    clickedPiece.setPieceY(row);

                    clickedTile.toBack();
                    clickedTile.setPiece(null);

                    tile.setPiece(clickedPiece);
                } else {
                    System.out.println("Invalid move!!!!");
                }
            } else {
                System.out.println("can't move piece");
            }
            clickedTile.setStyle("-fx-stroke-width: 1; -fx-stroke: black");
            clickedTile = null;
            clickedPiece = null;
        } else {
            if (tile.getPiece() != null) {
                clickedPiece = tile.getPiece();

                clickedTile = tile;
                clickedTile.setStyle("-fx-stroke: red; -fx-stroke-width: 1;");
                System.out.println("Piece clicked");
            } else {
                System.out.println("Invalid click: there is no piece");
                clickedPiece = null;
                clickedTile = null;
            }
        }
    }
}