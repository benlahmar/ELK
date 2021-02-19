<?php

use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';
       
$client = ClientBuilder::create() ->build();


//GET bank2021/_doc/tPt8r3cBs9NT-ysOhhWo

$params=[
    "index"=>"bank2021",
    "id"=>"tPt8r3cBs9NT-ysOhhWo",
    "client" => [
        "future"=>'lazy'
    ]
];

$res=$client->get($params);
$r=$res['_source'];
var_dump($r);

?>