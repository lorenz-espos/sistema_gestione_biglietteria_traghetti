<?php
    define('USER', 'is');
    define('PASSWORD', 'is');
    define('HOST', '212.237.63.59');
    define('DATABASE', 'is_2020');
    //error_reporting(E_ALL);
    //ini_set('display_errors', 1);
    // Enter your Host, username, password, database below.
// I left password empty because i do not set password on localhost.
$mysqli = new mysqli(HOST,USER,PASSWORD,DATABASE);

// Check connection
if ($mysqli -> connect_errno) {
  echo "Failed to connect to MySQL: " . $mysqli -> connect_error;
  exit();
}

// Perform query
/*if ($result = $mysqli -> query("SELECT * FROM clienti")) {
  echo "Returned rows are: " . $result -> num_rows."<br>";
  while($row = $result->fetch_assoc()){
      echo "Nome: ".$row["nome"]."<br>";
      echo "Cognome: ".$row["cognome"]."<br>";
  }
  
  // Free result set
  $result -> free_result(); */



?>
