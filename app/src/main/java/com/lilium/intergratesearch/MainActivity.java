package com.lilium.intergratesearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lilium.intergratesearch.AsyncTask.AppAysncTask;
import com.lilium.intergratesearch.AsyncTask.ContactsAsyncTask;
import com.lilium.intergratesearch.AsyncTask.FindAppAsyncTask;
import com.lilium.intergratesearch.AsyncTask.FindContactsAsyncTask;
import com.lilium.intergratesearch.AsyncTask.FindSmsAsyncTask;
import com.lilium.intergratesearch.AsyncTask.HistoryInitAsyncTask;
import com.lilium.intergratesearch.AsyncTask.InitSearchEngineAsyncTask;
import com.lilium.intergratesearch.AsyncTask.SmsAsyncTask;
import com.lilium.intergratesearch.Entity.App;
import com.lilium.intergratesearch.Entity.BaiduEntiy;
import com.lilium.intergratesearch.Entity.Contacts;
import com.lilium.intergratesearch.Entity.History;
import com.lilium.intergratesearch.Entity.SearchEngine;
import com.lilium.intergratesearch.Entity.Sentence;
import com.lilium.intergratesearch.Entity.Sms;
import com.lilium.intergratesearch.Listner.AppListner;
import com.lilium.intergratesearch.Listner.BaiduSubmitListner;
import com.lilium.intergratesearch.Listner.ContactsListner;
import com.lilium.intergratesearch.Listner.FindAppListner;
import com.lilium.intergratesearch.Listner.FindContactsListner;
import com.lilium.intergratesearch.Listner.FindSmsLisnter;
import com.lilium.intergratesearch.Listner.HistoryClickLisnter;
import com.lilium.intergratesearch.Listner.HistoryInitListner;
import com.lilium.intergratesearch.Listner.InitSearchEngineListner;
import com.lilium.intergratesearch.Listner.SearchEngineClickListner;
import com.lilium.intergratesearch.Listner.SearchEngineLongClickLisnter;
import com.lilium.intergratesearch.Listner.SmsListner;
import com.lilium.intergratesearch.Utils.BaiduSuggestionUtil;
import com.lilium.intergratesearch.Utils.HttpUtil;
import com.lilium.intergratesearch.Utils.NetworkUtil;
import com.lilium.intergratesearch.Utils.TranslationUtil;
import com.lilium.intergratesearch.adapter.AppSuggestionAdapter;
import com.lilium.intergratesearch.adapter.BaiduSuggestionAdapter;
import com.lilium.intergratesearch.adapter.ContactsSuggestionAdapter;
import com.lilium.intergratesearch.adapter.HistorySuggestionAdapter;
import com.lilium.intergratesearch.adapter.SearchEngineAdapter;
import com.lilium.intergratesearch.adapter.SmsSuggestionAdapter;
import com.muddzdev.styleabletoast.StyleableToast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private FloatingSearchView mSearchView;
    private Context mContext;
    private RecyclerView mAppRecyclerView;
    private List<App> mAppList = new ArrayList<>();
    private AppSuggestionAdapter mAppAdapter;
    private CardView mAppCardView;

    private List<Contacts> mContactsList = new ArrayList<>();
    private RecyclerView mContactsRecyclerView;
    private CardView mContactsCardView;
    private ContactsSuggestionAdapter mContactsAdapter;

    private List<Sms> mSmsList = new ArrayList<>();
    private RecyclerView mSmsRecyclerView;
    private SmsSuggestionAdapter mSmsAdapter;
    private CardView mSmsCardView;
    private String urlHead = "https://www.baidu.com/s?wd=%s";

    private List<SearchEngine> mSearchEngineList = new ArrayList<>();
    private RecyclerView mSearchEngineRecyclerView;
    private SearchEngineAdapter mSearchEngineAdapter;
    private ConstraintLayout mAddSearchEngine;
    private DrawerLayout mDrawer;
    private SearchEngine mSearchEngineDelete;
    private int mPosition;

    //搜索记录

    private LinkedList<History> mHistoryList = new LinkedList<>();
    private String mQueryText = "";
    private RecyclerView mHistoryRecyclerView;
    private HistorySuggestionAdapter mHistorySuggestionAdapter;
    private CardView mHistoryCardView;
    private String queryingText = "";


    //实时翻译

    private CardView mTranslationCardView;
    private TextView mTranslationTextView;
    private String meanText = "";
    private Handler mTransalationHandler = new Handler();
    private String mTranslationQuery = "";
    private Runnable mTransaltionTask = new Runnable() {
        @Override
        public void run() {
            if (!mTranslationQuery.equals("") && NetworkUtil.isNetworkAvailable(mContext)) {
                String translationUrl = "https://dict-co.iciba.com/api/dictionary.php?w=" + mTranslationQuery + "&type=json&key=3FDF1EBEE0ED64EF6E67E55D1B3C4B7F";
                translation(translationUrl);
            }

        }
    };


    private RecyclerView mBaiduSuggestionRecyclerView;
    private List<BaiduEntiy> mBaiduList = new ArrayList<>();
    private BaiduSuggestionAdapter mBaiduAdapter;
    private CardView mBaiduCardView;
    private Handler mBaiduHandler = new Handler();
    private String mBaiduQuery = "";
    private Runnable mBaiduTask = new Runnable() {
        @Override
        public void run() {
            if (!mBaiduQuery.equals("") && NetworkUtil.isNetworkAvailable(mContext)) {
                String baiduSuggestionUrl = "http://suggestion.baidu.com/su?wd=" + mBaiduQuery + "&json=1";
                Log.d("baidutest", "run: " + baiduSuggestionUrl);
                getSuggestions(baiduSuggestionUrl);
            }

        }
    };


    //设置
    private int mConfigSmsNum=3;
    private int smsMinSearchCount = 2;
    private int mTranslationMinCount=3;
    private int mBaiduMinCount=3;
    private int mNetworkDelay=500;
    private int mBaiduCount=5;
    private int mHistoryCount=5;
    private boolean mConfigMatcher=true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent=new Intent(mContext,ConfigActivity.class);
                startActivity(intent);
            }
        });

        //设置状态栏

