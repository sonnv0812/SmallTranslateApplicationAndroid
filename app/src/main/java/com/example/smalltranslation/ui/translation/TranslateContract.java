package com.example.smalltranslation.ui.translation;

public interface TranslateContract {
    interface View {
        void updateTranslate(String translatedSentence);

        void showError(String error);

        void processDownloadModel();

        void hideProgress();
    }

    interface Presenter {
        void handleTranslate(String sourceSentence);

        void handleCreateTranslator();

        void handleCloseTranslator();

        void checkModel();

        void handleDownloadModel();
    }
}
