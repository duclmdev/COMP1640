package edu.fpt.comp1640;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.Filter;
import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String... args) throws Exception {
        System.out.println(Arrays.toString(args));
        Tomcat tomcat = new Tomcat();
        Service service = tomcat.getService();
        service.addConnector(getSslConnector());

        Context context = tomcat.addWebapp("", new File("src/main/web").getAbsolutePath());
        context.addFilterDef(createFilterDef("pippoFilter", new EmptyFilter()));
        context.addFilterMap(createFilterMap("pippoFilter", "/rest"));
        context.getServletContext().setAttribute(Globals.ALT_DD_ATTR, new File("src/main/web/WEB-INF/web.xml").getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }

    private static Connector getSslConnector() {
        return new Connector() {{
            setPort(8080);
            setSecure(true);
            setScheme("https");
            setAttribute("keyAlias", "tomcat");
            setAttribute("keystorePass", "password");
            setAttribute("keystoreType", "JKS");
            setAttribute("keystoreFile", Main.class.getResource("keystore.jks").getPath());
            setAttribute("clientAuth", "false");
            setAttribute("protocol", "HTTP/1.1");
            setAttribute("sslProtocol", "TLS");
            setAttribute("maxThreads", "200");
            setAttribute("protocol", "org.apache.coyote.http11.Http11AprProtocol");
            setAttribute("SSLEnabled", true);
        }};
    }

    private static FilterDef createFilterDef(String filterName, Filter filter) {
        return new FilterDef(){{
            setFilterName(filterName);
            setFilter(filter);
        }};
    }

    private static FilterMap createFilterMap(String filterName, String urlPattern) {
        return new FilterMap() {{
            setFilterName(filterName);
            addURLPattern(urlPattern);
        }};
    }
}
