package com.jt.common.service;

import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    
    /**
     * url:addUser?id=1&name=tom
     * httpClient get请求方式
     * 参数: 1.String url 2.Map<String,String>,3.字符编码
     * /*	//addUser?id=1&name=tom
    		String paramUrl = url + "?";
    		for (Map.Entry<String,String> entry : params.entrySet()) {
    			String key = entry.getKey();
    			String value = entry.getValue();
    			paramUrl = paramUrl + key + "=" + value + "&";
			}
    		
    		paramUrl = paramUrl.substring(0, paramUrl.length()-1);*/
    public String doGet(String url,Map<String,String> params,String charset){
    	String result = null;
    	
    	//1.判断字符集编码是否为null
    	if(StringUtils.isEmpty(charset)){
    		charset = "UTF-8";
    	}
    	
    	try {
    		//2.判断是否有参数
        	if(params != null){
        		URIBuilder builder = new URIBuilder(url);
        		
        		for (Map.Entry<String,String> entry : params.entrySet()) {
        			
        			builder.addParameter(entry.getKey(), entry.getValue());
				}
        		//利用工具类实现url拼接
        		url = builder.build().toString();
        	}
    		
        	//发起http请求
        	HttpGet httpGet = new HttpGet(url);
    		httpGet.setConfig(requestConfig);
    		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    		
    		if(httpResponse.getStatusLine().getStatusCode() == 200){
    			//获取返回值结果
    			result = EntityUtils.
    					toString(httpResponse.getEntity(),charset);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    //重载方法
    public String doGet(String url){
    	
    	return doGet(url,null,null);
    }
    
    public String doGet(String url,Map<String,String> params){
    	
    	return doGet(url, params, null);
    }
	
	
}
