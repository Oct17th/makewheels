package tomcat.httpserver1;

import javax.servlet.Servlet;

/**
 * @author YiJie  2017/7/26
 **/
public class ServletHandler {

    public Servlet handle(String uri) {
        try {
//            this.getClass().getClassLoader().loadClass(servletName.substring(servletName.indexOf("/") + 1));
            String servletName = uri.substring(uri.indexOf("/") + 1);
//            ClassLoader classLoader = new URLClassLoader(new URL[]{new URL(new URL(Constants.WEB_ROOT),"servlet"+ File.separator,null)});
//            URL context = null;
//            URLStreamHandler handler = null;
//            Class c = classLoader.loadClass(servletName);
            Class c = Class.forName(Constants.Servlet_Package + servletName);
            Servlet servlet = (Servlet) c.newInstance();
            return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
