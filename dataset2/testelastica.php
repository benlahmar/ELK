<?php

require_once 'vendor/autoload.php';

use Elastica\Query\Term;
use Elastica\Query;
use Elastica\Query\Match;
use Elastica\Query\BoolQuery;


$client = new \Elastica\Client();
$search = new Elastica\Search($client);

$search->addIndex('bank2021');

    $bool = new BoolQuery();
    
    $term = new Term(['gender' => 'female']);

    $bool->addMust($term);

    $query = new Query();
    $query->setQuery($bool);

   // $query = new Query($term);
   $q = $query->toArray();
   echo json_encode($q);


    $resultSet = $search->search();
    $totalResults = $resultSet->getTotalHits();
    echo $totalResults;
    $results = $resultSet->getResults();
    foreach ($results as $result) {
        var_dump($result);
    }



?>