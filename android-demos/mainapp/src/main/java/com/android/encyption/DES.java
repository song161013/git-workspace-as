package com.android.encyption;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by 别乱动 on 2017/12/5.
 */

public class DES {
    private static final String TAG = "DES";
    public static final String PASSWORD_ENC_SECRET = "123456123456123456123456";

    /**
     * 加密
     */
    public static String encryptPassword(String clearText) {
        try {
            DESKeySpec keySpec = new DESKeySpec(PASSWORD_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
                    .getBytes("UTF-8")), Base64.DEFAULT);
            return encrypedPwd;
        } catch (Exception e) {
            Log.e(TAG, "异常了：" + e);
        }
        return clearText;
    }

    /**
     * 解密
     **/
    public static String decryptPassword(String encryptedPwd) {
        try {
            DESKeySpec keySpec = new DESKeySpec(PASSWORD_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encryptedWithoutB64 = Base64.decode(encryptedPwd, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
            return new String(plainTextPwdBytes);
        } catch (Exception e) {
            Log.e(TAG, "异常了：" + e);
        }
        return encryptedPwd;
    }
}
