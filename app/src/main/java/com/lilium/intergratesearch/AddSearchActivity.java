package com.lilium.intergratesearch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lilium.intergratesearch.Entity.SearchEngine;

import org.litepal.crud.DataSupport;

public class AddSearchActivity extends AppCompatActivity {
    private ImageButton mCancelAdd;
    private ImageButton mSubmitAdd;
    private ImageButton mDeleteSearch;
    private ImageButton mUpdateSearch;
    private EditText mSearchName;
    private EditText mSearchUrl;
    private String mName;
    private String mUrl;
    private static final String TAG = "AddSearchActivity";
    private SearchEngine mSearchEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search);
        StatusBarUtil.setColor(AddSearchActivity.this, Color.parseColor("#D32F2F"));
//        StatusBarUtil.setTransparent(AddSearchActivity.this);

        Toolbar mToolbar=(Toolbar)findViewById(R.id.add_search_toolbar);
        setSupportActionBar(mToolbar);


        mCancelAdd = (ImageButton) findViewById(R.id.cancel_add_search);
        mSubmitAdd = (ImageButton) findViewById(R.id.submit_add_search);
        mSearchName = (EditText) findViewById(R.id.search_name);
        mSearchUrl = (EditText) findViewById(R.id.search_url);
        mUpdateSearch = (ImageButton) findViewById(R.id.update_add_search);
        mDeleteSearch = (ImageButton) findViewById(R.id.delete_search_engine);
        final Intent intent = getIntent();
        mSearchEngine = (SearchEngine) intent.getSerializableExtra("searchengine");
//        final int postion = intent.getIntExtra("id", 0);
        //update
        if ( mSearchEngine != null) {
            mSearchName.setText( mSearchEngine.getName());
            mSearchUrl.setText( mSearchEngine.getUrl());
            mUpdateSearch.setVisibility(View.VISIBLE);
            mDeleteSearch.setVisibility(View.VISIBLE);
        } else {
            //add
            mSubmitAdd.setVisibility(View.VISIBLE);
        }
        //退出
        mCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        //添加
        mSubmitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mSearchName.getText().toString();
                mUrl = mSearchUrl.getText().toString();
                if (mUrl.equals("") || mName.equals("")) {
                    Toast.makeText(AddSearchActivity.this, "提交不能为空", Toast.LENGTH_SHORT).show();
                } else if (DataSupport.where("name = ? and url = ?", mName, mUrl).find(SearchEngine.class).size() > 0) {
                    Toast.makeText(AddSearchActivity.this, "请不要重复添加", Toast.LENGTH_SHORT).show();
                }else if(!(mUrl.startsWith("http://")||mUrl.startsWith("https://"))){
                    Toast.makeText(AddSearchActivity.this, "url头部必须为“https:// 或 http://", Toast.LENGTH_LONG).show();
                }else if(!mUrl.contains("%s")){
                    Toast.makeText(AddSearchActivity.this,"url必须包含%s替换关键字",Toast.LENGTH_LONG).show();
                }
                else {
                    SearchEngine searchEngine = new SearchEngine();
                    searchEngine.setName(mName);
                    searchEngine.setUrl(mUrl);
                    searchEngine.save();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        //删除 resultcode为200
        mDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleteResult = DataSupport.deleteAll(SearchEngine.class, "id= ?", String.valueOf( mSearchEngine.getId()));
                //删除
                if (deleteResult > 0) {
                    setResult(200);
                    finish();
                } else {
                    Toast.makeText(AddSearchActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //更新
        mUpdateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mSearchName.getText().toString();
                mUrl = mSearchUrl.getText().toString();
                SearchEngine searchEngineUpdate = new SearchEngine();
                searchEngineUpdate.setId(mSearchEngine.getId());
                searchEngineUpdate.setName(mName);
                searchEngineUpdate.setUrl(mUrl);
                int updateResult = searchEngineUpdate.updateAll("id = ?", String.valueOf( mSearchEngine.getId()));
                Intent intentUpdate=new Intent();
                intentUpdate.putExtra("searchengine",searchEngineUpdate);
                //更新
                if (updateResult > 0) {
                    setResult(RESULT_OK,intentUpdate);
                    finish();
                } else {
                    Toast.makeText(AddSearchActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
