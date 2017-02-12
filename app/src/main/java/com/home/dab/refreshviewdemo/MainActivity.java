package com.home.dab.refreshviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.home.dab.refreshviewdemo.demo.DemoAdapter;
import com.home.dab.refreshviewdemo.refresh.RefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView mTvHintHead, mTvHintFoot;
    private RefreshView mRefreshView;
    private DemoAdapter mDemoAdapter;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mDemoAdapter);
        mDemoAdapter.setData(mStringList);
        mRefreshView.setRefreshViewListener(new RefreshView.OnRefreshViewListener() {
            @Override
            public void onPullDownDistanceChange(int refreshFlag, int startRefreshDistance, int distance) {
                if (refreshFlag == RefreshView.FLAG_PULL_DOWN) {
                    mTvHintHead.setText("刷新的距离为:" + startRefreshDistance + "****当前距离为:" + distance);
                } else if (refreshFlag == RefreshView.FLAG_PULL_UP) {
                    mTvHintFoot.setText("刷新的距离为:" + startRefreshDistance + "****当前距离为:" + distance);
                }
            }

            @Override
            public void onRefreshing(final int refreshFlag) {
                if (refreshFlag == RefreshView.FLAG_PULL_DOWN) {
                    mTvHintHead.setText("刷新中...");
                } else if (refreshFlag == RefreshView.FLAG_PULL_UP) {
                    mTvHintFoot.setText("刷新中...");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            mRefreshView.finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });
    }

    private void initData() {
        mStringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mStringList.add(i + "");
        }
        mDemoAdapter = new DemoAdapter();
    }

    private void initView() {
        mTvHintFoot = (TextView) findViewById(R.id.tv_refresh_hint_foot);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mTvHintHead = (TextView) findViewById(R.id.tv_refresh_head);
        mRefreshView = (RefreshView) findViewById(R.id.refresh);
    }
}
