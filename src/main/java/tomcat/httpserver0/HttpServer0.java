package tomcat.httpserver0;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写实现Http服务器 v0.1
 * <p>
 * 实现简单静态资源（页面）的访问
 * 通过socket拿到输入输出流，进行http数据头解析及封装，发送静态资源
 *
 * @author YiJie  2017/7/24
 **/
public class HttpServer0 {
    /**
     * 端口号
     */
    public static int port = 9999;

    /*
    1.开启服务端端口监听
    2.循环监听端口请求
    3.持续阻塞，直到接收到socket，读取端口输入流，解析HTTP请求头
    4.读取解析出请求类型，请求文件名
    5.在项目中查找该文件
        1)若找到，设置Http响应头状态码为200，并通过文件输入流读取文件，存储为响应主体文本内容
        2)若未找到，设置Http响应头状态码为404，并通过文件输入流读取404页面，存储为响应主体文本内容
    6.封装Http响应头，设置Content-Length为响应主体文本字长，返回响应数据
     */
    public static void main(String[] args) {
        String SHUTDOWN_URI = "shutdown";
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("等待页面访问...\n");
            while (true) {
                Socket socket = serverSocket.accept();//阻塞状态
                System.out.println("监听到socket");
                Request request = new Request(socket.getInputStream());
                String page = request.getURI();

                if (page == null) continue;
                if (page .equals(SHUTDOWN_URI)) return;

                System.out.println(page);
                OutputStream outputStream = socket.getOutputStream();//TODO 返回404页面到底应该放在request中做还是放在response中做
                Response response = new Response(outputStream);
                response.sendStaticResource(page);
                socket.close();//关闭此套接字也将会关闭该套接字的 InputStream 和 OutputStream
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
