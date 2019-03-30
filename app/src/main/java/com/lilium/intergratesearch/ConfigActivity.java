package com.lilium.intergratesearch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

public class ConfigActivity extends AppCompatActivity {

    private TextView mConfigSmsNum;
    private final Context mContext = ConfigActivity.this;
    private static final String[] smsNums = {"1", "2", "3", "4", "5", "6"};
    private View mSmsDialogView;
    private NumberPicker mSmsNumberPicker;

    private static final String[] smsMinSearchCount={"1", "2", "3", "4", "5", "6"};
    private View mSmsSearchDialogView;
    private NumberPicker mSmsSearchNumberPicker;
    private TextView mSmsSearch;


    private static final String[] mTranslationMinCount={"1", "2", "3", "4", "5", "6","7","8","9"};
    private View mTranslationDialogView;
    private NumberPicker mTranslationNumberPicker;
    private  TextView mTranslationTextView;

    private static final String[] mBaiduMinCount={"1", "2", "3", "4", "5", "6","7","8","9"};
    private View mBaiduDialogView;
    private NumberPicker mBaiduNumberPicker;
    private  TextView mBaiduTextView;


    private static final String[] mNetworkDelay={"300", "400", "500", "600", "700", "800","900","1000","1100","1200","1300","1400","1500","1600","1700","1800","1900","2000"};
    private View mnetWorkDialogView;
    private NumberPicker mNetWorkNumberPicker;
    private  TextView mNetworkTextView;

