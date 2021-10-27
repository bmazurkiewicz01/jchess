package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.engine.piece.*;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public GridPane gridPane;

    private Tile[][] board;
    private List<Piece> pieces;

    private Piece clickedPiece;
    private Tile clickedTile;
    private PieceColor currentTurn;

    public void initialize() {
        board = new Tile[TileUtils.ROW_SIZE][TileUtils.COLUMN_SIZE];
        pieces = new ArrayList<>();
        currentTurn = PieceColor.WHITE;

        initializeBoard(board, pieces);
        initializePieces(pieces);
    }

    private void initializeBoard(Tile[][] board, List<Piece> pieces) {
        for (int y = 0; y < TileUtils.ROW_SIZE; y++) {
            for (int x = 0; x < TileUtils.COLUMN_SIZE; x++) {

                Tile tile = new Tile((x + y) % 2 == 0);
                tile.setStyle("-fx-stroke-width: 1; -fx-stroke: black");
                tile.setOnMousePressed(e -> handleOnTilePressed(tile));

                board[x][y] = tile;
                gridPane.add(tile, x, y);

                if (y == 1) {
                    pieces.add(new Pawn(x, y, PieceColor.BLACK, board));
                } else if (y == 6) {
                    pieces.add(new Pawn(x, y, PieceColor.WHITE, board));
                }
            }
        }

        Knight blackKnight1 = new Knight(6, 0, PieceColor.BLACK, board);
        Knight blackKnight2 = new Knight(1, 0, PieceColor.BLACK, board);
        Knight whiteKnight1 = new Knight(6, 7, PieceColor.WHITE, board);
        Knight whiteKnight2 = new Knight(1, 7, PieceColor.WHITE, board);

        pieces.add(blackKnight1);
        pieces.add(blackKnight2);
        pieces.add(whiteKnight1);
        pieces.add(whiteKnight2);

        Bishop blackBishop1 = new Bishop(5, 0, PieceColor.BLACK, board);
        Bishop blackBishop2 = new Bishop(2, 0, PieceColor.BLACK, board);
        Bishop whiteBishop1 = new Bishop(5, 7, PieceColor.WHITE, board);
        Bishop whiteBishop2 = new Bishop(2, 7, PieceColor.WHITE, board);

        pieces.add(blackBishop1);
        pieces.add(blackBishop2);
        pieces.add(whiteBishop1);
        pieces.add(whiteBishop2);

        Rook blackRook1 = new Rook(7, 0, PieceColor.BLACK, board);
        Rook blackRook2 = new Rook(0, 0, PieceColor.BLACK, board);
        Rook whiteRook1 = new Rook(7, 7, PieceColor.WHITE, board);
        Rook whiteRook2 = new Rook(0, 7, PieceColor.WHITE, board);

        pieces.add(blackRook1);
        pieces.add(blackRook2);
        pieces.add(whiteRook1);
        pieces.add(whiteRook2);

        Queen blackQueen = new Queen(3, 0, PieceColor.BLACK, board);
        Queen whiteQueen = new Queen(3, 7, PieceColor.WHITE, board);

        pieces.add(blackQueen);
        pieces.add(whiteQueen);


        King blackKing = new King(4, 0, PieceColor.BLACK, board);
        King whiteKing = new King(4, 7, PieceColor.WHITE, board);

        pieces.add(blackKing);
        pieces.add(whiteKing);

    }

    private void initializePieces(List<Piece> pieces) {
        for (Piece piece : pieces) {
            piece.setOnMousePressed(e -> handleOnTilePressed(board[piece.getPieceX()][piece.getPieceY()]));
            board[piece.getPieceX()][piece.getPieceY()].setPiece(piece);
            gridPane.add(piece, piece.getPieceX(), piece.getPieceY());
        }
    }

    private void handleOnTilePressed(Tile tile) {
        if (clickedPiece != null) {
            if (currentTurn == clickedPiece.getPieceColor()) {
                if (tile.getPiece() == null || tile.getPiece().getPieceColor() != clickedPiece.getPieceColor()) {

                    int row = GridPane.getRowIndex(tile);
                    int column = GridPane.getColumnIndex(tile);

                    System.out.printf("row %s column %s\n", row, column);

                    if (clickedPiece.isValidMove(column, row)) {
                        int oldPieceX = clickedPiece.getPieceX();
                        int oldPieceY = clickedPiece.getPieceY();
                        Piece oldTilePiece = null;

                        if (tile.getPiece() != null) {
                            oldTilePiece = tile.getPiece();
                            oldTilePiece.setVisible(false);
                        }

                        GridPane.setColumnIndex(clickedPiece, GridPane.getColumnIndex(tile));
                        GridPane.setRowIndex(clickedPiece, GridPane.getRowIndex(tile));

                        clickedPiece.toFront();
                        clickedPiece.setPieceX(column);
                        clickedPiece.setPieceY(row);

                        clickedTile.toBack();
                        clickedTile.setPiece(null);
                        tile.setPiece(clickedPiece);

                        King king = getKing(currentTurn);
                        System.out.printf("clickedPiece x = %d clickedPiece y = %d king x = %d king y = %d", clickedPiece.getPieceX(), clickedPiece.getPieceY(), king.getPieceX(), king.getPieceY());
                        if (king != null) {
                            if (king.isSafe(king.getPieceX(), king.getPieceY())) {
                                if (currentTurn == PieceColor.WHITE) currentTurn = PieceColor.BLACK;
                                else currentTurn = PieceColor.WHITE;
                            } else {
                                GridPane.setColumnIndex(clickedPiece, oldPieceX);
                                GridPane.setRowIndex(clickedPiece, oldPieceY);

                                clickedPiece.setPieceX(oldPieceX);
                                clickedPiece.setPieceY(oldPieceY);
                                clickedPiece.setVisible(true);

                                clickedTile.setPiece(clickedPiece);

                                if (oldTilePiece != null) {
                                    tile.setPiece(oldTilePiece);
                                    oldTilePiece.setVisible(true);
                                } else {
                                    tile.setPiece(null);
                                }

                            }
                        }
                    } else {
                        System.out.println("Invalid move!!!!");
                    }
                } else {
                    System.out.println("can't move piece");
                }
            } else {
                System.out.println("Not your turn");
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

    private King getKing(PieceColor pieceColor) {
        for (int y = 0; y < TileUtils.ROW_SIZE; y++) {
            for (int x = 0; x < TileUtils.COLUMN_SIZE; x++) {
                Piece piece = board[x][y].getPiece();
                if (piece != null && piece.getPieceColor() == pieceColor && piece instanceof King) {
                    return (King) piece;
                }
            }
        }
        return null;
    }
}