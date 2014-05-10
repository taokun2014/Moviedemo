package cn.tk.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {
	
	/**
	 * 根据豆瓣API下载电影信息的json数据
	 * @param url 根据用户输入的keywords 拼接上豆瓣api
	 * @return json数据
	 */
	
	public static String downloadjsondata(String url){
		String result = "";
		StringBuffer sb = new StringBuffer();
		try {
			URL pathUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) pathUrl.openConnection();
			conn.setConnectTimeout(3000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			String line = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			result = sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static Bitmap downloadBitmap (String bitmapUrl){
		Bitmap bm=null;
        InputStream is =null;
        BufferedInputStream bis=null;
        try{
            URL url=new URL(bitmapUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            bis=new BufferedInputStream(connection.getInputStream());
            bm= BitmapFactory.decodeStream(bis);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(bis!=null)
                    bis.close();
                if (is!=null)
                    is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bm;
	}
}
