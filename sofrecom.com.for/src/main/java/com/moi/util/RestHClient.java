/**
 * 
 */
package com.moi.util;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author BEN LAHMAR
 *
 */
public class RestHClient {

	static RestHighLevelClient client;
	public static RestHighLevelClient getinstance() {
		if(client==null)
		 client=new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost",9200,"http")));
		
		
		return client;
	}
	
	public static RestHighLevelClient getinstance(String host, int port, String protocole) {
		
		 client=new RestHighLevelClient(
				RestClient.builder(
						new HttpHost(host,port,protocole)));
		
		return client;
	}
	public static void close()
	{
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
