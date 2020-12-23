<?php
session_start();
require_once "config.php";
if(!isset($_SESSION["loggedin"]) || !$_SESSION["loggedin"] === true){
    header("location: login.php");
    exit;
}
$error = "";
$id_corsa="";
$id_cliente="";
if($_SERVER["REQUEST_METHOD"] == "POST"){
    //OTTIENI L'ID del cliente
    $id_corsa = $_POST["idCorsa"];
    $sql = "SELECT id FROM clienti where userName = ?";
    if($stmt = $mysqli->prepare($sql)){
        $param_username = $_SESSION["username"];
        $stmt->bind_param("s", $param_username);
        if($stmt->execute()){
            $stmt->store_result();
            $stmt->bind_result($id_cliente);
            if(!$stmt->fetch()){
                exit;
            }
        }
    }
   

    $sql = "SELECT * FROM prenotazioni WHERE idCliente = ? AND idCorsa = ?";
    $stmt->free_result();
    if($stmt = $mysqli->prepare($sql)){
        $param_id_cliente = $id_cliente;
        $param_id_corsa = $id_corsa;
        $stmt->bind_param("ss", $param_id_cliente, $param_id_corsa);

        //Controlla se la prenotazione non è già presente all'interno del database
        if($stmt->execute()){
            $stmt->store_result();
            if($stmt->num_rows > 0){
                $error = "Prenotazione già presente nel sistema per questo utente".$id_cliente."  ".$id_corsa;
            }

            
            //Altrimenti inserisci
            else{
                $sql = "INSERT INTO prenotazioni (idCliente, idCorsa) VALUES (?, ?)";
                $stmt->free_result();
                if($stmt->prepare($sql)){
                    $stmt->bind_param("ss", $param_id_cliente, $param_id_corsa);
                    if(!$stmt->execute()){
                        
                       $error = $stmt->error;
                       exit;
                    }
                   
                }
                $sql = "UPDATE corse SET postiVenduti = postiVenduti + 1 WHERE id = ?";
                $stmt->free_result();
                if($stmt->prepare($sql)){
                    $stmt->bind_param("s", $param_id_corsa);
                    if($stmt->execute()){
                        $error = "Prenotazione avvenuta con successo!";
                        echo '<meta http-equiv="refresh" content="0; url=success.php">';

                    }
                    else{

                        $error = "Impossibile effettuare prenotazione";
                    }
                }

                
            }
            $stmt->close();
        }
    }
    //$mysqli->close();
}

?>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title> Sistema Prenotazioni</title>
</head>

<body>
<div class="container-fluid">
        <div id="sticky-navbar">
            <ul>
                <li><a href="index.html">Home</a></li>
                <li><a href="login.php">Area Clienti</a></li>
            </ul>
        </div>
        
        <table class="table">
  <thead class="thead-dark">
    <tr>
    <th scope="col">Data</th>
      <th scope="col">Orario di Partenza</th>
      <th scope="col">Porto di Partenza</th>
      <th scope="col">Porto di Arrivo</th>
      <th scope="col">Prezzo</th>
      <th scope="col">Acquista</th>
    </tr>
  </thead>
  <tbody>
  <?php

// Initialize the session

 
// Check if the user is already logged in, if yes then redirect him to login page




$sql = "SELECT * FROM tabellario_corse JOIN postiDisponibili WHERE postiDisponibili>0 AND tabellario_corse.codiceCorsa=postiDisponibili.codiceCorsa ORDER BY data DESC;";
if($stmt2 = $mysqli->prepare($sql)){

    if($stmt2->execute()){
        $res = $stmt2->get_result();
        while($row = $res->fetch_assoc()){  
            
            echo '<form action="'.htmlspecialchars($_SERVER["PHP_SELF"]).'" method="post">';
            echo "<tr>";
            echo '<td scope = "row">'.$row["data"].'</td>';
            echo '<td scope = "row">'.$row["oraPartenza"].':00</td>';
            echo '<td scope = "row">'.$row["portoPartenza"].'</td>';
            echo '<td scope = "row">'.$row["portoArrivo"].'</td>';
            echo '<td scope = "row">'.$row["prezzo"].'€</td>';
            echo '<input type = "hidden" name = "idCorsa" value ='.$row["codiceCorsa"].'>';
            echo '<td scope = "row"><input type="submit" class="btn btn-primary" value="Acquista"><i class="fa fa-cart-plus" style = "font-size:30px"></i></td>';
            echo "</tr>";
            echo "</form>";
        }
        
    }
    
}

?>
  </tbody>
</table>
</form>
 <?php 
    //echo $error;
 ?>
    </div>

</body>