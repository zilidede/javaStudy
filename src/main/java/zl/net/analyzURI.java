package zl.net;

/*
 * @Description: 解析uri
 * @Param:
 * @Author: zl
 * @Date: 2019/4/28 11:18
 */
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

public class analyzURI {
    public static void main(String[] args) {
        try {
            URI absolute = new URI("http://www.baidu.com/serach?h1=en");

            URI relative = new URI("image/1.jpg");
            URI resolved = relative.relativize(absolute);
            System.out.println(URLDecoder.decode("http://www.baidu.com/12/","utf-8"));
            System.out.println(resolved);
        }
        catch (URISyntaxException e){
            e.printStackTrace();

        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();

        }


    }
}
