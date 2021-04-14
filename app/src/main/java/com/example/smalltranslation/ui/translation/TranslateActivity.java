package com.example.smalltranslation.ui.translation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smalltranslation.R;
import com.example.smalltranslation.data.repository.translation.TranslateRepository;
import com.example.smalltranslation.data.repository.translation.TranslateRepositoryImpl;

public class TranslateActivity extends AppCompatActivity
        implements TranslateContract.View {

    private EditText editSource;
    private TextView textTarget, textProgress;
    private String sentenceSource;
    private TranslateContract.Presenter presenter;
    private ProgressBar progressDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        editSource = findViewById(R.id.edit_source);
        textTarget = findViewById(R.id.text_target);
        progressDownload = findViewById(R.id.progress_download);
        progressDownload.setVisibility(View.GONE);
        textProgress = findViewById(R.id.text_progress);
        initPresenter();

        presenter.checkModel();
    }

    private void initPresenter() {
        TranslateRepository repository = new TranslateRepositoryImpl();
        presenter = new TranslatePresenter(this, repository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editSource.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sentenceSource = s.toString();
                presenter.handleTranslate(sentenceSource);
            }

            @Override
            public void afterTextChanged(Editable s) {
                sentenceSource = s.toString();
                presenter.handleTranslate(sentenceSource);
            }
        });
    }

    @Override
    public void updateTranslate(String translatedSentence) {
        textTarget.setText(translatedSentence);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void processDownloadModel() {
        progressDownload.setVisibility(View.VISIBLE);
        textProgress.setText(getString(R.string.text_downloading));
        editSource.setInputType(InputType.TYPE_NULL);
        editSource.setEnabled(false);
        editSource.setFocusable(false);
        presenter.handleDownloadModel();
    }

    @Override
    public void hideProgress() {
        progressDownload.setVisibility(View.GONE);
        presenter.handleCreateTranslator();
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                textProgress.setText(getString(R.string.text_downloaded));
            }
        }, 5000);
        editSource.setEnabled(true);
        editSource.setInputType(InputType.TYPE_CLASS_TEXT);
        editSource.setFocusable(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.handleCloseTranslator();
    }
}
