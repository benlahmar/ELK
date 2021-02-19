<?php

use Elastica\Aggregation\Terms;
use Elastica\Query;
use Elastica\Aggregation\Histogram;
use Elastica\Aggregation\Avg;
use Elastica\Aggregation\Range;
use Elastica\Aggregation\DateHistogram;
require_once 'vendor/autoload.php';

$client = new \Elastica\Client();

$search = new Elastica\Search($client);

// set up the aggregation
$termsAgg = new Terms("genders");
$termsAgg->setField("gender.keyword");
$termsAgg->setSize(10);

$avg= new Avg("byabc");
$avg->setField("age");

$termsAgg->addAggregation($avg);




$hisoagg = new Histogram("byage","age",10);

$range=new Range("byage2","age");
$range->addRange(10,20);

$datehisto=new DateHistogram("dateby","operations.operationDate","month");

$query = new Query();

$query->addAggregation($termsAgg);
$query->addAggregation($hisoagg);
$query->addAggregation($avg);

$index = $client->getIndex('bank2021');

$res = $index->search($query);
//getAggs

$aggs=$termsAgg->getAggs();
var_dump($aggs);

$bucket1= $res->getAggregation("genders");
$bucket2= $res->getAggregation("byage");
$valavg=$res->getAggregation("byabc");

var_dump($bucket1);
var_dump($bucket2);
var_dump($valavg);

/*
GET bank2021/_search
{
  "aggs": {
    "byage":{
      "histogram": {
        "field": "age",
        "interval": 10
      }
    }
  }
  
}
*/



?>