    private TextView mColorConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.config_toolbar);
        setSupportActionBar(mToolbar);
        StatusBarUtil.setColor(ConfigActivity.this, Color.parseColor("#D32F2F"));

        //修改短信搜索条数
        mConfigSmsNum = (TextView) findViewById(R.id.sms_config_num);

        mConfigSmsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmsDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sms, null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                mSmsNumberPicker=(NumberPicker) mSmsDialogView.findViewById(R.id.numberPicker_sms);
                mSmsNumberPicker.setDisplayedValues(smsNums);
                mSmsNumberPicker.setMinValue(0);
                mSmsNumberPicker.setMaxValue(smsNums.length-1);
                mSmsNumberPicker.setValue(Integer.parseInt(getSharedPreferences("config",MODE_PRIVATE).getString("config_sms_num","3"))-1);
                builder.setView(mSmsDialogView);
                builder.setTitle("修改短信最大搜索条数:");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_myadd));
                builder.setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                configSms(mSmsNumberPicker.getValue()+1);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

        //设置短信生效数

        mSmsSearch=(TextView)findViewById(R.id.sms_search_config_num);
        mSmsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmsSearchDialogView=LayoutInflater.from(mContext).inflate(R.layout.dialog_sms,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                mSmsSearchNumberPicker=(NumberPicker) mSmsSearchDialogView.findViewById(R.id.numberPicker_sms);
                mSmsSearchNumberPicker.setDisplayedValues(smsMinSearchCount);
                mSmsSearchNumberPicker.setMinValue(0);
                mSmsSearchNumberPicker.setMaxValue(smsMinSearchCount.length-1);
                mSmsSearchNumberPicker.setValue(Integer.parseInt(getSharedPreferences("config",MODE_PRIVATE).getString("config_sms_search_num","2"))-1);
                builder.setView(mSmsSearchDialogView);
                builder.setTitle("修改短信最大搜索条数:");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_myadd));
                builder.setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                configSearchSms(mSmsSearchNumberPicker.getValue()+1);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        mTranslationTextView=(TextView)findViewById(R.id.tranlation_config_num);
        mTranslationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTranslationDialogView=LayoutInflater.from(mContext).inflate(R.layout.dialog_sms,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                mTranslationNumberPicker=(NumberPicker) mTranslationDialogView.findViewById(R.id.numberPicker_sms);
                mTranslationNumberPicker.setDisplayedValues(mTranslationMinCount);
                mTranslationNumberPicker.setMinValue(0);
                mTranslationNumberPicker.setMaxValue(mTranslationMinCount.length-1);
                mTranslationNumberPicker.setValue(getSharedPreferences("config",MODE_PRIVATE).getInt("config_translation_num",3)-1);
                builder.setView(mTranslationDialogView);
                builder.setTitle("修改翻译生效字符数:");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_myadd));
                builder.setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                configTranslation(mTranslationNumberPicker.getValue()+1);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

        //设置百度搜索建议生效字符数

        mBaiduTextView=(TextView)findViewById(R.id.baidu_config_num);
        mBaiduTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduDialogView=LayoutInflater.from(mContext).inflate(R.layout.dialog_sms,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                mBaiduNumberPicker=(NumberPicker) mBaiduDialogView.findViewById(R.id.numberPicker_sms);
                mBaiduNumberPicker.setDisplayedValues(mBaiduMinCount);
                mBaiduNumberPicker.setMinValue(0);
                mBaiduNumberPicker.setMaxValue(mBaiduMinCount.length-1);
                mBaiduNumberPicker.setValue(getSharedPreferences("config",MODE_PRIVATE).getInt("config_baidu_num",3)-1);
                builder.setView(mBaiduDialogView);
                builder.setTitle("修改百度搜索建议生效字符数:");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_myadd));
                builder.setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                configBaiduNum(mBaiduNumberPicker.getValue()+1);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
        mNetworkTextView=(TextView)findViewById(R.id.network_config_delay);
        mNetworkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnetWorkDialogView=LayoutInflater.from(mContext).inflate(R.layout.dialog_sms,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                mNetWorkNumberPicker=(NumberPicker) mnetWorkDialogView.findViewById(R.id.numberPicker_sms);
                mNetWorkNumberPicker.setDisplayedValues(mNetworkDelay);
                mNetWorkNumberPicker.setMinValue(3);
                mNetWorkNumberPicker.setMaxValue(mNetworkDelay.length+2);
                mNetWorkNumberPicker.setValue(getSharedPreferences("config",MODE_PRIVATE).getInt("config_network_delay",500)/100);
                builder.setView(mnetWorkDialogView);
                builder.setTitle("修改网络延迟时间(单位:ms)");
                builder.setMessage("网络延迟时间调小可能造成卡顿!\n建议普通玩家不要尝试!");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_myadd));
                builder.setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("networkdelay", "onClick: "+mNetWorkNumberPicker.getValue());
                                configNetwork(mNetWorkNumberPicker.getValue()*100);
                            }
                        });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });



    }

    //设置短信修改条数

    private void configSms(int smsNumber) {
        SharedPreferences.Editor editor = getSharedPreferences("config", MODE_PRIVATE).edit();
        editor.putString("config_sms_num", String.valueOf(smsNumber));
        editor.apply();
    }
    //设置短信生效数
    private void configSearchSms(int smsSearchNum){
        SharedPreferences.Editor editor = getSharedPreferences("config", MODE_PRIVATE).edit();
        editor.putString("config_sms_search_num", String.valueOf(smsSearchNum));
        editor.apply();
    }

    //翻译生效字符数
    private  void configTranslation(int translationNum){
        SharedPreferences.Editor editor=getSharedPreferences("config",MODE_PRIVATE).edit();
        editor.putInt("config_translation_num",translationNum);
        editor.apply();
    }

    //设置百度搜索建议生效数
    private void configBaiduNum(int baiduNum){
        SharedPreferences.Editor editor=getSharedPreferences("config",MODE_PRIVATE).edit();
        editor.putInt("config_baidu_num",baiduNum);
        editor.apply();
    }

    //设置网络延迟时间

    private void configNetwork(int networkDelay){
        SharedPreferences.Editor editor=getSharedPreferences("config",MODE_PRIVATE).edit();
        editor.putInt("config_network_delay",networkDelay);
        editor.apply();
    }


}
