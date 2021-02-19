<?php

/**
 * BEN LAHMAR EL HABIB
 */
use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';

$client= ClientBuilder::create()->build();


$parms=[
    "index"=>"bank0011",
    "id"=>1,
    "body"=>["age"=>24, "nom"=>"toi", "abc"=>"alpha", 
    "operations"=>[["type"=>"debit","amount"=>"147"],["type"=>"cridit","amount"=>"147"]]]
];
$rep= $client->index($parms);

var_dump($rep);

//var_dump($client);

?>


