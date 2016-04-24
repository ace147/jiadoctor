/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.httpclient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;

/**
 * @author Panda
 * @version 1.0
 */
public class Client {

	public final static int socketTimeout = 20000;
	public final static int connectTimeout = 20000;
	public final static int connectionRequestTimeout = 20000;

	private static String localHost = "localhost";
	private static int maxTotal = 100;
	private static int defaultMaxPerRoute = 100;
	private static int maxPerRoute = 200;
	private static int validateTime = 1000;
	private static int maxHeaderCount = 200;
	private static int maxLineLength = 2000;

	// 链接池配置
	public static PoolingHttpClientConnectionManager connManager;

	// 凭证供应商 ssl
	public static CredentialsProvider credentialsProvider;

	// 请求配置
	public static RequestConfig defaultRequestConfig;

	private static CloseableHttpClient closeableHttpClient;

	public static synchronized CloseableHttpClient getCloseableHttpClient() {
		if (null == closeableHttpClient)
			closeableHttpClient = HttpClients.custom().setConnectionManager(getConnManager())
				.setDefaultCredentialsProvider(getCredentialsProvider()).setDefaultRequestConfig(getDefaultRequestConfig()).build();
		return closeableHttpClient;
	}

	Client() {
		super();
	}

	public Client setConnManager(PoolingHttpClientConnectionManager connManager) {
		Client.connManager = connManager;
		return this;
	}

	public Client setCredentialsProvider(CredentialsProvider credentialsProvider) {
		Client.credentialsProvider = credentialsProvider;
		return this;
	}

	public Client setDefaultRequestConfig(RequestConfig defaultRequestConfig) {
		Client.defaultRequestConfig = defaultRequestConfig;
		return this;
	}

	private static PoolingHttpClientConnectionManager getConnManager() {

		if (null == connManager) {

			HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

				@Override
				public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
					LineParser lineParser = new BasicLineParser() {

						@Override
						public Header parseHeader(final CharArrayBuffer buffer) {
							try {
								return super.parseHeader(buffer);
							} catch (ParseException ex) {
								return new BasicHeader(buffer.toString(), null);
							}
						}

					};
					return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

						@Override
						protected boolean reject(final CharArrayBuffer line, int count) {
							// try to ignore all garbage preceding a status line
							// infinitely
							return false;
						}

					};
				}

			};

			HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

			HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
				requestWriterFactory, responseParserFactory);

			SSLContext sslcontext = SSLContexts.createSystemDefault();

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();

			DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

				@Override
				public InetAddress[] resolve(final String host) throws UnknownHostException {
					if (host.equalsIgnoreCase(localHost)) {
						return new InetAddress[] { InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }) };
					} else {
						return super.resolve(host);
					}
				}

			};

			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);

			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

			connManager.setDefaultSocketConfig(socketConfig);

			// connManager.setSocketConfig(new HttpHost(targetHost, targetPort),
			// socketConfig);

			connManager.setValidateAfterInactivity(validateTime);

			MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(maxHeaderCount)
				.setMaxLineLength(maxLineLength).build();

			ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();

			connManager.setDefaultConnectionConfig(connectionConfig);
			// connManager.setConnectionConfig(new HttpHost(targetHost,
			// targetPort), ConnectionConfig.DEFAULT);
			connManager.setMaxTotal(maxTotal);
			connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
			// connManager.setMaxPerRoute(new HttpRoute(new HttpHost(targetHost,
			// targetPort)), maxPerRoute);

		}
		return connManager;
	}

	private static CredentialsProvider getCredentialsProvider() {
		if (null == credentialsProvider)
			credentialsProvider = new BasicCredentialsProvider();
		return credentialsProvider;
	}

	public static RequestConfig getDefaultRequestConfig() {
		if (null == defaultRequestConfig) {
			defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		}
		return defaultRequestConfig;
	}

	public static String getLocalHost() {
		return localHost;
	}

	public static void setLocalHost(String localHost) {
		Client.localHost = localHost;
	}

	public static int getMaxTotal() {
		return maxTotal;
	}

	public static void setMaxTotal(int maxTotal) {
		Client.maxTotal = maxTotal;
	}

	public static int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public static void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		Client.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public static int getMaxPerRoute() {
		return maxPerRoute;
	}

	public static void setMaxPerRoute(int maxPerRoute) {
		Client.maxPerRoute = maxPerRoute;
	}

	public static int getValidateTime() {
		return validateTime;
	}

	public static void setValidateTime(int validateTime) {
		Client.validateTime = validateTime;
	}

	public static int getMaxHeaderCount() {
		return maxHeaderCount;
	}

	public static void setMaxHeaderCount(int maxHeaderCount) {
		Client.maxHeaderCount = maxHeaderCount;
	}

	public static int getMaxLineLength() {
		return maxLineLength;
	}

	public static void setMaxLineLength(int maxLineLength) {
		Client.maxLineLength = maxLineLength;
	}

}
