package ISBiglietteria.DAO;

import java.sql.*;

//DBManager è la classe che si occupa di gestire la connessione al database 
//E' un singleton in quanto è stato considerato che solo una connessione alla volta può essere effettuata dall'applicazione
//E in alcune porzioni del programma è necessario avere la possibilità di ottenere rapidamente un riferimento all'istanza
public class DBManager {

	public static DBManager getInstance() {
		if (DBManager.single_instance == null) {
			single_instance = new DBManager();

		}
		return single_instance;
	}

	// Apre la connessione al database con il nome utente e le password specificati
	public void openConnection(String user, String pass) {
		this.user = user;
		this.pass = pass;
		try {
			this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
			System.out.println("Database connection established");
			conn.setAutoCommit(false); // Questa linea è necessaria perchè MySQL di default presenta il commit
										// automatico delle transazioni
			// In questo modo viene garantito che senza un commit esplicito non vengano
			// apportate modifiche al database derivanti da transazioni parziali
			// Potenzialmente invalidando lo stato di alcuni oggetti del database

		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
			this.conn = null;

		}

	}

//funzione di apertura della connessione al DB con
	public void openConnection() {
		try {
			this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
			System.out.println("Database connection established");

		}

		catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
			this.conn = null;

		}

	}

	public Connection getConnection() {
		return this.conn;
	}

///funzione di chiusura della connessione al DB
	public void closeConnection() {
		try {
			System.out.println("esco dal database...");
			conn.close();
			System.out.println("Database connection terminated");
		} catch (SQLException e) {
			/* ignore close errors */ }

	}

	private static DBManager single_instance = null;
	private final String url = "jdbc:mysql://212.237.63.59:3306/is_2020"; // url è final per evitare errori di
																			// connessione dovuti a una possibile
																			// modifica del link
	private String user = "is";
	private String pass = "is";
	private Connection conn = null;

}