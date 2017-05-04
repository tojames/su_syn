package com.sub.syn.youhuiquan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class YouHuiquanJob {

	private static PoolingHttpClientConnectionManager cm;
	private static String EMPTY_STR = "";
	private static String UTF_8 = "UTF-8";
	private String url = "http://api.dataoke.com/index.php?r=Port/index&type=total&appkey=8438shn9i6&v=utf-8&page=[page]";
	private int page = 1;
	
	private PYouHuiQuanService service=new PYouHuiQuanService();

	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(50);// 整个连接池最大连接数
			cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGetRequest(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String httpPostRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

		return getResult(httpPost);
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) {
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			// response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// long len = entity.getContentLength();// -1 表示长度未知
				String result = EntityUtils.toString(entity);
				response.close();
				// httpClient.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return EMPTY_STR;
	}

	private List<YouHuiQuan> jsonToBean(String json) {
		List<YouHuiQuan> list = new ArrayList<YouHuiQuan>();
		com.alibaba.fastjson.JSONObject object = JSON.parseObject(json);
		JSONArray items = object.getJSONArray("result");
		String s = "";
		YouHuiQuan bean = new YouHuiQuan();
		for (int i = 0; i < items.size(); i++) {
			bean = new YouHuiQuan();
			s = items.getString(i);
			JSONObject obj = JSONObject.parseObject(s);
			bean.setId(Integer.parseInt(obj.getString("ID")));
			bean.setAliClick(obj.getString("ali_click"));
			bean.setCid(Integer.parseInt(obj.getString("Cid")));
			bean.setD_title(obj.getString("D_title"));
			bean.setDsr(obj.getString("Dsr"));
			bean.setGoodsId(obj.getLongValue("GoodsID"));
			bean.setIntroduce(obj.getString("Introduce"));
			bean.setIsTmall(obj.getIntValue("IsTmall"));
			bean.setOrgPrice(obj.getDoubleValue("Org_Price"));
			bean.setPic(obj.getString("Pic"));
			bean.setPrice(obj.getDoubleValue("Price"));
			bean.setqQuanMLink(obj.getString("Quan_m_link"));
			bean.setQuanCondition(obj.getString("Quan_condition"));
			bean.setQuanLink(obj.getString("Quan_link"));
			bean.setQuanPrice(obj.getDoubleValue("Quan_price"));
			bean.setSalesNum(obj.getIntValue("Sales_num"));
			bean.setSellerID(obj.getString("SellerID"));
			bean.setTitle(obj.getString("Title"));
			bean.setType(1);
			bean.setQuanSurplus(obj.getIntValue("Quan_surplus"));
			list.add(bean);
		}
		return list;
	}

	public void synJob() {
		String json = httpGetRequest(url.replace("[page]", page + ""));
		List<YouHuiQuan> list = jsonToBean(json);
		try {
			while (list.size()>0) {
				service.deleteByIds(list);
				service.saveBath(list);
				++page;
				System.out.println(list.size() + " 页数：" + page);
				json = httpGetRequest(url.replace("[page]", page + ""));
				try{
					list = jsonToBean(json);
				}catch(com.alibaba.fastjson.JSONException e){
					break;
				}
			}
		} catch (Exception e) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			synJob();
		}
	}

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		YouHuiquanJob b = new YouHuiquanJob();
		b.synJob();
		long end=System.currentTimeMillis();
		System.out.println("耗时："+(end-start)/1000+"秒");
	}
}
