package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.controller.MainController;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Pawn extends Piece {
    private boolean firstMove;
    private final MainController mainController;

    public Pawn(int x, int y, PieceColor pieceColor, Tile[][] board, MainController mainController) {
        super(x, y, pieceColor, board);
        this.mainController = mainController;
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-pawn.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-pawn.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);


        if (isMovingForward(y) && pathClear(y)) {
            if (board[x][y].getPiece() != null) {
                return diffY == 1 && diffX == 1;
            } else {
                if (firstMove) {
                    if (diffY > 0 && diffY <= 2 && diffX == 0) {
                        firstMove = false;
                        return true;
                    }
                } else {
                    return diffY == 1 && diffX == 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean move(Tile tile, Tile clickedTile, King king, boolean testMove) {
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        if (tile.getPiece() == null || tile.getPiece().getPieceColor() != getPieceColor()) {
            if (isValidMove(column, row)) {

                int oldPieceX = getPieceX();
                int oldPieceY = getPieceY();

                Piece oldTilePiece = null;
                if (tile.getPiece() != null) {
                    oldTilePiece = tile.getPiece();
                    oldTilePiece.setVisible(false);
                }

                GridPane.setColumnIndex(this, GridPane.getColumnIndex(tile));
                GridPane.setRowIndex(this, GridPane.getRowIndex(tile));

                toFront();
                setPieceX(column);
                setPieceY(row);

                clickedTile.toBack();
                clickedTile.setPiece(null);
                tile.setPiece(this);

                if (king != null) {
                    if (king.isSafe(king.getPieceX(), king.getPieceY())) {

                        if(canPromote(pieceY)) {
                            try {
                                Piece piece = mainController.showPawnPromotionPane(pieceX * TileUtils.TILE_WIDTH, pieceY * TileUtils.TILE_HEIGHT);
                                piece.setPieceX(pieceX);
                                piece.setPieceY(pieceY);
                                piece.setPieceColor(pieceColor);
                                mainController.addPiece(piece);
                                tile.setPiece(piece);

                                mainController.removePiece(this);
                                setVisible(false);
                            }  catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (testMove) revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                        return true;
                    } else {
                        revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                    }
                }
            }
        }
        return false;
    }

    private boolean pathClear(int endY) {
        if (getPieceColor() == PieceColor.WHITE) {
            for (int boardY = pieceY - 1; boardY > endY; boardY--) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }
        } else {
            for (int boardY = pieceY + 1; boardY < endY; boardY++) {
                if (board[pieceX][boardY].getPiece() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMovingForward(int y) {
        if (getPieceColor() == PieceColor.WHITE) {
            return y < pieceY;
        } else {
            return y > pieceY;
        }
    }

    private boolean canPromote(int y) {
        if (getPieceColor() == PieceColor.WHITE) {
            return y == 0;
        } else {
            return y == 7;
        }
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
