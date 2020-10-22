/**
 * 
 */
package com.moi.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.moi.model.Account;
import com.moi.model.Operation;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author BEN LAHMAR
 *
 */
public class AccountManger implements IDao {

	RestHighLevelClient client;
	String index;
	private ObjectMapper mapper;
	
	/**
	 * @param client
	 * @param index
	 */
	public AccountManger(RestHighLevelClient client, String index) {
		super();
		this.client = client;
		this.index = index;
		mapper=new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); 
	}

	
	public Account findById(String id) {					
		GetRequest req=new GetRequest(index, id);//creation requet
		GetResponse rep;
		Account ac = null;
		try {
			rep = client.get(req, RequestOptions.DEFAULT);//execution
			
			ac = mapper.readValue(rep.getSourceAsString(), Account.class);//conversion
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ac;
	}

	
	public Account findByNumber(int nb) {
		
		SearchRequest req=new SearchRequest(index);
		
		SearchSourceBuilder sb=new SearchSourceBuilder();
		sb.query(QueryBuilders.termQuery("account_number", nb));
		req.source(sb);
		SearchResponse rep;
		
		Account ac = null ;
		try {
			 rep = client.search(req, RequestOptions.DEFAULT);
			 SearchHit[] data = rep.getHits().getHits();
			 SearchHit hit = rep.getHits().getAt(0);
			  ac = mapper.readValue(hit.getSourceAsString(), Account.class);
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  ac ;
	}


	public List<Account> findByage(int age) {
		List<Account> acs=new ArrayList<Account>();
		
		SearchRequest req=new SearchRequest(index);
		
		SearchSourceBuilder sb=new SearchSourceBuilder();
		sb.query(QueryBuilders.matchQuery("age", age));
		req.source(sb);
		
		SearchResponse rep;
		
		try {
			rep=client.search(req, RequestOptions.DEFAULT);
			SearchHit[] hits = rep.getHits().getHits();//recuperer tous les hits
			for (SearchHit hit : hits) {
				Account c = mapper.readValue(hit.getSourceAsString(), Account.class);//converssion
				acs.add(c);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return acs;
	}


	@Override
	public List<Account> findAll() {
		List<Account> acs=new ArrayList<Account>();
		
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();
		sb.query(QueryBuilders.matchAllQuery());
		req.source(sb);
		
		SearchResponse rep;
		try {
			rep=client.search(req, RequestOptions.DEFAULT);
			SearchHit[] hits = rep.getHits().getHits();//recuperer tous les hits
			for (SearchHit hit : hits) {
				Account c = mapper.readValue(hit.getSourceAsString(), Account.class);//converssion
				acs.add(c);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return acs;
	}


	
	
	@Override
	public List<Operation> findOperation4Account(int num) {
		SearchRequest req=new SearchRequest(index);//GET /bankoperation2/_search
		
		SearchSourceBuilder sb=new SearchSourceBuilder();
		
		QueryBuilder query=QueryBuilders.termQuery("operations.type", "debit");
		//QueryBuilders.nestedQuery("operations", query, ScoreMode.None);
		
		
		NestedQueryBuilder nqb=new NestedQueryBuilder("operations", query, ScoreMode.None);
		
		sb.query(nqb);
		String includes="operations";
		sb.fetchSource(includes, null);
		req.source(sb);
		
		SearchResponse rep;
		
		List<Operation> ops=new ArrayList<Operation>();
		
		try {
			 rep = client.search(req, RequestOptions.DEFAULT);
			 System.out.println(rep);
			 
			  SearchHit data = rep.getHits().getAt(0);
			  System.out.println(data);
			    Operation x = mapper.readValue(data.getSourceAsString(), Operation.class) ;
			//System.out.println(x.);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  ops ;
	}


	@Override
	public List<Account> findByageAndGendre(int age, String gendre) {
		SearchRequest req=new SearchRequest(index);//GET /bankoperation2/_search
		
		SearchSourceBuilder sb=new SearchSourceBuilder();//constructeur de requetes
		
		BoolQueryBuilder bool = QueryBuilders.boolQuery();
		bool.must(QueryBuilders.matchQuery("age", age));
		
		bool.should(QueryBuilders.termQuery("gendre", gendre));
		
		sb.query(bool);
		req.source(sb);
		
		List<Account> cs=new ArrayList<Account>();
		//execution
		SearchResponse rep ;
		
		try {
			rep= client.search(req, RequestOptions.DEFAULT);
			SearchHit[] v = rep.getHits().getHits();
			for (SearchHit h : v) {
				cs.add(mapper.readValue(h.getSourceAsString(), Account.class));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cs;
	}


	@Override
	public List<Account> findbetweendate(LocalDate dd, LocalDate df) {
		
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();
		sb.query(QueryBuilders.rangeQuery("operations.operationDate").gte("2015-11-04").lte("2015-11-04"));
		
		req.source(sb);
		
		//ex
		SearchResponse rep ;
		List<Account> cs=new ArrayList<Account>();
		try {
			rep= client.search(req, RequestOptions.DEFAULT);
			SearchHit[] v = rep.getHits().getHits();
			for (SearchHit h : v) {
				cs.add(mapper.readValue(h.getSourceAsString(), Account.class));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cs;
	}
	

	
	public List<Operation> historyBetwenDate(LocalDate dd, LocalDate df) {
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();
		
		BoolQueryBuilder boolqb=new BoolQueryBuilder();
		
		boolqb.must(
				     QueryBuilders.rangeQuery(
				    		 "operations.operationDate").lte(df).gte(dd));
		
		NestedQueryBuilder nqb=new NestedQueryBuilder("operations", boolqb, ScoreMode.None);
		String include="operations";
		String exclude="";
		sb.fetchSource(include, exclude);
		sb.query(nqb);
		req.source(sb);
		SearchResponse rep;
		List<Operation> ops=new ArrayList<>();
		try {
			rep = client.search(req, RequestOptions.DEFAULT);
			SearchHit[] hits = rep.getHits().getHits();
			for (SearchHit hit : hits) {
				System.out.println(hit);
				ops.add(mapper.readValue(hit.getSourceAsString(), Operation.class));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ops;
	}


	
	
	
	@Override
	public double moyByAge() {
		
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();		
		AvgAggregationBuilder avg = AggregationBuilders.avg("byavg").field("age");	
		sb.aggregation(avg);
		
		req.source(sb);		
		double x=0;
		 SearchResponse resp ;
		 try {
			resp= client.search(req, RequestOptions.DEFAULT);
			Avg r = resp.getAggregations().get("byavg");
			 
			System.out.println(r.getValue());
			x=r.getValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return x;
	}


	
	public Map<String, Long> bugendre() {
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();	
		TermsAggregationBuilder term=AggregationBuilders.terms("bygendre").field("gender.keyword");
			term.subAggregation(AggregationBuilders.avg("byage").field("age"));
		  sb.aggregation(term);
		  
		  req.source(sb);
		  Map<String, Long> map=new HashMap<>();
		  SearchResponse resp ;
		  try {
			
			  resp= client.search(req, RequestOptions.DEFAULT);
			  Terms x = resp.getAggregations().get("bygendre");
			
			  x.getBuckets().stream()
			  .forEach(a -> {map.put( a. getKeyAsString(), a.getDocCount()); });
			  
			 Bucket b = x.getBucketByKey("female");
			 
			
			Avg vv= b.getAggregations().get("byage");
			System.out.println("******"+vv.value());
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	
	public void aaa()
	{
		SearchRequest req=new SearchRequest(index);
		SearchSourceBuilder sb=new SearchSourceBuilder();	
		TermsAggregationBuilder term=AggregationBuilders.terms("bygendre").field("gender.keyword");
			term.subAggregation(AggregationBuilders.topHits("byhit").size(10));
		  sb.aggregation(term);
		  
		  req.source(sb);
		  Map<String, Long> map=new HashMap<>();
		  SearchResponse resp ;
		  try {
			
			  resp= client.search(req, RequestOptions.DEFAULT);
			  Terms x = resp.getAggregations().get("bygendre");
			
			  x.getBuckets().stream()
			  .forEach(a -> {map.put( a. getKeyAsString(), a.getDocCount()); });
			  
			 Bucket b = x.getBucketByKey("female");
			 
			TopHits ccc= b.getAggregations().get("byhit");
			SearchHit[] hits = ccc.getHits().getHits();
			for (SearchHit hit : hits) {
				System.out.println(hit.getSourceAsString());
			}
			Avg vv= b.getAggregations().get("byage");
			System.out.println("******"+vv.value());
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
