package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.engine.piece.*;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
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
                    pawn = new Pawn(x, y, PieceColor.BLACK, board);
                } else if (y == 6) {
                    pawn = new Pawn(x, y, PieceColor.WHITE, board);
                }

                if (pawn != null) {
                    Pawn finalPawn = pawn;
                    pawn.setOnMousePressed(e -> handleOnTilePressed(board[finalPawn.getPieceX()][finalPawn.getPieceY()]));
                    tile.setPiece(pawn);
                    gridPane.add(pawn, x, y);
                }
            }
        }

        Knight blackKnight1 = new Knight(6, 0, PieceColor.BLACK, board);
        Knight blackKnight2 = new Knight(1, 0, PieceColor.BLACK, board);
        Knight whiteKnight1 = new Knight(6, 7, PieceColor.WHITE, board);
        Knight whiteKnight2 = new Knight(1, 7, PieceColor.WHITE, board);

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

        Bishop blackBishop1 = new Bishop(5, 0, PieceColor.BLACK, board);
        Bishop blackBishop2 = new Bishop(2, 0, PieceColor.BLACK, board);
        Bishop whiteBishop1 = new Bishop(5, 7, PieceColor.WHITE, board);
        Bishop whiteBishop2 = new Bishop(2, 7, PieceColor.WHITE, board);

        blackBishop1.setOnMousePressed(e -> handleOnTilePressed(board[blackBishop1.getPieceX()][blackBishop1.getPieceY()]));
        blackBishop2.setOnMousePressed(e -> handleOnTilePressed(board[blackBishop2.getPieceX()][blackBishop2.getPieceY()]));
        whiteBishop1.setOnMousePressed(e -> handleOnTilePressed(board[whiteBishop1.getPieceX()][whiteBishop1.getPieceY()]));
        whiteBishop2.setOnMousePressed(e -> handleOnTilePressed(board[whiteBishop2.getPieceX()][whiteBishop2.getPieceY()]));

        board[5][0].setPiece(blackBishop1);
        board[2][0].setPiece(blackBishop2);
        board[5][7].setPiece(whiteBishop1);
        board[2][7].setPiece(whiteBishop2);

        gridPane.add(blackBishop1, 5, 0);
        gridPane.add(blackBishop2, 2, 0);
        gridPane.add(whiteBishop1, 5, 7);
        gridPane.add(whiteBishop2, 2, 7);

        Rook blackRook1 = new Rook(7, 0, PieceColor.BLACK, board);
        Rook blackRook2 = new Rook(0, 0, PieceColor.BLACK, board);
        Rook whiteRook1 = new Rook(7, 7, PieceColor.WHITE, board);
        Rook whiteRook2 = new Rook(0, 7, PieceColor.WHITE, board);

        blackRook1.setOnMousePressed(e -> handleOnTilePressed(board[blackRook1.getPieceX()][blackRook1.getPieceY()]));
        blackRook2.setOnMousePressed(e -> handleOnTilePressed(board[blackRook2.getPieceX()][blackRook2.getPieceY()]));
        whiteRook1.setOnMousePressed(e -> handleOnTilePressed(board[whiteRook1.getPieceX()][whiteRook1.getPieceY()]));
        whiteRook2.setOnMousePressed(e -> handleOnTilePressed(board[whiteRook2.getPieceX()][whiteRook2.getPieceY()]));

        board[7][0].setPiece(blackRook1);
        board[0][0].setPiece(blackRook2);
        board[7][7].setPiece(whiteRook1);
        board[0][7].setPiece(whiteRook2);

        gridPane.add(blackRook1, 7, 0);
        gridPane.add(blackRook2, 0, 0);
        gridPane.add(whiteRook1, 7, 7);
        gridPane.add(whiteRook2, 0, 7);
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