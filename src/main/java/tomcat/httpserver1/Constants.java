package tomcat.httpserver1;

import java.io.File;

/**
 * 存放系统各路径常量
 *
 * @author YiJie  2017/7/26
 **/
public class Constants {
    /**
     * 静态资源位置
     */
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "webapp";

    public static final String Servlet_Package = "tomcat.httpserver1.servlet.";//TODO 通过路径取得包前缀


    /**
     * 私有化构造方法，因为该类只通过类名返回常量值，无需创建对象
     */
    private Constants(){}
}
