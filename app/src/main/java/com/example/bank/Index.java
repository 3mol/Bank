package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class Index extends AppCompatActivity implements View.OnClickListener {
    Button login;
    Button registration;
    EditText user;
    EditText pwd;
    CheckBox ch_remember;
    CheckBox ch_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        registration = (Button) findViewById(R.id.Registration);
        login = (Button) findViewById(R.id.login);
        ch_remember = (CheckBox) findViewById(R.id.remember);
        ch_auto = (CheckBox) findViewById(R.id.ch_auto);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Index.this, Registration.class));
            }
        });
        login.setOnClickListener(this);
        load();
    }

    @Override
    protected void onStart() {
        load();
        super.onStart();
    }

    private void load() {
        try {
            FileInputStream fs = openFileInput("user.dat");
            int len = fs.available();
            if (len > 0) {
                byte[] b = new byte[len];
                fs.read(b);
                String[] userDat = new String(b, "UTF-8").split("\n");
                user.setText(userDat[0]);
                pwd.setText(userDat[1]);
                ch_remember.setChecked(true);
                if (userDat[2].equals("true")){
                    ch_auto.setChecked(true);
                }else {
                    ch_auto.setChecked(false);
                }
                fs.close();
                System.out.println("恢复数据成功&&尝试自动登陆!");
                if (ch_auto.isChecked()) {
                    login.performClick();
                } else {
                    System.out.println("未勾选自动登陆");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        final String userName = user.getText().toString();
        final String password = pwd.getText().toString();
        if (userName.length() < 2 || password.length() < 3) {
            Toast.makeText(Index.this, "用户名或密码长度不符！", Toast.LENGTH_SHORT).show();
        } else {
            HttpUtil.sendHttpRequest("http://hyjvol.55555.io/getInfo.php?name=" + userName + "&pwd=" + password, new okhttp3.Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("失败！请重试！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if (result.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Index.this, "登陆失败!\n不存在用户或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        FileOutputStream fs = openFileOutput("user.dat", MODE_PRIVATE);
                        if (ch_remember.isChecked()) {
                            fs.write(userName.getBytes());
                            fs.write("\n".getBytes());
                            fs.write(password.getBytes());
                            fs.write("\n".getBytes());
                            if (ch_auto.isChecked()) {
                                fs.write("true".getBytes());
                            } else {
                                fs.write("false".getBytes());
                            }
                        } else {
                            File f = new File(getApplicationContext().getFilesDir().getAbsolutePath());
                            if (f.exists()) {
                                f.delete();
                            }
                        }
                        fs.close();
                        Intent intent = new Intent(Index.this, Main.class);
                        intent.putExtra("data", result);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
}
