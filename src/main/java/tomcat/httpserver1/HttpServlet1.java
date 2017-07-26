package tomcat.httpserver1;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写实现Http服务器 v1.0
 * 一个简单的servlet容器。可以提供静态资源和servlet访问
 * <p>
 * request/response使用外观模式
 *
 * @author YiJie  2017/7/24
 **/
public class HttpServlet1 {
    private static final int port = 8520;

    public static void main(String[] args) {
        String SHUTDOWN_URI = "shutdown";
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Request request = new Request(socket.getInputStream());
                String uri = request.getURI();

                if (uri == null) continue;
                if (uri .equals(SHUTDOWN_URI)) return;

                Response response = new Response(socket.getOutputStream());
                if (uri.startsWith("servlet")) {
                    Servlet servlet = new ServletHandler().handle(uri);
                    servlet.service(request, response);
                } else {
                    response.sendStaticResource(uri);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
