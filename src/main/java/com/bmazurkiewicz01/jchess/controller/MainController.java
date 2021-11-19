package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.piece.*;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import com.bmazurkiewicz01.jchess.view.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public AnchorPane root;
    @FXML
    public GridPane gridPane;

    private Tile[][] board;
    private List<Piece> pieces;

    private Piece clickedPiece;
    private Tile clickedTile;
    private PieceColor currentTurn;

    public void initialize() {
        startGame();
    }

    public void startGame() {
        board = new Tile[TileUtils.ROW_SIZE][TileUtils.COLUMN_SIZE];
        currentTurn = PieceColor.WHITE;
        pieces = new ArrayList<>();

        gridPane.getChildren().clear();
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
                    pieces.add(new Pawn(x, y, PieceColor.BLACK, board, this));
                } else if (y == 6) {
                    pieces.add(new Pawn(x, y, PieceColor.WHITE, board, this));
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


        King blackKing = new King(4, 0, PieceColor.BLACK, board, gridPane);
        King whiteKing = new King(4, 7, PieceColor.WHITE, board, gridPane);

        pieces.add(blackKing);
        pieces.add(whiteKing);

    }

    private void initializePieces(List<Piece> pieces) {
        for (Piece piece : pieces) {
            addPiece(piece);
        }
    }

    public void addPiece(Piece piece) {
        piece.setOnMousePressed(e -> handleOnTilePressed(board[piece.getPieceX()][piece.getPieceY()]));
        board[piece.getPieceX()][piece.getPieceY()].setPiece(piece);
        gridPane.add(piece, piece.getPieceX(), piece.getPieceY());
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public Piece showPawnPromotionPane(PieceColor pieceColor) {

        final Stage pawnPromotionDialog = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ View.PAWN_PROMOTION_DIALOG.getFileName()));
        Parent root;
        try {
            root = fxmlLoader.load();

            pawnPromotionDialog.setScene(new Scene(root));
            pawnPromotionDialog.getScene().setFill(Color.TRANSPARENT);
            pawnPromotionDialog.initModality(Modality.APPLICATION_MODAL);
            pawnPromotionDialog.initOwner(this.root.getScene().getWindow());
            pawnPromotionDialog.initStyle(StageStyle.TRANSPARENT);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        PawnPromotionDialogController controller = fxmlLoader.getController();
        controller.setBoard(board);
        controller.setPieceColor(pieceColor);

        pawnPromotionDialog.showAndWait();

        return controller.getSelectedPiece();
    }

    private void handleOnTilePressed(Tile tile) {
        if (clickedPiece != null) {
            if (currentTurn == clickedPiece.getPieceColor()) {

                Piece oldPiece = null;
                if (tile.getPiece() != null) {
                    oldPiece = tile.getPiece();
                    pieces.remove(oldPiece);
                }

                if (clickedPiece.move(tile, clickedTile, getKing(currentTurn), false)) {
                    if (currentTurn == PieceColor.WHITE) currentTurn = PieceColor.BLACK;
                    else currentTurn = PieceColor.WHITE;

                    if (isCheckmate(currentTurn)) {
                        System.out.println("YOU WIN");
                        startGame();
                    }
                } else {
                    if (oldPiece != null) {
                        pieces.add(oldPiece);
                    }
                    System.out.println("Invalid move");
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

    private boolean isCheckmate(PieceColor pieceColor) {
        King king = getKing(pieceColor);
        for (Piece piece : pieces) {
            for (int y = 0; y < TileUtils.ROW_SIZE; y++) {
                for (int x = 0; x < TileUtils.COLUMN_SIZE; x++) {
                    if (piece.getPieceColor() == pieceColor && piece.move(board[x][y], board[piece.getPieceX()][piece.getPieceY()], king, true)) {
                        return false;
                    }
                }
            }
        }
        return true;
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