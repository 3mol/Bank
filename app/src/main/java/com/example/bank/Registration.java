package com.example.bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Registration";
    Button agree;
    EditText re_user;
    EditText re_pwd;
    EditText re_email;
    EditText re_phone;
    CheckBox checkBox;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        agree = (Button) findViewById(R.id.agree);
        re_user = (EditText) findViewById(R.id.re_user);
        re_pwd = (EditText) findViewById(R.id.re_pwd);
        re_email = (EditText) findViewById(R.id.re_email);
        re_phone = (EditText) findViewById(R.id.re_phone);
        checkBox = (CheckBox) findViewById(R.id.re_check);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    agree.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    agree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
        agree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String user = re_user.getText().toString().trim();
        final String email = re_email.getText().toString().trim();
        final String phone = re_phone.getText().toString().trim();
        final String pwd = re_pwd.getText().toString().trim();

        boolean isEmpty = TextUtils.isEmpty(user)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(phone);
        boolean isUserName = LoginUtil.isUserNameRule(user);
        boolean ispwd = LoginUtil.isUserNameRule(pwd);
        boolean isEmail = LoginUtil.isEmail(email);
        boolean isPhone = LoginUtil.isPhone(phone);
        if (isEmpty) {
            Toast.makeText(Registration.this, "信息不能为空!", Toast.LENGTH_SHORT).show();
        } else {
            if (!isUserName || !ispwd) {
                Toast.makeText(Registration.this, "用户名和密码的长度不能小于6\n" +
                                "且必须以字母开头",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (!isEmail) {
                    Toast.makeText(Registration.this, "邮箱格式错误!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    if (!isPhone) {
                        Toast.makeText(Registration.this, "手机号码格式错误!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        //同意所有.
                        if (checkBox.isChecked()) {

                            String address = "http://hyjvol.55555.io/Registration.php?name="
                                    + user + "&pwd=" + pwd + "&phone=" + phone + "&email=" + email;
                            Log.d(TAG, "onClick: " + address);
                            HttpUtil.sendHttpRequest(address, new okhttp3.Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    System.out.println("连接服务器失败！请重试！");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String result = response.body().string();
                                    Log.d(TAG, "onResponse: " + result);
                                    if (result.equals("0")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Registration.this, "注册失败!\n" +
                                                        "用户已存在,请修改信息重试", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        try {
                                            FileOutputStream fs = openFileOutput("user.dat", MODE_PRIVATE);
                                            fs.write(user.getBytes());
                                            fs.write("\n".getBytes());
                                            fs.write(pwd.getBytes());
                                            fs.write("\n".getBytes());
                                            fs.write("false".getBytes());
                                            fs.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(Registration.this)
                                                        .setTitle("账户成功注册!")//设置对话框标题
                                                        .setMessage("是否直接登陆到系统")//设置显示的内容
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                                startActivity(new Intent(Registration.this, Index.class));
                                                                finish();
                                                            }
                                                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                                    public void onClick(DialogInterface dialog, int which) {//响应事件
                                                        Log.d("tag", "alertdialog! 请保存数据！");
                                                    }
                                                }).show();//在按键响应事件中显示此对话框
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Registration.this, "请勾选同意选项!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }
}
