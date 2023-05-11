package cn.com.aiooo;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES_Impl {
    private static final String CHARSET_NAME = "gb2312";
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_ALGORITHM = "AES";
    private static final int BLOCK_SIZE = 16;
    private static final Charset CHARSET = Charset.forName(CHARSET_NAME);

    public static String AES_pkcs5_encrypt(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes(CHARSET);

        if (keyBytes.length < 16) {
            // 将秘钥填充至16字节
            byte[] keyBytesCorrectSize = new byte[16];
            System.arraycopy(keyBytes, 0, keyBytesCorrectSize, 0, keyBytes.length);
            keyBytes = keyBytesCorrectSize;
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] inputBytes = data.getBytes(CHARSET);

        return Base64.getEncoder().encodeToString(cipher.doFinal(inputBytes));
    }

    public static String AES_pkcs5_decrypt(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes(CHARSET);

        if (keyBytes.length < 16) {
            // 将秘钥填充至16字节
            byte[] keyBytesCorrectSize = new byte[16];
            System.arraycopy(keyBytes, 0, keyBytesCorrectSize, 0, keyBytes.length);
            keyBytes = keyBytesCorrectSize;
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] dataBytes = Base64.getDecoder().decode(data);
        byte[] decryptedBytes = cipher.doFinal(dataBytes);
        return new String(decryptedBytes, CHARSET);
    }

}
