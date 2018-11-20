package cn.easybuy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;


/**
 * 短信发送
 * @author 徐端
 */
public class GetMessageCode {
	
	private static String QUERY_PATH="https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	
	//账号ID
	private static String ACCOUNT_SID="cb32a4a05f59438f9d05e8513f194532";
	//密码
	private static String AUTH_TOKEN="079de26157d54df78b2e20347c25d497";
	
	//根据相应的手机号发送验证码
	public static String getCode(String phone){
		String rod=smsCode();
		
		String tamp="【迈拓科技】登录验证码："+rod+"，如非本人操作，请忽略此短信。";
		String timestamp=getTimestamp();
		String sig=getMD5(ACCOUNT_SID,AUTH_TOKEN,timestamp);
		OutputStreamWriter out=null;
		BufferedReader br=null;
		StringBuilder result=new StringBuilder();
		try {
			URL url=new URL(QUERY_PATH);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);//设置是否允许数据写入
			connection.setDoOutput(true);//设置是否允许参数数据输出
			connection.setConnectTimeout(5000);//设置链接响应时间
			connection.setReadTimeout(10000);//设置参数读取时间
			connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");			
			//提交请求
			out=new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			String args=getQueryArgs(ACCOUNT_SID, tamp, phone, timestamp, sig, "JSON");
			out.write(args);
			out.flush();
			//读取返回参数
			br=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String temp="";
			while((temp=br.readLine())!=null){
				result.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json=new JSONObject();
		String respCode=json.getString("respCode");
		if(rod.equals(respCode)){
			 return rod;
		}else{
			return rod;			
		}
	}
	//定义一个请求参数拼接方法
	public static String getQueryArgs(String accountSid,String smsContent,String to,String timestamp,String sig,String respDataType){
		return "accountSid="+accountSid+"&smsContent="+smsContent+"&to="+to+"&timestamp="+timestamp+"&sig="+sig+"&respDataType="+respDataType;
	}
	//获取时间戳
	public static String getTimestamp(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	//sing签名
	public static String getMD5(String sid,String token,String timestamp){
		StringBuilder result=new StringBuilder();
		String source=sid+token+timestamp;
		//获取某个类的实例
				try {					
				   MessageDigest digest=MessageDigest.getInstance("MD5");
				   //要进行加密的东西
				   byte[] bytes=digest.digest(source.getBytes());
				   for(byte b:bytes){
					   String hex=Integer.toHexString(b&0xff);
					   if(hex.length()==1)
						   result.append("0"+hex);
					   else
						   result.append(hex);
				   }
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} 
		return result.toString();
	}
	//创建验证码
	public static String smsCode(){
		String random=(int)((Math.random()*9+1)*100000)+"";		
		return random;
	}
}
