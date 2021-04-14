package com.example.smalltranslation.data.repository.translation;

import androidx.annotation.NonNull;

import com.example.smalltranslation.data.base.OnBooleanListener;
import com.example.smalltranslation.data.base.OnDataLoadedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Set;

public class TranslateRepositoryImpl implements TranslateRepository {

    private Translator englishVietnameseTranslator;
    private RemoteModelManager modelManager;

    @Override
    public void createTranslator() {
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.VIETNAMESE)
                .build();
        englishVietnameseTranslator = Translation.getClient(options);
    }

    @Override
    public void closeTranslator() {
        englishVietnameseTranslator.close();
    }

    @Override
    public void downloadModel(OnBooleanListener callback) {
        TranslateRemoteModel vietnameseModel =
                new TranslateRemoteModel.Builder(TranslateLanguage.VIETNAMESE).build();
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        modelManager.download(vietnameseModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onTrue();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFalse(e);
                    }
                });
    }

    @Override
    public void checkedModel(OnBooleanListener callback) {
        modelManager = RemoteModelManager.getInstance();

        modelManager.getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(new OnSuccessListener<Set<TranslateRemoteModel>>() {
                    @Override
                    public void onSuccess(Set<TranslateRemoteModel> translateRemoteModels) {
                        callback.onTrue();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFalse(e);
                    }
                });
    }

    @Override
    public void translate(String source, OnDataLoadedListener<String> callback) {
        englishVietnameseTranslator.translate(source)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(s);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
    }
}
