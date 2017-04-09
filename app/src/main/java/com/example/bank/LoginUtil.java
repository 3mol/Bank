package com.example.bank;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

/**
 * Created by 胡宇靖 on 2017/4/6 0006.
 */

public class LoginUtil {
    public static final boolean isEmail(String email) {
        return email.matches("\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}");
    }

    public static final boolean isUserNameRule(String user) {
        if (user.length() >= 6) {
            char ch = user.charAt(0);
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') return true;
            else return false;
        } else return false;
    }

    public static final boolean isPhone(String phone) {
        return phone.matches("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
    }

    public static final String enPwd(String pwd) {
        MessageDigest md5 = null;
        String md5_result = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5_byte = md5.digest(pwd.getBytes("UTF-8"));
            md5_result = new String(md5_byte);
            return md5_result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5_result;
    }

/*    public static boolean existPassWord(){
        return
    }*/
}
