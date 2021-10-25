package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.engine.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.spreadsheet.Grid;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public GridPane gridPane;

    private Node[][] board;

    private Piece clickedPiece;
    private Tile clickedTile;

    public void initialize() {
        board = new Node[TileUtils.ROW_SIZE][TileUtils.COLUMN_SIZE];

        for (int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                Tile tile = new Tile(x, y, (x + y) % 2 == 0);
                board[x][y] = tile;
                gridPane.add(tile, x, y);
                Pawn pawn = null;
                if (y == 1) {
                    pawn = new Pawn(x, y, PieceColor.DARK, board, gridPane);
                } else if (y == 6) {
                    pawn = new Pawn(x, y, PieceColor.LIGHT, board, gridPane);
                }
                if (pawn != null) {
                    tile.setPiece(pawn);
                    gridPane.add(pawn, x, y);
                }
                tile.setOnMousePressed(e -> {
                    if (clickedPiece != null) {
                        if (tile.getPiece() == null || tile.getPiece().getPieceColor() != clickedPiece.getPieceColor()) {
                            int row = GridPane.getRowIndex(tile);
                            int column = GridPane.getColumnIndex(tile);

                            System.out.printf("row %s column %s\n", row, column);

                            if (clickedPiece.isValidMove(column, row)) {

                                if (tile.getPiece() != null) {
                                    tile.getPiece().setVisible(false);
                                }

                                GridPane.setColumnIndex(clickedPiece, GridPane.getColumnIndex(tile));
                                GridPane.setRowIndex(clickedPiece, GridPane.getRowIndex(tile));

                                clickedPiece.toFront();
                                tile.setPiece(clickedPiece);
                                clickedPiece.setPieceX(column);
                                clickedPiece.setPieceY(row);

                                clickedTile.toBack();
                                clickedTile.setPiece(null);

                                clickedPiece = null;
                                clickedTile = null;
                            } else {
                                System.out.println("Invalid move!!!!");
                                clickedPiece = null;
                                clickedTile = null;
                            }

                        } else {
                            System.out.println("can't move piece");
                            clickedPiece = null;
                            clickedTile = null;
                        }
                    } else {
                        if (tile.getPiece() != null) {
                            clickedPiece = tile.getPiece();
                            clickedTile = tile;
                            System.out.println("Piece clicked");
                        } else {
                            System.out.println("Invalid click: there is no piece");
                            clickedPiece = null;
                            clickedTile = null;
                        }
                    }
                });
            }
        }
    }
}