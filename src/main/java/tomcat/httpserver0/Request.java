package tomcat.httpserver0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 表示一个Http请求
 * 类中主要做Http请求参数的解析
 *
 * @author YiJie  2017/7/24
 **/
public class Request {
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
        //TODO 读rfc文档，解析HTTP请求头的方式麻烦profession一点
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String page = bufferedReader.readLine().split(" ")[1];//取到请求头，第一行第二个词，为请求页面
            return page.substring(1);//去掉字符句首的"/"
        } catch (NullPointerException e){
           return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
