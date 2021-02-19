<?php
use Elastica\Aggregation\Terms;
use Elastica\Aggregation\Stats;
use Elastica\Query;

require_once 'vendor/autoload.php';

$client = new \Elastica\Client();
$search = new Elastica\Search($client);

$termsAgg = new Terms("genders");
$termsAgg->setField("gender.keyword");
$termsAgg->setOrder("height_stats.avg", "desc");

$statsAgg = new Stats("height_stats");
$statsAgg->setField("operations.amount");

$termsAgg->addAggregation($statsAgg);

// add the aggregation to a Query object
$query = new Query();
$query->addAggregation($termsAgg);
$index = $client->getIndex('bankoperation3');
$buckets = $index->search($query)->getAggregation("genders");
var_dump($buckets);
foreach($buckets as $bucket){
   // $statsAggResult = $bucket["height_stats"];
    // do something with the result of the stats agg
    var_dump($bucket[0]);
}