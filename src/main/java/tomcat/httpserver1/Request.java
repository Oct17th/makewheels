package tomcat.httpserver1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 封装HTTP请求数据
 * 同0.1版本的比较，另继承了ServletRequest接口，更全面的解析出Http请求数据
 *
 * @author YiJie  2017/7/26
 **/
public class Request implements ServletRequest {
    private String URI;
    private InputStream inputStream;

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        this.URI = parseURI();
    }

    public String getURI() {
        return this.URI;
    }

    /**
     * 解析Http请求数据
     */
    public void parse() {
    }

    /**
     * 之所以把这个设为私有，是避免每次获取URI都要解析一遍
     *
     * @return 返回静态资源URI
     */
    private String parseURI() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * request_line = [request_method] [request_uri] [protocol]
             */
            String request_line = bufferedReader.readLine();
            String uri = request_line.split(" ")[1];//取到请求头，第一行第二个词，为请求页面
            return uri.substring(1);//去掉字符句首的"/"
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {//request_line为空或request_line中没有uri的状态
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getAttribute(String s) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String s) {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String s) {
        return new String[0];
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    public String getRealPath(String s) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }
}
