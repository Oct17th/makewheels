package tomcat.httpserver0;

import java.io.*;

/**
 * 表示一个response响应
 * <p>
 * 类中封装socket.outputStream，做静态资源的转发
 *
 * @author YiJie  2017/7/24
 **/
public class Response {
    /**
     * 静态资源位置
     */
    private static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "webapp";

    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 响应静态资源
     *
     * @param URI 请求静态资源地址，若不存在则返回404页面
     */
    public void sendStaticResource(String URI) {
//        PrintWriter printWriter = new PrintWriter(outputStream, true);
        File file = new File(WEB_ROOT, URI);
        int status;
        String statusMes;
        if (file.exists()) {
            System.out.println("请求页面：" + URI);
            status = 200;
            statusMes = "OK";
        } else {
            file = new File(WEB_ROOT, "404.html");
            status = 404;
            statusMes = "File Not Found";
        }
        try ( FileInputStream fileInputStream = new FileInputStream(file)){
            //响应主体内容长度
            int contentLength = fileInputStream.available();

            //写入响应头
            String response_header = "HTTP/1.1 " + status + statusMes + "\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + contentLength + "\r\n"
                    + "\r\n";
            outputStream.write(response_header.getBytes());
//            printWriter.print(response_header);

            //写入响应正文
            byte[] content = new byte[contentLength];
            fileInputStream.read(content);
            outputStream.write(content);
//            printWriter.print(content);

            System.out.println("写入页面结束\n------------------------------------------------");
        } catch (FileNotFoundException e) {
            //断言不会出现此异常
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
