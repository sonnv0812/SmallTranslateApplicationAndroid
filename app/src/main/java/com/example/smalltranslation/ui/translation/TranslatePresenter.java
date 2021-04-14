package com.example.smalltranslation.ui.translation;

import com.example.smalltranslation.data.base.OnBooleanListener;
import com.example.smalltranslation.data.base.OnDataLoadedListener;
import com.example.smalltranslation.data.repository.translation.TranslateRepository;

public class TranslatePresenter implements TranslateContract.Presenter {

    private TranslateContract.View view;
    private TranslateRepository repository;

    public TranslatePresenter(TranslateContract.View view, TranslateRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void handleTranslate(String sourceSentence) {
        if (sourceSentence == null) {

        } else {
            repository.translate(sourceSentence, new OnDataLoadedListener<String>() {
                @Override
                public void onSuccess(String data) {
                    view.updateTranslate(data);
                }

                @Override
                public void onFailure(Exception exception) {
                    view.showError(exception.toString());
                }
            });
        }
    }

    @Override
    public void handleCreateTranslator() {
        repository.createTranslator();
    }

    @Override
    public void handleCloseTranslator() {
        repository.closeTranslator();
    }

    @Override
    public void checkModel() {
        repository.checkedModel(new OnBooleanListener() {
            @Override
            public void onTrue() {
                view.hideProgress();
            }

            @Override
            public void onFalse(Exception e) {
                view.processDownloadModel();
            }
        });
    }

    @Override
    public void handleDownloadModel() {
        repository.downloadModel(new OnBooleanListener() {
            @Override
            public void onTrue() {
                view.hideProgress();
            }

            @Override
            public void onFalse(Exception e) {
                view.showError(e.toString());
            }
        });
    }
}
