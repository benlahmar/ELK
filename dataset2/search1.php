<?php

use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';
       
$client = ClientBuilder::create() ->build();

$params=[
    "index"=>"bank2021" 
];

$params["body"] = <<<JSON
{"aggs": {
    "rr": {
      "terms": {
        "field": "operations.type.keyword"
      },"aggs": {
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
JSON;
$res=$client->search($params);
print_r(json_encode($params['body']));
var_dump($res);

?>