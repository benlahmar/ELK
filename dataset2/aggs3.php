<?php
/**
 * BEN LAHMAR EL HABIB
 */
use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';
       
$client = ClientBuilder::create() ->build();


$myQuery = [
    'match' => [
    'firstname' => 'habib'
]
];


$params = [
    'index' => 'bankoperation3',
    'body' => [
        'aggs' => [
            'gender' => [
                'composite' => [
                    'sources' => [
                        [
                        "gender"=>[
                            "terms"=>[
                                "field"=>"gender.keyword"
                            ]
                        ]
                        ],
                        [
                        "age"=>[
                            "histogram"=>[
                                "field"=>"age",
                                "interval"=>10
                            ]
                        ]
                        ]
                    ]
                ]
            ]
                 
                ],
                'query' => $myQuery
    ]
];
print_r(json_encode($params));
$response = $client->search($params);
print_r(json_encode($response));

var_dump($response);

?>