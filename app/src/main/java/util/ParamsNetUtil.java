package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ParamsNetUtil {
    public static String Request(String url,String way) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            // 建立连接
            URL requestURL = new URL(url);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(way);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();

            // 从连接里获取输入流（二进制）
            InputStream inputStream = urlConnection.getInputStream();

            // 将输入流包装转换成 BufferedReader （我们人易懂的文本）
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // 从 BufferedReader 中一行行读取字符串,用 StringBuilder 接收
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                return null;
            }

            bookJSONString = builder.toString();


            //...
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //...
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bookJSONString;
    }

    public static String getReq(String req,String params,String way) {
        String BaseUrl = "http://39.108.8.211";
        String realUrl = BaseUrl+req+params;
//        System.out.println(realUrl);
        return Request(realUrl,way);
    }
}
