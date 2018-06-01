//package com.sub.syn.youhuiquan;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.ParseException;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.jd.open.api.sdk.DefaultJdClient;
//import com.jd.open.api.sdk.JdClient;
//import com.jd.open.api.sdk.JdException;
//import com.jd.open.api.sdk.request.cps.UnionThemeGoodsServiceQueryCouponGoodsRequest;
//import com.jd.open.api.sdk.response.cps.UnionThemeGoodsServiceQueryCouponGoodsResponse;
//import com.sub.syn.common.Common;
//
///**
// * 淘客链接提取商品ID
// * @author wuyb
// *
// */
//public class TestTKJDJob extends ParentYhyJob{
//
//	private static Log log=LogFactory.getLog(TestTKJDJob.class);
//	
//	/***淘宝短连接***/
//	private String url = "https://oauth.jd.com/oauth/authorize?response_type=code&client_id=287F253B17614782BE54E969DA55DA6B&redirect_uri=http://www.quangou365.com&state=wuyb";
//	
//	private int page = 1;
//	
//	public void synJob() {
//		//String json = httpGetRequest(url.replace("[page]", page + ""));
//		//System.out.println(json);
//		
//		
//		String url ="https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id="+Common.appkeyJd
//				+"&client_secret="+ Common.appSecret
//				+"&scope=read&redirect_uri=http://www.quangou365.com"
//				+"&code="+ Common.code
//				+"&state=1234";
//		String json = httpGetRequest(url.replace("[page]", page + ""));
//		System.out.println(json);
////		URL uri = new URL(url);
////				HttpURLConnection conn =(HttpURLConnection) uri.openConnection();
////				conn.setRequestProperty("Accept-Charset","utf-8");
////				conn.setRequestMethod("POST");
////				int code = conn.getResponseCode();
////				InputStream is =conn.getInputStream();
////				String jsonStr =inputStream2String(is);
////				StringaccessToken = this.getAccessToken(jsonStr);
//		
//		
//	}
//
//	public static void main(String[] args) throws ParseException {
//		//TestTKJDJob b = new TestTKJDJob();
//		//b.synJob();
//		
//		
//		JdClient client=new DefaultJdClient(Common.SERVER_URL,Common.accessToken,Common.appkeyJd,Common.appSecret);
//
//		UnionThemeGoodsServiceQueryCouponGoodsRequest request=new UnionThemeGoodsServiceQueryCouponGoodsRequest();
//
//		request.setFrom( 123 );
//		request.setPageSize( 123 );
//
//		try {
//			UnionThemeGoodsServiceQueryCouponGoodsResponse response=client.execute(request);
//			System.out.println(response.getMsg());
//		} catch (JdException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
//}
