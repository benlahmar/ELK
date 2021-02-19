<?php
/**
 * BEN LAHMAR EL HABIB
 */
use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';
       
$client = ClientBuilder::create() ->build();

//Chercher les comptes des personnes femme qui travaillent de preference a casa  OR casablanca
//et ne recuperer que les comptes dans l'age >28
$params =[
    "index"=>"bank2021",
    "body"=>[
        "query"=>[
            "bool"=>[
                "must"=>[
                    [
                    "range"=>[
                        "age"=>
                        [
                            "gt"=>28
                        ]
                             ]
                    ]
                    ,
                    [
                    "term"=>[
                                "gender"=>[
                                    "value"=>"female"
                                    ]
                            ]
                        ]
                                ],
                                "should"=>[
                                    [
                                        "match"=>[
                                            "city"=>"casablanca"
                                        ]
                                    ]
                                ]
                
            ]
        ]
    ]
                        ];

                        print_r(json_encode($params['body']));
//dsl 
$rep= $client->search($params);


print_r($rep["hits"]["total"]);

$hits= $rep["hits"]['hits'];

var_dump($hits[1]["_source"]);


?>