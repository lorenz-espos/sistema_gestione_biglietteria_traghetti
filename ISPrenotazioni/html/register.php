<?php
// Include config file
require_once "config.php";
 
// Define variables and initialize with empty values
$username = $password = $confirm_password = "";
$username_err = $password_err = $confirm_password_err = "";
$nome = $nome_err = "";
$cognome = $cognome_err = "";
$codice_fiscale = $codice_fiscale_err = "";
$data_nascita = $data_nascita_err = "";
// Processing form data when form is submitted
if($_SERVER["REQUEST_METHOD"] == "POST"){
 
    // Validate username
    if(empty(trim($_POST["username"]))){
        $username_err = "Per favore inserisci un username";
    } else{
        // Prepare a select statement
        $sql = "SELECT id FROM clienti WHERE userName = ?";
        
        if($stmt = $mysqli->prepare($sql)){
            // Bind variables to the prepared statement as parameters
            $stmt->bind_param("s", $param_username);
            
            // Set parameters
            $param_username = trim($_POST["username"]);
            
            // Attempt to execute the prepared statement
            if($stmt->execute()){
                // store result
                $stmt->store_result();
                
                if($stmt->num_rows == 1){
                    $username_err = "Questo username non è disponibile";
                } else{
                    $username = trim($_POST["username"]);
                }
            } else{
                echo "Qualcosa è andato storto. Riprova più tardi";
            }

            // Close statement
            $stmt->close();
        }
    }

    if(empty(trim($_POST["codice_fiscale"]))){
        $codice_fiscale_err = "Per favore inserisci un codice fiscale";
    } else{
        // Prepare a select statement
        $sql = "SELECT id FROM clienti WHERE codiceFiscale = ?";
        
        if($stmt = $mysqli->prepare($sql)){
            // Bind variables to the prepared statement as parameters
            $stmt->bind_param("s", $param_codice_fiscale);
            
            // Set parameters
            $param_codice_fiscale = trim($_POST["codice_fiscale"]);
            
            // Attempt to execute the prepared statement
            if($stmt->execute()){
                // store result
                $stmt->store_result();
                
                if($stmt->num_rows == 1){
                    $codice_fiscale_err = "Questo Codice Fiscale è stato già utilizzato";
                } else{
                    $codice_fiscale = trim($_POST["username"]);
                }
            } else{
                echo "Qualcosa è andato storto. Riprova più tardi";
            }

            // Close statement
            $stmt->close();
        }
    }

    //VALIDA NOME
    if(empty(trim($_POST["nome"]))){
        $nome_err = "Per favore inserisci un nome";
    } else {
        $nome = trim($_POST["nome"]);
    }

    //VALIDA COGNOME
    if(empty(trim($_POST["cognome"]))){
        $cognome_err = "Per favore inserisci un cognome";
    } else {
        $cognome = trim($_POST["cognome"]);
    }

    //VALIDA DATA DI NASCITA
    if(!strtotime($_POST['data_nascita'])){
        $data_nascita_err = "Per favore inserisci una data di nascita";
    } else {
        $data_nascita = date('Y-m-d', strtotime($_POST['data_nascita']));
    }

    // Validate password
    if(empty(trim($_POST["password"]))){
        $password_err = "Per favore inserisci una password";     
    } elseif(strlen(trim($_POST["password"])) < 6){
        $password_err = "Le password devono avere almeno 6 caratteri";
    } else{
        $password = trim($_POST["password"]);
    }
    
    // Validate confirm password
    if(empty(trim($_POST["confirm_password"]))){
        $confirm_password_err = "Conferma la tua password";     
    } else{
        $confirm_password = trim($_POST["confirm_password"]);
        if(empty($password_err) && ($password != $confirm_password)){
            $confirm_password_err = "Le password non corrispondono";
        }
    }
    
    // Check input errors before inserting in database
    if(empty($username_err) && empty($password_err) && empty($confirm_password_err)){
        
        // Prepare an insert statement
        $sql = "INSERT INTO clienti (userName, userPass, codiceFiscale, nome, cognome, dataDiNascita) VALUES (?, ?, ?, ?, ?, date(?))";
         
        if($stmt = $mysqli->prepare($sql)){
            // Bind variables to the prepared statement as parameters
            $stmt->bind_param("ssssss", $param_username, $param_password, $param_codice_fiscale, $param_nome, $param_cognome, $param_data_nascita);
            
            // Set parameters
            $param_username = $username;
            $param_password = password_hash($password, PASSWORD_DEFAULT); // Creates a password hash
            $param_codice_fiscale = $codice_fiscale;
            $param_nome = $nome;
            $param_cognome = $cognome;
            $param_data_nascita = $data_nascita;
            
            // Attempt to execute the prepared statement
            if($stmt->execute()){
                // Redirect to login page
                header("location: login.php");
            } else{
                $confirm_password_err = $stmt->error;
            }

            // Close statement
            $stmt->close();
        }
    }
    
    // Close connection
    $mysqli->close();
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

        <div class="register-wrapper">
            <h2>Registrati</h2>
            <p>Completa i campi per registrare un nuovo account.</p>
            <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
                <div class="form-group <?php echo (!empty($username_err)) ? 'has-error' : ''; ?>">
                    <label>Username</label>
                    <input type="text" name="username" class="form-control" value="<?php echo $username; ?>">
                    <span class="help-block"><?php echo $username_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($password_err)) ? 'has-error' : ''; ?>">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" value="<?php echo $password; ?>">
                    <span class="help-block"><?php echo $password_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($confirm_password_err)) ? 'has-error' : ''; ?>">
                    <label>Confirm Password</label>
                    <input type="password" name="confirm_password" class="form-control"
                        value="<?php echo $confirm_password; ?>">
                    <span class="help-block"><?php echo $confirm_password_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($nome_err)) ? 'has-error' : ''; ?>">
                    <label>Nome</label>
                    <input type="text" name="nome" class="form-control" value="<?php echo $nome; ?>">
                    <span class="help-block"><?php echo $nome_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($cognome_err)) ? 'has-error' : ''; ?>">
                    <label>Cognome</label>
                    <input type="text" name="cognome" class="form-control" value="<?php echo $cognome; ?>">
                    <span class="help-block"><?php echo $cognome_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($codice_fiscale_err)) ? 'has-error' : ''; ?>">
                    <label>Codice Fiscale</label>
                    <input type="text" name="codice_fiscale" class="form-control"
                        value="<?php echo $codice_fiscale; ?>">
                    <span class="help-block"><?php echo $codice_fiscale_err; ?></span>
                </div>
                <div class="form-group <?php echo (!empty($data_nascita_err)) ? 'has-error' : ''; ?>">
                    <label>Data di Nascita</label>
                    <input type="date" name="data_nascita" class="form-control">
                    <span class="help-block"><?php echo $data_nascita_err; ?></span>
                </div>
                <div class="form-group">
                    <input type="submit" class="btn btn-primary" value="Submit">
                    <input type="reset" class="btn btn-default" value="Reset">
                </div>
                
                <p>Hai già un account? <a href="login.php">Accedi qui</a>.</p>
                
            </form>

        </div>

    </div>


</body>