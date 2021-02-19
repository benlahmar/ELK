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

/*
"aggs": {
    "rr": {
      "terms": {
        "field": "operations.type.keyword"
      },
      "aggs": {
        "a": {
          "value_count": {
            "field": "account_number"
          }
        }, 
        "b":{
          "sum": {
            "field": "operations.amount"
          }
        },
        "c":{
          "bucket_script": {
            "buckets_path": {"x":"a", "y":"b"},
            "script": "params.x / params.y"
          }
        },
        "d":{
          "bucket_script": {
            "buckets_path": {"z":"c", "a":"b", "b":"a"},
            "script": "params.a/(params.z+params.b)"
          }
        }
      }
    }
  }
}

*/
$params = [
    'index' => 'bankoperation3',
    'body' => [
        'aggs' => [
            'By_gender' => [
                'terms' => [
                    'field' => 'gender.keyword'
                ]
            ],
            'By_date' => [
                'date_histogram' => [
                    'field' => 'operations.operationDate',
                    "interval"=> "month"
                    
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