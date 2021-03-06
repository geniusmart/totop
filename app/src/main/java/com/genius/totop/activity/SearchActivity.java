package com.genius.totop.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.genius.totop.R;
import com.genius.totop.fragment.SearchFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.trinea.android.common.util.StringUtils;

@SuppressWarnings("FieldCanBeLocal")
public class SearchActivity extends FragmentActivity {

    @InjectView(R.id.searchText) TextView mSearchText;
    @InjectView(R.id.searchInput) EditText mSearchInputEditText;

    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        ButterKnife.inject(this);

        mSearchFragment = new SearchFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mSearchFragment).commit();
        mSearchInputEditText.requestFocus();
    }

    @OnClick(R.id.searchText)
    public void search(View view) {
        mSearchInputEditText.clearFocus();
        String searchText = mSearchInputEditText.getText().toString();
        if(StringUtils.isBlank(searchText)){
            Toast.makeText(this,getString(R.string.str_search_input_empty),Toast.LENGTH_SHORT).show();
        }else{
            mSearchFragment.search(searchText);
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    @OnClick(R.id.searchTopbarReturn)
    public void back(){
        finish();
    }
}
