package healthwatcher.login.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class GoogleLogin {

	public static boolean authenticate(String login, String pwd){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		 HttpPost httpost=new HttpPost("https://www.google.com/accounts/ClientLogin?accountType=GOOGLE&service=cl&source=sample");
		 List <NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("Email", login));
        nvps.add(new BasicNameValuePair("Passwd", pwd));

        try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

        try {
			HttpResponse response = httpClient.execute(httpost);
			
			if (response.getStatusLine().getReasonPhrase().equals("OK")){
				return true;
			}else 
			{
				return false;
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
