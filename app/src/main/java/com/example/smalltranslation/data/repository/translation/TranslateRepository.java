package com.example.smalltranslation.data.repository.translation;

import com.example.smalltranslation.data.base.OnBooleanListener;
import com.example.smalltranslation.data.base.OnDataLoadedListener;

public interface TranslateRepository {
    void createTranslator();

    void closeTranslator();

    void downloadModel(OnBooleanListener callback);

    void checkedModel(OnBooleanListener callback);

    void translate(String source, OnDataLoadedListener<String> callback);
}
