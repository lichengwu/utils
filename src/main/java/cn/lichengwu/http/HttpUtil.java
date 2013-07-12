/*
 * Copyright (c) 2010-2011 lichengwu
 * All rights reserved.
 * 
 */
package cn.lichengwu.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http工具类 基于 apache httpclient
 * 
 * @author lichengwu
 * @created 2012-1-2
 * 
 * @version 1.0
 */
public class HttpUtil {

	/**
	 * POST方法
	 * 
	 * @author lichengwu
	 * @created 2012-1-2
	 * 
	 * @param url
	 * @param params
	 * @throws IOException
	 */
	public static void post(String url, Map<String, String> params)
			throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>(
						params.size());
				for (Entry<String, String> entry : params.entrySet()) {
					postParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				HttpEntity entity = new UrlEncodedFormEntity(postParams);
				httpPost.setEntity(entity);
			}
			httpClient.execute(httpPost);
			httpPost.abort();
		} catch (ClientProtocolException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * POST并返回响应字符串
	 * 
	 * @author lichengwu
	 * @created 2012-1-2
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String obtainStringFromPost(String url,
			Map<String, String> params) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>(
						params.size());
				for (Entry<String, String> entry : params.entrySet()) {
					postParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				HttpEntity entity = new UrlEncodedFormEntity(postParams);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * POST并返回HttpResponse
	 * 
	 * @author lichengwu
	 * @created 2012-1-2
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResponse obtainHttpResponseFromPost(String url,
			Map<String, String> params) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>(
						params.size());
				for (Entry<String, String> entry : params.entrySet()) {
					postParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				HttpEntity entity = new UrlEncodedFormEntity(postParams);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			return response;
		} catch (ClientProtocolException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * GET方法
	 * 
	 * @author lichengwu
	 * @created 2012-1-2
	 *
	 * @param url
	 * @param params
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void get(String url, Map<String, String> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = null;
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>(
						params.size());
				for (Entry<String, String> entry : params.entrySet()) {
					postParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				URI ul = new URI(url);
				URI uri = URIUtils.createURI(ul.getScheme(), ul.getHost(),
						ul.getPort(), ul.getPath(),
						URLEncodedUtils.format(postParams, "UTF-8"), null);
				httpGet = new HttpGet(uri);
			}else{
				httpGet =new HttpGet(url);
			}
			httpClient.execute(httpGet);
			httpGet.abort();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
}
