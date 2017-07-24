package tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写实现Http服务器
 *
 * @author YiJie  2017/7/24
 **/
public class HttpServerTest {
    /**
     * 资源路径
     */
    public static final String PAGE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "webapp";
    /**
     * 端口号
     */
    public static int port = 9999;

    /*
    1.开启服务端端口监听
    2.循环监听端口请求
    3.持续阻塞，直到接收到socket，读取端口输入流，解析HTTP数据头
    4.读取解析出请求类型，请求文件名
    5.在项目中查找该文件
        1)若找到，设置Http响应头状态码为200，并通过文件输入流读取文件，存储为响应主体文本内容
        2)若未找到，设置Http响应头状态码为404，并通过文件输入流读取404页面，存储为响应主体文本内容
    6.封装Http响应头，设置Content-Length为响应主体文本字长，返回响应数据
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("等待页面访问...\n");
            while (true) {
                Socket socket = serverSocket.accept();//阻塞状态
                System.out.println("监听到socket");
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//TODO 读rfc文档，解析HTTP请求头的方式麻烦profession一点
                String page = bufferedReader.readLine().split(" ")[1];//取到请求头，第一行第二个词，为请求页面

                OutputStream outputStream = socket.getOutputStream();
//                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                FileInputStream fileInputStream;
                int status;
                try {
                    fileInputStream = new FileInputStream(PAGE_PATH + page);
                    System.out.println("请求页面：" + page);
                    status = 200;
                } catch (FileNotFoundException e) {//没有找到文件，则返回404页面
                    fileInputStream = new FileInputStream(PAGE_PATH + File.separator + "404.html");
                    status = 404;
                }
                //响应主体内容长度
                int contentLength = fileInputStream.available();

                //写入响应头
                String response_header = "HTTP/1.1 " + status + " OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: " + contentLength + "\r\n"
                        + "\r\n";
                outputStream.write(response_header.getBytes());
//                printWriter.print(response_header);

                //写响应正文
                byte[] content = new byte[contentLength];
                fileInputStream.read(content);
                outputStream.write(content);
//                printWriter.print(content);

                System.out.println("写入页面结束\n------------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
