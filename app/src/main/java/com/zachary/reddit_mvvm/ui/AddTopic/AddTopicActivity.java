package com.zachary.reddit_mvvm.ui.AddTopic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zachary.reddit_mvvm.R;
import com.zachary.reddit_mvvm.base.BaseAppCompatActivity;
import com.zachary.reddit_mvvm.ui.AddTopic.AddTopicViewModel.AddTopicViewModelListener;

public class AddTopicActivity extends BaseAppCompatActivity implements AddTopicViewModelListener {
    private AddTopicViewModel viewModel;

    private EditText etTopic;
    private TextView tvWordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_add_topic);
    }

    @Override
    protected void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTopic = getView(R.id.etTopic);
        tvWordCount = getView(R.id.tvWordCount);

        etTopic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onTextChanged(s.toString());
            }
        });
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddTopicViewModel.class);
        viewModel.setListener(this);
        viewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvWordCount.setText(String.format(getString(R.string.word_count_format),s.length()));
            }
        });
        etTopic.setText(viewModel.getText().getValue());
    }

    @Override
    public void showEmptyTextToast() {
        Toast.makeText(this, R.string.error_topic_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addTopicSuccessful(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_topic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                viewModel.addTopic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
