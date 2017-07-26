package tomcat.httpserver1;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * 封装HTTP请求数据
 * 同0.1版本的比较，另继承了ServletResponse接口，更全面的封装Http响应数据
 *
 * @author YiJie  2017/7/26
 **/
public class Response implements ServletResponse {

    private OutputStream outputStream ;
    private PrintWriter printWriter;

    public Response (OutputStream outputStream){
        this.outputStream = outputStream;
        this.printWriter = new PrintWriter(outputStream,true);
    }

    /**
     * 响应静态资源
     *
     * @param URI 请求静态资源地址，若不存在则返回404页面
     */
    public void sendStaticResource(String URI) {
//        PrintWriter printWriter = new PrintWriter(outputStream, true);
        File file = new File(Constants.WEB_ROOT, URI);
        int status;
        String statusMes;
        if (file.exists()) {
            System.out.println("请求页面：" + URI);
            status = 200;
            statusMes = "OK";
        } else {
            file = new File(Constants.WEB_ROOT, "404.html");
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

        } catch (FileNotFoundException e) {
            //断言不会出现此异常
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    /**
     * 供servlet调用
     *
     * @return
     * @throws IOException
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
