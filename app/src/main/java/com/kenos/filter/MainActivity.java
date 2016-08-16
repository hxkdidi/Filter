package com.kenos.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private static final String TAG = "ColorUtil";
    private int titleViewHeight = 50; // 标题栏的高度
    private int headViewHeight = 180; // 头部的高度
    private int headViewTopSpace; // 头部距顶部的距离
    private RelativeLayout rl_bar;
    private View view_title_bg;
    private View view_action_more_bg;
    private boolean isScrollIdle;
    private ListView listView;
    private View itemHeaderView;
    private List<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        rl_bar = (RelativeLayout) findViewById(R.id.rl_bar);
        view_title_bg = findViewById(R.id.view_title_bg);
        view_action_more_bg = findViewById(R.id.view_action_more_bg);
        listView = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.header_ad_layout, listView, false);
        listView.addHeaderView(view);
        for (int i = 0; i < 20; i++) {
            strings.add(i + "");
        }
        DevAdapter devAdapter = new DevAdapter(this, strings);
        listView.setAdapter(devAdapter);
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isScrollIdle && headViewTopSpace < 0) return;
        // 获取头部View、自身的高度、距离顶部的高度
        if (itemHeaderView == null) {
            itemHeaderView = listView.getChildAt(0);
        }
        if (itemHeaderView != null) {
            headViewTopSpace = DensityUtil.px2dip(this, itemHeaderView.getTop());
            headViewHeight = DensityUtil.px2dip(this, itemHeaderView.getHeight());
        }
        Log.i(TAG, " headViewTopSpace= " + headViewTopSpace + " headViewHeight= " + headViewHeight);
        handleTitleBarColorEvaluate();
    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (headViewTopSpace > 0) {
            fraction = 1f - headViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rl_bar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(headViewTopSpace) * 1f;
        fraction = space / (headViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rl_bar.setAlpha(1f);

        if (fraction >= 1f) {
            view_title_bg.setAlpha(0f);
            view_action_more_bg.setAlpha(0f);
            rl_bar.setBackgroundColor(this.getResources().getColor(R.color.orange));
        } else {
            view_title_bg.setAlpha(1f - fraction);
            view_action_more_bg.setAlpha(1f - fraction);
            rl_bar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(this, fraction, R.color.transparent, R.color.orange));
        }
    }
}
