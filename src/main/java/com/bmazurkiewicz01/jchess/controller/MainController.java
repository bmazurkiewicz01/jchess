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

        Knight blackKnight1 = new Knight(6, 0, PieceColor.DARK, board, gridPane);
        Knight blackKnight2 = new Knight(1, 0, PieceColor.DARK, board, gridPane);
        Knight whiteKnight1 = new Knight(6, 7, PieceColor.LIGHT, board, gridPane);
        Knight whiteKnight2 = new Knight(1, 7, PieceColor.LIGHT, board, gridPane);

        blackKnight1.setOnMousePressed(e -> handleOnTilePressed(board[blackKnight1.getPieceX()][blackKnight1.getPieceY()]));
        blackKnight2.setOnMousePressed(e -> handleOnTilePressed(board[blackKnight2.getPieceX()][blackKnight2.getPieceY()]));
        whiteKnight1.setOnMousePressed(e -> handleOnTilePressed(board[whiteKnight1.getPieceX()][whiteKnight1.getPieceY()]));
        whiteKnight2.setOnMousePressed(e -> handleOnTilePressed(board[whiteKnight2.getPieceX()][whiteKnight2.getPieceY()]));

        board[6][0].setPiece(blackKnight1);
        board[1][0].setPiece(blackKnight2);
        board[6][7].setPiece(whiteKnight1);
        board[1][7].setPiece(whiteKnight2);

        gridPane.add(blackKnight1, 6, 0);
        gridPane.add(blackKnight2, 1, 0);
        gridPane.add(whiteKnight1, 6, 7);
        gridPane.add(whiteKnight2, 1, 7);
    }

    public void handleOnTilePressed(Tile tile) {
        if (clickedPiece != null) {
            if (tile.getPiece() == null || tile.getPiece().getPieceColor() != clickedPiece.getPieceColor()) {

                int row = GridPane.getRowIndex(tile);
                int column = GridPane.getColumnIndex(tile);

                System.out.printf("row %s column %s\n", row, column);

                if (clickedPiece.isValidMove(column, row, tile)) {
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