package org.jsmall.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

    private static final String KEY_AES = "AES";
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA-1";
    private static final int Bit_128 = 128;

    /**
     * 用MD5算法进行加密 
     * 
     * @param str 需要加密的字符串 
     * @return MD5加密后的结果 
     */
    public static String encodeMD5String(String str) {
        return encode(str, KEY_MD5);
    }

    /**  
     * 用SHA算法进行加密  
     * 
     * @param str 需要加密的字符串  
     * @return SHA加密后的结果  
     */
    public static String encodeSHAString(String str) {
        return encode(str, KEY_SHA);
    }

    /**
     * 加密算法
     * 
     * @param plainText
     * @param method
     * @return 加密后的结果
     */
    public static String encode(String plainText, String method) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                if (b[offset] < 0)
                    b[offset] += 256;
                if (b[offset] < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(b[offset] & 0xFF));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * AES加密 
     * 
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */
    public static String AESEncode(String content, String password) {
        try {
            // 密钥生成器
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(Bit_128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            // 生成密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);
            // 加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            return parseByte2HexStr(cipher.doFinal(content.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     * 将二进制转换成十六进制 
     *
     * @param buf
     * @return 
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * AES解密 
     * 
     * @param content待解密内容 
     * @param password 解密密钥 
     * @return 
     */
    public static String AESDncode(String content, String password) {
        try {
             // 密钥生成器
             KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
             kgen.init(Bit_128, new SecureRandom(password.getBytes()));
             SecretKey secretKey = kgen.generateKey();
             byte[] enCodeFormat = secretKey.getEncoded();
             // 生成密钥
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_AES);
             // 创建密码器
             Cipher cipher = Cipher.getInstance(KEY_AES);
             // 解密模式
             cipher.init(Cipher.DECRYPT_MODE, key);
             byte[] result = cipher.doFinal(parseHexStr2Byte(content));
             // 解密
             return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将十六进制转换为二进制
    
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr == null || hexStr.length() < 1)
            return null;

        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
