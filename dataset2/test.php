<?php

require_once 'vendor/autoload.php';

$client = new \Elastica\Client();

$search = new Elastica\Search($client);

$search
    ->addIndex('bankoperation3');

$term = new \Elastica\Query\Term(['firstname' => 'habib']);

$query = new \Elastica\Query();

$query->setQuery($term);

$search->setQuery($query);
$resultSet = $search->search();
$x=$search->count();

$results = $resultSet->getResults();
$totalResults = $resultSet->getTotalHits();
echo $totalResults;

foreach ($results as $result) {
   var_dump($result);
}