//        StatusBarUtil.setColor(MainActivity.this, Color.parseColor("#F44336"));
//        StatusBarUtil.setColorForDrawerLayout(MainActivity.this, mDrawer, Color.parseColor("#607D8B"));
        ImmersionBar.with(this).statusBarColor(R.color.my_color_primary).navigationBarColor(R.color.my_color_primary).barAlpha(0.3f).fitsSystemWindows(true).init();

        //设置导航宽度

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        LinearLayout mNavCardView = (LinearLayout) findViewById(R.id.nav_linearview);
        mNavCardView.getLayoutParams().width = (int) (display.getWidth() * 0.65);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //初始化搜索引擎 默认百度和谷歌

        LinearLayoutManager searchEnineLayoutManager = new LinearLayoutManager(this);
        mSearchEngineRecyclerView = (RecyclerView) findViewById(R.id.search_engine_list);
        mSearchEngineRecyclerView.setLayoutManager(searchEnineLayoutManager);
        mSearchEngineAdapter = new SearchEngineAdapter(mSearchEngineList, new SearchEngineClickListner() {
            @Override
            public void callBack(SearchEngine searchEngine) {
                urlHead = searchEngine.getUrl();
                mDrawer.closeDrawer(GravityCompat.START);
                Snackbar.make(mSearchView, "使用" + searchEngine.getName() + "搜索", Snackbar.LENGTH_SHORT).show();
            }
        }, new SearchEngineLongClickLisnter() {
            @Override
            public void callback(SearchEngine searchEngine, int position) {
                if (position == 0 || position == 1) {
//                    Toast.makeText(mContext, "默认搜索引擎不能修改嗷", Toast.LENGTH_SHORT).show();
                    new StyleableToast
                            .Builder(mContext)
                            .text("搜索引擎不能修改嗷!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.parseColor("#F44336"))
                            .show();
                    return;
                }
                mSearchEngineDelete = searchEngine;
                mPosition = position;
                Intent intent = new Intent(mContext, AddSearchActivity.class);
                intent.putExtra("searchengine", mSearchEngineDelete);
                startActivityForResult(intent, 2);
            }
        });
        mSearchEngineRecyclerView.setAdapter(mSearchEngineAdapter);
        new InitSearchEngineAsyncTask(new InitSearchEngineListner() {
            @Override
            public void onSuccess(List<SearchEngine> searchEngineList) {
                mSearchEngineAdapter.swapData(searchEngineList);
                mSearchEngineList = searchEngineList;
            }

            @Override
            public void onFailed() {
//                Toast.makeText(mContext, "没有添加自定义搜索引擎嗷", Toast.LENGTH_LONG).show();
                new StyleableToast
                        .Builder(mContext)
                        .text("没有添加自定义搜索引擎嗷")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.parseColor("#F44336"))
                        .show();
            }
        }).execute();

        //增加搜索引擎
        mAddSearchEngine = (ConstraintLayout) findViewById(R.id.add_search_engine);
        mAddSearchEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddSearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        //搜索记录初始化

        mHistoryRecyclerView = (RecyclerView) findViewById(R.id.search_history_list);
        LinearLayoutManager hitoryLinearLayoutManager = new LinearLayoutManager(this);
        mHistoryRecyclerView.setLayoutManager(hitoryLinearLayoutManager);
        mHistorySuggestionAdapter = new HistorySuggestionAdapter(mHistoryList, new HistoryClickLisnter() {
            @Override
            public void callback(History history) {
                mSearchView.setSearchText(history.getHistoryBodyText());
            }
        });
        mHistoryRecyclerView.setAdapter(mHistorySuggestionAdapter);
        mHistoryCardView = (CardView) findViewById(R.id.history_card_view);
        mHistoryCardView.setVisibility(View.GONE);
        new HistoryInitAsyncTask(new HistoryInitListner() {
            @Override
            public void onSucess(LinkedList<History> histories) {
                mHistoryList = histories;
                mHistorySuggestionAdapter.swapData(histories);
            }

            @Override
            public void onFailed() {
//                Toast.makeText(mContext, "未找到搜索记录", Toast.LENGTH_SHORT).show();

            }
        }).execute();


        //实时翻译

        mTranslationCardView = (CardView) findViewById(R.id.translation_card_view);
        mTranslationTextView = (TextView) findViewById(R.id.translation_textview);
        mTranslationCardView.setVisibility(View.GONE);
        mTranslationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTranslationQuery.equals("")) {
                    Uri url = Uri.parse("https://m.iciba.com/" + mTranslationQuery);
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(intent);
                }
            }
        });


        //app搜索

        mAppCardView = (CardView) findViewById(R.id.app_card_view);
        mAppRecyclerView = (RecyclerView) findViewById(R.id.search_results_list);
        mAppAdapter = new AppSuggestionAdapter(mAppList,mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAppRecyclerView.setLayoutManager(linearLayoutManager);
        mAppRecyclerView.setAdapter(mAppAdapter);
        mAppCardView.setVisibility(View.GONE);
        new AppAysncTask(new AppListner() {
            @Override
            public void onsuccess(List<App> results) {
                mAppAdapter.swapData(results);
                mAppList = results;
            }

            @Override
            public void onFailed() {

            }
        }).execute();


        //百度搜索建议

        mBaiduSuggestionRecyclerView = (RecyclerView) findViewById(R.id.search_baidu_list);
        mBaiduAdapter = new BaiduSuggestionAdapter(mBaiduList, mContext, new BaiduSubmitListner() {
            @Override
            public void callback(String submitText) {
                mSearchView.setSearchText(submitText);
            }
        });
        LinearLayoutManager mBaiduLinearManger = new LinearLayoutManager(this);
        mBaiduSuggestionRecyclerView.setLayoutManager(mBaiduLinearManger);
        mBaiduSuggestionRecyclerView.setAdapter(mBaiduAdapter);
        mBaiduCardView = (CardView) findViewById(R.id.baidu_card_view);
        mBaiduCardView.setVisibility(View.GONE);


        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchView.attachNavigationDrawerToMenuButton(drawer);

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                queryingText = newQuery;
                mSearchView.showProgress();
                mAppAdapter.getSearchContent(newQuery);
                mSmsAdapter.getSearchContent(newQuery);
                mContactsAdapter.getSearchContent(newQuery);
                mHistoryCardView.setVisibility(View.GONE);
                if (newQuery.equals("")) {
                    mSearchView.hideProgress();
                    mAppAdapter.swapData(mAppList);
//                    mAppRecyclerView.setVisibility(View.GONE);
                    mAppCardView.setVisibility(View.GONE);


                    mContactsAdapter.swapData(mContactsList);
                    mContactsCardView.setVisibility(View.GONE);

                    mSmsAdapter.swapData(mSmsList);
                    mSmsCardView.setVisibility(View.GONE);
                    mTranslationCardView.setVisibility(View.GONE);

                    mBaiduCardView.setVisibility(View.GONE);

                    return;
                } else {

                    //搜索app

                    new FindAppAsyncTask(new FindAppListner() {
                        @Override
                        public void onSuccess(List<App> appList) {
                            mAppAdapter.swapData(appList);
                            mSearchView.hideProgress();
//                            mAppRecyclerView.setVisibility(View.VISIBLE);
                            mAppCardView.setVisibility(View.VISIBLE);
                        }


                        @Override
                        public void onFailed() {
                            mSearchView.hideProgress();
                            mAppAdapter.swapData(mAppList);
//                            mAppRecyclerView.setVisibility(View.GONE);
                            mAppCardView.setVisibility(View.GONE);
//                            Toast.makeText(MainActivity.this, "no app match", Toast.LENGTH_SHORT).show();
                        }
                    }, mAppList,mConfigMatcher).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newQuery);

                    //搜索联系人

                    new FindContactsAsyncTask(new FindContactsListner() {
                        @Override
                        public void onSuccess(List<Contacts> contactsList) {
                            mContactsAdapter.swapData(contactsList);
                            mContactsCardView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailed() {
                            mContactsAdapter.swapData(mContactsList);
                            mContactsCardView.setVisibility(View.GONE);
//                            Toast.makeText(mContext, "no contacts match", Toast.LENGTH_SHORT).show();
                        }
                    }, mContactsList,mConfigMatcher).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newQuery);

                    //搜索短信内容

                    if (newQuery.length() >= smsMinSearchCount) {
                        new FindSmsAsyncTask(new FindSmsLisnter() {
                            @Override
                            public void onSuccess(List<Sms> smsList) {
                                mSmsAdapter.swapData(smsList);
                                mSmsCardView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailed() {
                                mSmsAdapter.swapData(mSmsList);
                                mSmsCardView.setVisibility(View.GONE);
//                                Toast.makeText(mContext, "no sms matched", Toast.LENGTH_SHORT).show();
                            }
                        }, mSmsList,mConfigSmsNum,mConfigMatcher).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newQuery);
                    }

                    //搜索记录过滤显示

                    mHistoryCardView.setVisibility(View.VISIBLE);

                    //实时翻译 英译汉

                    if (oldQuery.length() < newQuery.length()) {
                        if (newQuery.length() >= mTranslationMinCount && TranslationUtil.isEnglish(newQuery)) {
                            if (mTransaltionTask != null) {
                                mTransalationHandler.removeCallbacks(mTransaltionTask);
                            }
                            if (!newQuery.equals("")) {
                                mTranslationQuery = newQuery;
                                mTransalationHandler.postDelayed(mTransaltionTask, mNetworkDelay);
                            }
                        } else {
                            mTranslationCardView.setVisibility(View.GONE);
                        }
                    } else {
                        mTranslationCardView.setVisibility(View.GONE);
                    }

                    //百度搜索建议

                    if (oldQuery.length() < newQuery.length()) {
                        if (newQuery.length() >= mBaiduMinCount) {
                            if (mBaiduTask != null) {
                                mBaiduHandler.removeCallbacks(mBaiduTask);
                            }
                            if (!newQuery.equals("")) {
                                mBaiduQuery = newQuery;
                                mBaiduHandler.postDelayed(mBaiduTask, mNetworkDelay);
                            }

                        } else {
                            mBaiduCardView.setVisibility(View.GONE);
                        }
                    } else {
                        mBaiduCardView.setVisibility(View.GONE);
                    }
                }

            }
        });


        //联系人搜索
        mContactsRecyclerView = (RecyclerView)

                findViewById(R.id.search_contacts_list);

        mContactsCardView = (CardView)

                findViewById(R.id.contacts_card_view);

        mContactsAdapter = new

                ContactsSuggestionAdapter(mContactsList, mContext);

        LinearLayoutManager linearLayoutManagerContacts = new LinearLayoutManager(this);
        mContactsRecyclerView.setLayoutManager(linearLayoutManagerContacts);
        mContactsRecyclerView.setAdapter(mContactsAdapter);
        mContactsCardView.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }


        //短信搜索
        mSmsRecyclerView = (RecyclerView)

                findViewById(R.id.search_sms_list);

        mSmsAdapter = new

                SmsSuggestionAdapter(mContext, mSmsList);

        mSmsCardView = (CardView)

                findViewById(R.id.sms_card_view);

        LinearLayoutManager linearLayoutManagerSms = new LinearLayoutManager(this);
        mSmsRecyclerView.setLayoutManager(linearLayoutManagerSms);
        mSmsRecyclerView.setAdapter(mSmsAdapter);
        mSmsCardView.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 2);
        } else {
            readSms();
        }

        //联网搜索 直接确认

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                mQueryText = currentQuery;
                searchSubmit(mQueryText);
            }
        });

        //联网搜索 点击按钮

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    String query = mSearchView.getQuery();
                    mQueryText = query;
                    searchSubmit(query);
                }
            }
        });

    }

    //联网搜索核心方法

    private void searchSubmit(String queryText) {
        addHistory();
        Uri url = Uri.parse(urlHead.replace("%s", queryText));
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        startActivity(intent);
    }

    //翻译

    private void translation(String translationUrl) {
        HttpUtil.sendOkHttpRequest(translationUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, "获取翻译数据失败", Toast.LENGTH_SHORT).show();
                        new StyleableToast
                                .Builder(mContext)
                                .text("获取翻译数据失败")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.parseColor("#F44336"))
                                .show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                meanText = TranslationUtil.parseEnglishToChinese(result);
                if (meanText.equals("")) {
                    return;
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTranslationTextView.setText(meanText);
                            mTranslationCardView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    //百度搜索建议

    private void getSuggestions(String suggestionUrl) {
        HttpUtil.sendOkHttpRequest(suggestionUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, "获取百度搜索建议失败", Toast.LENGTH_SHORT).show();
//                        Log.d("baidutest", "run: on failed");
                        new StyleableToast
                                .Builder(mContext)
                                .text("获取百度搜索建议失败")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.parseColor("#F44336"))
                                .show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                List<BaiduEntiy> baiduEntiyList = BaiduSuggestionUtil.parseBaiduSuggestion(responseBody,mContext);
                if (baiduEntiyList != null && baiduEntiyList.size() > 0) {
                    mBaiduList = baiduEntiyList;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBaiduAdapter.getSearchContent(mBaiduQuery);
                            mBaiduAdapter.swapData(mBaiduList);
                            mBaiduCardView.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(mContext, "获取百度搜索建议失败", Toast.LENGTH_SHORT).show();
                            new StyleableToast
                                    .Builder(mContext)
                                    .text("获取百度搜索建议失败")
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.parseColor("#F44336"))
                                    .show();
                        }
                    });
                }
            }
        });
    }


    //读取短信

    private void readSms() {
        new SmsAsyncTask(mContext, new SmsListner() {
            @Override
            public void onSuccess(List<Sms> smsList) {
                mSmsAdapter.swapData(smsList);
                mSmsList = smsList;
            }

            @Override
            public void onFailed() {
//                Toast.makeText(mContext, "failed to load sms", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    //读取联系人

    private void readContacts() {
        new ContactsAsyncTask(mContext, new ContactsListner() {
            @Override
            public void onSuccess(List<Contacts> contactsList) {
                mContactsAdapter.swapData(contactsList);
                mContactsList = contactsList;
            }

            @Override
            public void onFailed() {
//                Toast.makeText(mContext, "failed to load contacts", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    //添加搜索记录

    private void addHistory() {
        List<History> historyList = DataSupport.findAll(History.class);
        for (History historyTemp : historyList) {
            if (historyTemp.getHistoryBodyText().equals(mQueryText)) {
                return;
            }
        }
        History history = new History();
        mHistoryList.addFirst(history);
        mHistoryCardView.setVisibility(View.GONE);
        history.setHistoryBodyText(mQueryText);
        history.save();
        if (mHistoryList.size() > mHistoryCount) {
            mHistoryList.removeLast();
            DataSupport.deleteAll(History.class, "id= ? ", String.valueOf(DataSupport.findFirst(History.class).getId()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHistorySuggestionAdapter.notifyDataSetChanged();
        SharedPreferences configShared=getSharedPreferences("config",MODE_PRIVATE);

        //设置短信数

        String configSmsNum=configShared.getString("config_sms_num","3");
        mConfigSmsNum=Integer.parseInt(configSmsNum);


        //短信生效数

        String configSearchNum=configShared.getString("config_sms_search_num","2");
        smsMinSearchCount=Integer.parseInt(configSearchNum);

        //翻译生效数
        mTranslationMinCount=configShared.getInt("config_translation_num",3);

        //百度搜索建议生效数
        mBaiduMinCount=configShared.getInt("config_baidu_num",3);

        //设置网络延迟时间
        mNetworkDelay=configShared.getInt("config_network_delay",500);
//        Log.d("networkdelay", "onResume: "+mNetworkDelay);


        //百度搜素条数
        mBaiduCount=configShared.getInt("config_baidu_count",5);

        //搜索记录条数
        mHistoryCount=configShared.getInt("config_history_count",5);

        //模糊搜索
        mConfigMatcher=configShared.getBoolean("config_matcher",true);



    }


    @Override
    protected void onStart() {
        super.onStart();
        final TextView mDaySentence = (TextView) findViewById(R.id.day_sentence);
        SharedPreferences sharedPreferences = getSharedPreferences("sentence", MODE_PRIVATE);

        if (!sharedPreferences.contains(getNowDate())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtil.sendOkHttpRequest("http://open.iciba.com/dsapi/", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDaySentence.setText("恭喜你触发彩蛋！\n其实是网络没加载成功哈哈哈哈");
                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseBody = response.body().string();
                            Log.d("sentencetest", "onResponse: "+responseBody);
                            recordSentence(responseBody);
                        }
                    });
                }
            }).start();
        }
        String result=sharedPreferences.getString(getNowDate(),"")+"\n//"+getNowDate();
        mDaySentence.setText(result);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    private void recordSentence(String responseBody) {
        Gson gson = new Gson();
        Sentence sentence = gson.fromJson(responseBody, Sentence.class);
        String content = sentence.getContent();
        String networkTime = sentence.getDateline();
        SharedPreferences.Editor sentenceEditor = getSharedPreferences("sentence", MODE_PRIVATE).edit();
        sentenceEditor.putString(networkTime, content);
        sentenceEditor.apply();
    }

    private String getNowDate() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String nowDate=simpleDateFormat.format(date);
        Log.d("sentencetest", "getNowDate: "+nowDate);
        return nowDate;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
//                    Toast.makeText(this, "You denied the contacts permission", Toast.LENGTH_SHORT).show();
                    new StyleableToast
                            .Builder(mContext)
                            .text("拒绝了权限有些功能将不能使用嗷")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.parseColor("#F44336"))
                            .show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    readSms();
                } else {
//                    Toast.makeText(this, "you denied the sms permission", Toast.LENGTH_SHORT).show();
                    new StyleableToast
                            .Builder(mContext)
                            .text("拒绝了权限有些功能将不能使用嗷")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.parseColor("#F44336"))
                            .show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    SearchEngine searchEngine = DataSupport.findLast(SearchEngine.class);
                    mSearchEngineList.add(searchEngine);
                    mSearchEngineAdapter.notifyDataSetChanged();
                }
                break;
            case 2:
                if (resultCode == 200) {
                    mSearchEngineList.remove(mPosition);
                    mSearchEngineAdapter.notifyItemRemoved(mPosition);
                    for (SearchEngine searchEngine : mSearchEngineList) {
                    }
                }
                if (resultCode == RESULT_OK) {
                    SearchEngine searchEngineUpdate = (SearchEngine) data.getSerializableExtra("searchengine");
                    mSearchEngineList.remove(mPosition);
                    mSearchEngineList.add(mPosition, searchEngineUpdate);
                    mSearchEngineAdapter.notifyItemChanged(mPosition);

                }
                if (resultCode == RESULT_CANCELED) {
                    break;
                }
                break;
            default:
                break;
        }
    }
}
