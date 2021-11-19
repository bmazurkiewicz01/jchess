package com.bmazurkiewicz01.jchess.view;

public enum View {

    MENU("menu.fxml"),
    MAIN("main.fxml"),
    PAWN_PROMOTION_DIALOG("view/pawnPromotionDialog.fxml");

    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
