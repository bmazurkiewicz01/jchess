package com.bmazurkiewicz01.jchess.engine.piece;

import com.bmazurkiewicz01.jchess.App;
import com.bmazurkiewicz01.jchess.controller.MainController;
import com.bmazurkiewicz01.jchess.engine.tile.Tile;
import com.bmazurkiewicz01.jchess.engine.tile.TileUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class King extends Piece {
    private boolean firstMove;
    private final GridPane gridPane;

    public King(int x, int y, PieceColor pieceColor, Tile[][] board, GridPane gridPane) {
        super(x, y, pieceColor, board);
        this.gridPane = gridPane;
        firstMove = true;
        setImage(new Image(pieceColor == PieceColor.WHITE ? Objects.requireNonNull(App.class.getResource("images/white-king.png")).toString()
                : Objects.requireNonNull(App.class.getResource("images/black-king.png")).toString()));
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);

        return isSafe(x, y) && ((diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 1) || canCastle(x, y) != null);
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

                Rook rook = canCastle(column, row);
                if (rook != null) {
                    int rookColumn = GridPane.getColumnIndex(rook);
                    int rookRow = GridPane.getRowIndex(rook);

                    if (rookColumn > pieceX) {
                        GridPane.setColumnIndex(rook, rookColumn - 2);
                    } else {
                        GridPane.setColumnIndex(rook, rookColumn + 3);
                    }

                    board[rookColumn][rookRow].setPiece(null);

                    board[GridPane.getColumnIndex(rook)][GridPane.getRowIndex(rook)].setPiece(rook);
                    rook.setPieceX(GridPane.getColumnIndex(rook));
                }

                GridPane.setColumnIndex(this, GridPane.getColumnIndex(tile));
                GridPane.setRowIndex(this, GridPane.getRowIndex(tile));

                toFront();
                setPieceX(column);
                setPieceY(row);

                clickedTile.toBack();
                clickedTile.setPiece(null);
                tile.setPiece(this);

                if (isSafe(getPieceX(), getPieceY())) {
                    firstMove = false;
                    if (testMove) revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                    return true;
                } else {
                    revertMove(tile, clickedTile, oldPieceX, oldPieceY, oldTilePiece);
                }
            }
        }
        return false;
    }

    public boolean isSafe(int x, int y) {
        for (int boardY = 0; boardY < TileUtils.ROW_SIZE; boardY++) {
            for (int boardX = 0; boardX < TileUtils.COLUMN_SIZE; boardX++) {
                Piece piece = board[boardX][boardY].getPiece();
                if (piece != null && piece.getPieceColor() != pieceColor) {
                    if (piece instanceof King) {
                        int diffX = Math.abs(piece.getPieceX() - x);
                        int diffY = Math.abs(piece.getPieceY() - y);

                        if ((diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 1)) {
                            return false;
                        }
                    } else if (piece instanceof Pawn) {
                        int diffX = Math.abs(piece.getPieceX() - x);
                        int diffY = Math.abs(piece.getPieceY() - y);

                        if (diffY == 1 && diffX == 1) {
                            return false;
                        }
                    } else if (piece.isValidMove(x, y)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Rook canCastle(int x, int y) {
        int diffX = Math.abs(pieceX - x);
        int diffY = Math.abs(pieceY - y);


        Rook rook = null;
        if (firstMove && pathClear(x)  && (diffX == 2 && diffY == 0)) {
            if (pieceColor == PieceColor.WHITE) {
                if (pieceX < x) {
                    Piece piece = ((Tile) MainController.getNodeByRowColumnIndex(7, 7, gridPane)).getPiece();
                    if (piece instanceof Rook) {
                        rook = (Rook) piece;
                    }
                } else if (pieceX > x) {
                    Piece piece = ((Tile) MainController.getNodeByRowColumnIndex(7, 0, gridPane)).getPiece();
                    if (piece instanceof Rook) {
                        rook = (Rook) piece;
                    }
                }
            } else {
                if (pieceX < x) {
                    Piece piece = ((Tile) MainController.getNodeByRowColumnIndex(0, 7, gridPane)).getPiece();
                    if (piece instanceof Rook) {
                        rook = (Rook) piece;
                    }
                } else if (pieceX > x) {
                    Piece piece = ((Tile) MainController.getNodeByRowColumnIndex(0, 0, gridPane)).getPiece();
                    if (piece instanceof Rook) {
                        rook = (Rook) piece;
                    }
                }
            }
        }
        if (rook != null && pathClear(rook.getPieceX())) return rook;
        return null;
    }

    private boolean pathClear(int endX) {
        if (endX > pieceX) {
            for (int boardX = pieceX + 1; boardX < endX; boardX++) {
                if (board[boardX][pieceY].getPiece() != null || !isSafe(boardX, pieceY)) {
                    return false;
                }
            }
        } else if (endX < pieceX) {
            for (int boardX = pieceX - 1; boardX > endX; boardX--) {
                if (board[boardX][pieceY].getPiece() != null || !isSafe(boardX, pieceY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
