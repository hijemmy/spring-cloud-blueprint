package com.jemmy.common.security;

import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

/**
 * @author Jemmy
 */

public enum  CryDesUtil {
    /**
     * 单例模式
     */
    INSTANCE;

    /**
     *
     * @param rawText
     * @param key
     * @param iv
     * @return base64编码
     */
    public String descrypt(String rawText,String key,byte[] iv){
        byte[] bt;
        try {
            bt = decrypt(Base64.decode(rawText.getBytes()), key,iv);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new String(bt);
    }
    /**
     *
     * @param rawText
     * @param key 长度需为8的倍数
     * @return
     */
    public String encrypt(String rawText,String key,byte[] iv){
        byte[] bt = encryptByKey(rawText.getBytes(), key,iv);
        return new String(Base64.encode(bt));
    }

    /**
     * 加密
     * @param datasource byte[]
     * @param key String
     * @return byte[]
     */
    private static byte[] encryptByKey(byte[] datasource, String key,byte[] iv) {
        try{

            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密匙初始化Cipher对象
            IvParameterSpec ivParameterSpec;
            if(null==iv){
                byte[] ivs=new byte[8];
                Arrays.fill(ivs,(byte)0);
                ivParameterSpec=new IvParameterSpec(ivs);
            }else {
                ivParameterSpec=new IvParameterSpec(iv);
            }
            cipher.init(Cipher.ENCRYPT_MODE, securekey, ivParameterSpec);

            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param src byte[]
     * @param key String
     * @return byte[]
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, String key,byte[] iv) throws Exception {
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        IvParameterSpec ivParameterSpec;
        if(null==iv){
            byte[] ivs=new byte[8];
            Arrays.fill(ivs,(byte)0);
            ivParameterSpec=new IvParameterSpec(ivs);
        }else {
            ivParameterSpec=new IvParameterSpec(iv);
        }
        cipher.init(Cipher.DECRYPT_MODE, securekey, ivParameterSpec);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}
