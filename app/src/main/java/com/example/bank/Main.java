package com.example.bank;

import android.content.Intent;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";
    Button but_exit;
    TextView tv_name;
    TextView tv_phone;
    TextView tv_cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv_name = (TextView) findViewById(R.id.name);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_cash = (TextView) findViewById(R.id.cash);
        but_exit = (Button) findViewById(R.id.but_exit);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        init(data);
        but_exit.setOnClickListener(this);
    }

    private void init(String data) {

        try {
            System.out.println(data);
            JSONObject jsonData = new JSONObject(data);
            tv_name.setText(jsonData.get("name").toString());
            tv_phone.setText(jsonData.get("phone").toString());
            tv_cash.setText(jsonData.get("cash").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        changeData();
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }

    private void changeData() {
        /**
         * describe : set文件自动登陆字段
         * 胡宇靖 2017/4/9 0009
         * @param []
         * @return void
         */
        try {
            FileInputStream fileInputStream = openFileInput("user.dat");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String data = new String(bytes, "utf-8");
            data = data.replace("true", "false");
            fileInputStream.close();
            Log.d(TAG, "changeData: " + data);
            FileOutputStream fileOutputStream = openFileOutput("user.dat", MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
