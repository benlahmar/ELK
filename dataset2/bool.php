<?php

use Elastica\Aggregation\Terms;
use Elastica\Aggregation\Stats;
use Elastica\Query;
use Elastica\Query\BoolQuery;
use Elastica\Query\Term;
use Elastica\Search;

require_once 'vendor/autoload.php';

$client = new \Elastica\Client();
$boolQuery = new BoolQuery();
$termQuery1 = new Term(array('firstname' => 'habib'));
$boolQuery->addMust($termQuery1);
$boolQuery ->addShould(new Term(array('lastname' => 'benlahmar')));

$es_query = new Query($boolQuery);
$q = $es_query->toArray();
echo json_encode($q);
$es_search = new Search($client);
$rs = $es_search->addIndex('bankoperation3')->search($es_query, 10);

print_r($rs);
?>