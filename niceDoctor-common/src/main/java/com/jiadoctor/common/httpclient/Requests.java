/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author Panda
 * @version 1.0
 */
public class Requests {

	@SuppressWarnings("deprecation")
	public static String doPost(String uri, java.util.Map<String, Object> formMap) {

		String responseBody = null;

		// 获得HttpClient，该Client已经配置默认的响应编码，响应时间等相关配置，如果需要自定义的话，则自行建立httpclient，最后必须关闭httpclient.close()
		CloseableHttpClient httpclient = Client.getCloseableHttpClient();

		try {
			// 创建一个http post的 Method
			HttpPost httpPost = createHttpPost(uri, formMap);

			// 构建响应处理
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 400) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			System.out.println(uri);
			// 执行请求，并等待获取响应
			responseBody = httpclient.execute(httpPost, responseHandler);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// 释放连接管理中无效连接
			httpclient.getConnectionManager().closeExpiredConnections();
		}
		return responseBody;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public static void doPostWithoutResponse(String uri, java.util.Map<String, Object> formMap) {

		CloseableHttpClient httpclient = Client.getCloseableHttpClient();

		try {
			HttpPost httpPost = createHttpPost(uri, formMap);

			CloseableHttpResponse response = httpclient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			response.close();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
			httpclient.getConnectionManager().closeExpiredConnections();
		}
	}

	public static HttpPost createHttpPost(String uri, java.util.Map<String, Object> formMap) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(uri);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		for (String key : formMap.keySet()) {
			String value = String.valueOf(formMap.get(key));
			nvps.add(new BasicNameValuePair(key, value));
		}
		// 设置字符集
		HttpEntity entity = new UrlEncodedFormEntity(nvps, Consts.UTF_8.name());
		httpPost.setEntity(entity);
		RequestConfig requestConfig = RequestConfig.copy(Client.getDefaultRequestConfig()).setSocketTimeout(Client.socketTimeout)
			.setConnectTimeout(Client.connectTimeout).setConnectionRequestTimeout(Client.connectionRequestTimeout).build();
		httpPost.setConfig(requestConfig);
		return httpPost;
	}

	@SuppressWarnings("deprecation")
	public static String doGet(String uri) {
		String responseBody = null;
		CloseableHttpClient httpclient = Client.getCloseableHttpClient();
		try {
			HttpGet httpget = new HttpGet(uri);

			RequestConfig requestConfig = RequestConfig.copy(Client.getDefaultRequestConfig())
				.setSocketTimeout(Client.socketTimeout).setConnectTimeout(Client.connectTimeout)
				.setConnectionRequestTimeout(Client.connectionRequestTimeout).build();

			httpget.setConfig(requestConfig);

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			responseBody = httpclient.execute(httpget, responseHandler);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			httpclient.getConnectionManager().closeExpiredConnections();
		}
		return responseBody;
	}
}
