package com.ly.lubusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ly.lubus.LuBusManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.init).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LuBusManager.getInstance().init(MainActivity.this, getResources().getStringArray(R.array.lu_path));
                    }
                }).start();
                break;
            case R.id.btn1:
                LuBusManager.getInstance().call(this, "one/test", null);
                break;
            case R.id.btn2:
                LuBusManager.getInstance().call(this, "one/test1", null);
                break;
            case R.id.btn3:
                LuBusManager.getInstance().call(this, "one/test2", null);
                break;
            case R.id.btn4:
                LuBusManager.getInstance().call(this, "two/test", null);
                break;
            case R.id.btn5:
                LuBusManager.getInstance().call(this, "two/test1", null);
                break;
            case R.id.btn6:
                LuBusManager.getInstance().call(this, "two/test2", null);
                break;
        }
    }
}
