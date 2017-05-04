package com.sub.syn.youhuiquan;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(
					"http://api.dataoke.com/index.php?r=Port/index&type=total&appkey=8438shn9i6&v=utf-8&page=1");
			// Create a custom response handler
			ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
				// 成功调用连接后，对返回数据进行的操作
				public JSONObject handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						// 获得调用成功后 返回的数据
						HttpEntity entity = response.getEntity();
						if (null != entity) {
							String result = EntityUtils.toString(entity);
							// 根据字符串生成JSON对象
							JSONObject resultObj = JSONObject.parseObject(result);
							return resultObj;
						} else {
							return null;
						}
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			// 返回的json对象
			JSONObject responseBody = httpclient.execute(httpPost, responseHandler);
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
