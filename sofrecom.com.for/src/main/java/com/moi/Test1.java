/**
 * 
 */
package com.moi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sound.midi.SysexMessage;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpHead;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;



import com.moi.dao.AccountManger;
import com.moi.model.Account;
import com.moi.util.RestHClient;

/**
 * @author BEN LAHMAR
 *
 */
public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RestHighLevelClient client=RestHClient.getinstance();
		
		AccountManger am=new AccountManger(client,"bankoperation");
		
		Account ac = am.findById("100");
		System.out.println(ac.getAddress()+"------"+ac.getCompany());
		
		
		Account a1 = am.findByNumber(4);
		System.out.println(a1.getCompany());
		
		List<Account> acs = am.findByage(25);
		acs.stream().forEach(System.out::println);
		
		
		System.out.println(am.findAll().size());
		
		//am.findOperation4Account(4).stream().forEach(System.out::println);
		
		am.findByageAndGendre(25, "female")
		.stream()
		.forEach(System.out::println);
		
		
		
		am.moyByAge();
		Map<String, Long> map = am.bugendre();
		map.entrySet().forEach(  x-> {System.out.println(x.getKey()+"-----"+x.getValue());});
		
		
		am.aaa();
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
