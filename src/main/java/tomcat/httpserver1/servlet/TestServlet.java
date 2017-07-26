package tomcat.httpserver1.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author YiJie  2017/7/26
 **/
public class TestServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("------------>before print");
        servletResponse.getWriter().print("httpserver2 test");
        System.out.println("------------>after print");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
