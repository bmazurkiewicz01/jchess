package com.bmazurkiewicz01.jchess.controller;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.engine.piece.*;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class PawnPromotionDialogController {
    @FXML
    public AnchorPane root;

    @FXML
    public Button queenPromotion;
    @FXML
    public Button rookPromotion;
    @FXML
    public Button bishopPromotion;
    @FXML
    public Button knightPromotion;

    public Tile[][] board;
    public PieceColor pieceColor;
    public Piece selectedPiece;

    public void initialize() {
        queenPromotion.setOnMousePressed(e -> {
            selectedPiece = new Queen(0, 0, pieceColor, board);
            closeStage(e);
        });

        rookPromotion.setOnMousePressed(e -> {
            selectedPiece = new Rook(0, 0, pieceColor, board);
            closeStage(e);
        });

        bishopPromotion.setOnMousePressed(e -> {
            selectedPiece = new Bishop(0, 0, pieceColor, board);
            closeStage(e);
        });

        knightPromotion.setOnMousePressed(e -> {
            selectedPiece = new Knight(0, 0, pieceColor, board);
            closeStage(e);
        });
    }

    public void closeStage(Event actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;

        if (pieceColor == PieceColor.WHITE) {
            root.getStylesheets().add(Objects.requireNonNull(App.class.getResource("view/css/white-pawn-promotion-dialog-style.css")).toExternalForm());
        } else {
            root.getStylesheets().add(Objects.requireNonNull(App.class.getResource("view/css/black-pawn-promotion-dialog-style.css")).toExternalForm());
        }
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }
}
