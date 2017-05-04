package com.sub.syn.common;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class MessageTools {

public static String url="http://gw.api.taobao.com/router/rest";
	
	public static String appkey="23765499";
	
	public static String secret="778dcf14c08940a20e473828f2d06be7";
	
	public static Long adzoneId=80460157L;

	public static void main(String[] args) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("券购365");//签名
		req.setSmsParamString("{\"code\":\"金迎春+吴禹璇\"}");
		req.setRecNum("13911624198");
		req.setSmsTemplateCode("SMS_63140066");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());

	}
}
