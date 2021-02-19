<?php


require_once 'vendor/autoload.php';
$client = new \Elastica\Client();


$index = $client->getIndex('bankoperation3');

$query = '{"query":{"query_string":{"query":"habib"}}}';

$path = $index->getName() .  '/_search';

$response = $client->request($path, \Elastica\Request::GET, $query);
$responseArray = $response->getData();

var_dump($responseArray);

?>