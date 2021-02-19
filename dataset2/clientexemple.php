
<?php

use Elasticsearch\ClientBuilder;
require 'vendor/autoload.php';
       
$client = ClientBuilder::create() ->build();

var_dump($client);


?>