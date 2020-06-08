package com.gofirst.utils;

	import com.alibaba.fastjson.JSON;
	import com.alibaba.fastjson.TypeReference;
//	import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
	import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
	import org.apache.http.client.config.RequestConfig;
	import org.apache.http.client.entity.UrlEncodedFormEntity;
	import org.apache.http.client.methods.CloseableHttpResponse;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.client.methods.HttpRequestBase;
	import org.apache.http.entity.StringEntity;
	import org.apache.http.impl.client.CloseableHttpClient;
	import org.apache.http.impl.client.HttpClientBuilder;
	import org.apache.http.impl.client.HttpClients;
	import org.apache.http.impl.client.LaxRedirectStrategy;
	import org.apache.http.util.EntityUtils;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.util.List;

	/**
	 * Created by Abell_Xy on 2020/5/11 3:15 下午
	 */
	@Slf4j
	public class HttpUtils {

	    private static int TIME_OUT = 10000;//默认10秒超时

	    private HttpUtils() {
	        //工具类无需对象实例化
	    }

	    private static boolean setProxy(String proxyHost, Integer proxyPort, HttpRequestBase httpRequestBase) {
	        return setProxy(proxyHost, proxyPort, httpRequestBase, TIME_OUT);
	    }

	    private static boolean setProxy(String proxyHost, Integer proxyPort, HttpRequestBase httpRequestBase, int timeout) {
	        boolean isViaProxy = false;
	        if (!StringUtils.isEmpty(proxyHost) && proxyHost != null) {
	            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
	            RequestConfig config = RequestConfig.custom()
	                    .setProxy(proxy)
	                    .setConnectTimeout(timeout)
	                    .setSocketTimeout(timeout)
	                    .setConnectionRequestTimeout(timeout)
	                    .build();
	            httpRequestBase.setConfig(config);
	            isViaProxy = true;
	        } else {
	            RequestConfig config = RequestConfig.custom()
	                    .setConnectTimeout(timeout)
	                    .setSocketTimeout(timeout)
	                    .setConnectionRequestTimeout(timeout)
	                    .build();
	            httpRequestBase.setConfig(config);
	        }
	        return isViaProxy;
	    }

	    public static String get(String url, String encoding, String proxyHost, Integer proxyPort) {
	        return get(url, encoding, proxyHost, proxyPort, TIME_OUT);
	    }

	    public static String get(String url, String encoding, String proxyHost, Integer proxyPort, int timeoutMiniSeconds) {
	        String response = null;
	        CloseableHttpClient client = HttpClients.createDefault();
	        HttpGet httpGet = new HttpGet(url);
	        boolean isViaProxy = setProxy(proxyHost, proxyPort, httpGet, timeoutMiniSeconds);
	        try {
	            CloseableHttpResponse httpResponse = client.execute(httpGet);
	            try {
	                log.debug(httpResponse.getStatusLine() + " , " + url + (isViaProxy ? " , via:" + proxyHost + ":" + proxyPort : ""));
	                response = EntityUtils.toString(httpResponse.getEntity(), encoding);
	                log.debug(response);
	            } finally {
	                httpResponse.close();
	            }
	        } catch (Exception e) {
	            exceptionHandle(e, url, timeoutMiniSeconds);
	        } finally {
	            try {
	                client.close();
	            } catch (Exception e) {
	                exceptionHandle(e, url, timeoutMiniSeconds);
	            }
	        }
	        return response;
	    }

	    /**
	     * 以get方式请求指定url,并返回headers数组
	     *
	     * @param url
	     * @param proxyHost
	     * @param proxyPort
	     * @return
	     */
	    public static Header[] get(String url, String proxyHost, Integer proxyPort) {
	        CloseableHttpClient client = HttpClients.createDefault();
	        HttpGet httpGet = new HttpGet(url);
	        setProxy(proxyHost, proxyPort, httpGet);
	        try {
	            CloseableHttpResponse httpResponse = client.execute(httpGet);

	            try {
	                Header[] headers = httpResponse.getAllHeaders();
	                return headers;
	            } finally {
	                httpResponse.close();
	            }
	        } catch (Exception e) {
	            exceptionHandle(e, url, TIME_OUT);
	        } finally {
	            try {
	                client.close();
	            } catch (Exception e) {
	                exceptionHandle(e, url, TIME_OUT);
	            }
	        }
	        return new Header[0];
	    }

	    /**
	     * 以get方式请求指定url,并返回headers数组
	     *
	     * @param url
	     * @return
	     */
	    public static Header[] getHeaders(String url) {
	        return get(url, null, null);
	    }

	    public static String get(String url) {
	        return get(url, "utf-8", null, null);
	    }

	    public static String get(String url, int timeoutMiniSeconds) {
	        return get(url, "utf-8", null, null, timeoutMiniSeconds);
	    }

	    public static String get(String url, String encoding) {
	        return get(url, encoding, null, null);
	    }

	    public static String post(String url, String postData, String mediaType, String encoding,
	                              Header[] headers, String proxyHost, Integer proxyPort) {
	        return post(url, postData, mediaType, encoding,
	                headers, proxyHost, proxyPort, TIME_OUT);

	    }

	    public static String post(String url, String postData, String mediaType, String encoding,
	                              Header[] headers, String proxyHost, Integer proxyPort, int timeoutMiniSeconds) {
	        String response = null;
	        CloseableHttpClient client = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(url);
	        boolean isViaProxy = setProxy(proxyHost, proxyPort, httpPost, timeoutMiniSeconds);
	        try {
	            StringEntity entity = new StringEntity(postData, encoding);
	            entity.setContentType(mediaType);
	            httpPost.setEntity(entity);
	            if (headers != null && headers.length > 0) {
	                httpPost.setHeaders(headers);
	            }
	            CloseableHttpResponse httpResponse = client.execute(httpPost);
	            try {
	                log.debug(httpResponse.getStatusLine().getStatusCode() + " " + url + (isViaProxy ? " via:" + proxyHost + ":" + proxyPort : ""));
	                byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
	                response = new String(bytes, encoding);
	                log.debug(response);
	            } finally {
	                httpResponse.close();
	            }
	        } catch (Exception e) {
	            exceptionHandle(e, url, postData, timeoutMiniSeconds);
	        } finally {
	            try {
	                client.close();
	            } catch (Exception e) {
	                exceptionHandle(e, url, postData, timeoutMiniSeconds);
	            }
	        }
	        return response;

	    }

	    public static String post(String url) {
	        return post(url, "", "application/json");
	    }

	    public static String post(String url, String postData, int timeoutMiniSeconds) {
	        return post(url, postData, "application/json", "utf-8", null, null, null, timeoutMiniSeconds);
	    }

	    public static String post(String url, String postData, String mediaType) {
	        return post(url, postData, mediaType, "utf-8", null, null, null);
	    }

	    public static String post(String url, String postData, Header[] header, String mediaType) {
	        return post(url, postData, mediaType, "utf-8", header, null, null);
	    }

	    public static String post(String url, String postData, Header[] header, String mediaType, int timeoutMiniSeconds) {
	        return post(url, postData, mediaType, "utf-8", header, null, null, timeoutMiniSeconds);
	    }

	    private static void exceptionHandle(Exception e, String url, int timeOut) {
	        e.printStackTrace();
	        throw new RuntimeException("调用服务失败，服务地址：" + url + "，超时时间：" + timeOut + "，异常类型："
	                + e.getClass() + "，错误原因：" + e.getMessage());
	    }

	    private static void exceptionHandle(Exception e, String url, String postData, int timeOut) {
	        e.printStackTrace();
	        throw new RuntimeException("调用服务失败，服务地址：" + url + "，请求参数：" + postData + "，超时时间：" + timeOut + "，异常类型："
	                + e.getClass() + "，错误原因：" + e.getMessage());
	    }

	    private static void exceptionHandle(Exception e, String url, List<NameValuePair> pairs, int timeOut) {
	        e.printStackTrace();
	        throw new RuntimeException("调用服务失败，服务地址：" + url + "，请求参数：" + JSON.toJSON(pairs) + "，超时时间：" + timeOut + ",异常类型："
	                + e.getClass() + "，错误原因：" + e.getMessage());
	    }

	    /**
	     * 模拟form提交
	     *
	     * @param url
	     * @param urlParameters
	     * @return
	     * @throws IOException
	     */
	    public static String doFormPost(String url, List<NameValuePair> urlParameters, int timeout) throws IOException {
	        CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpPost post = new HttpPost(url);
	        RequestConfig config = RequestConfig.custom()
	                .setConnectTimeout(timeout)
	                .setSocketTimeout(timeout)
	                .setConnectionRequestTimeout(timeout)
	                .build();
	        post.setConfig(config);
	        post.setEntity(new UrlEncodedFormEntity(urlParameters));
	        StringBuffer result = new StringBuffer();
	        try {
	            HttpResponse response = client.execute(post);
	            System.out.println("Response Code : "
	                    + response.getStatusLine().getStatusCode());
	            BufferedReader rd = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent()));

	            String line;
	            while ((line = rd.readLine()) != null) {
	                result.append(line);
	            }
	        } catch (Exception e) {
	            exceptionHandle(e, url, urlParameters, timeout);
	        } finally {
	            if (client != null) {
	                try {
	                    client.close();
	                } catch (IOException e) {
	                    log.error("client close with error", e);
	                }
	            }
	        }
	        return result.toString();
	    }

	    public static String doFormPost(String url, List<NameValuePair> urlParameters) throws IOException {
	        return doFormPost(url, urlParameters, TIME_OUT);
	    }

	    public static String doFormPost(String url, List<NameValuePair> params, String mediaType, Header[] headers) throws IOException {
	        return doFormPost(url, params, mediaType, "utf-8", headers);
	    }

	    public static String doFormPost(String url, List<NameValuePair> params, String mediaType, String encoding, Header[] headers) throws IOException {
	        return doFormPost(url, params, mediaType, encoding, headers, TIME_OUT);
	    }

	    public static String doFormPost(String url, List<NameValuePair> params, String mediaType, String encoding,
	                                    Header[] headers, int timeout) throws IOException {
	        CloseableHttpClient client = null;
	        String result = "";
	        try {
	            client = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
	            RequestConfig config = RequestConfig.custom()
	                    .setConnectTimeout(timeout)
	                    .setSocketTimeout(timeout)
	                    .setConnectionRequestTimeout(timeout)
	                    .build();
	            HttpPost post = new HttpPost(url);
	            post.setConfig(config);
	            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, encoding);
	            formEntity.setContentType(mediaType);
	            post.setEntity(formEntity);
	            if (headers != null && headers.length > 0) {
	                post.setHeaders(headers);
	            }
	            CloseableHttpResponse resp = client.execute(post);
	            try {
	                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result = EntityUtils.toString(resp.getEntity(), encoding);
	                } else {
	                    throw new RuntimeException("from remote server receive status is " + resp.getStatusLine().getStatusCode() + " , url=>" + url + ",params=>" + JSON.toJSON(params));
	                }
	            } finally {
	                resp.close();
	            }
	        } catch (Exception e) {
	            exceptionHandle(e, url, timeout);
	        } finally {
	            if (client != null) {
	                try {
	                    client.close();
	                } catch (IOException e) {
	                    log.error("client close with error", e);
	                }
	            }

	        }
	        return result;
	    }


//	    public static void main(String[] args) {
//	        String result = "[{\n" +
//	                " \"AO_NUMBER\": \"140A103LJ0110\",\n" +
//	                " \"PRE_AO_START_TIME\": \"-\",\n" +
//	                " \"PRE_AO_DOC_TIME\": \"-\",\n" +
//	                " \"AO_START_TIME\": \"2019-10-05 09:24:17\",\n" +
//	                " \"AO_DOC_TIME\": \"2019-11-28 13:52:12\",\n" +
//	                " \"AIRPLANE_NUMBER\": \"134\",\n" +
//	                " \"PRE_AO_NUMBER\": null,\n" +
//	                " \"PRE_AO_STATUS\": null,\n" +
//	                " \"AO_STATUS\": \"已归档\"\n" +
//	                "}]";
//
//	        List<SyncPlanInfoCpiResult> cpiResult = null;
//	        try {
//	            cpiResult = JSON.parseObject(result, new TypeReference<List<SyncPlanInfoCpiResult>>() {
//	            });
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            log.error("planinfocpi 序列化出错：" + JSON.toJSON(e.getStackTrace()));
//	        }
//	        System.out.println(JSON.toJSON(cpiResult));
//	    }
	}

