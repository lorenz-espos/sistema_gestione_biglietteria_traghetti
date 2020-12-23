package ISBiglietteria.Control;

import ISBiglietteria.entities.*;
import ISBiglietteria.DAO.*;
import java.sql.Date;

public class SistemaDiAutenticazione {


	public static class InvalidCredentials extends Exception {
	}

	public static SistemaDiAutenticazione getInstance() {
		if (SistemaDiAutenticazione.single_instance == null) {
			SistemaDiAutenticazione.single_instance = new SistemaDiAutenticazione();
		}
		return SistemaDiAutenticazione.single_instance;
	}

	public String login(String username, String password) {
		DBManager dbm = DBManager.getInstance();
        String esito = null;
		//Controllo della correttezza delle credenziali
		if (ImpiegatoDAO.leggiImpiegato(dbm, username, password).getUsername() == null
				|| ImpiegatoDAO.leggiImpiegato(dbm, username, password).getPassword() == null) {
			esito = "Username o password errati!";
			this.check = false;

		} else {
			esito = "Accesso effettuato.";
			this.check = true;
			type = ImpiegatoDAO.leggiTipoImpiegato(dbm, username, password); //permette di distinguere l'impiegato dal dirigente
			if (type == 1) {
				DirigenteSingleton dirigente = DirigenteSingleton.getInstance();

				//Inizializzo i campi del dirigente
				dirigente.init(ImpiegatoDAO.leggiImpiegato(dbm, username, password).getCodiceFiscale(), 
						ImpiegatoDAO.leggiImpiegato(dbm, username, password).getIdImpiegato(), ImpiegatoDAO.leggiImpiegato(dbm, username, password).getNome(), 
						ImpiegatoDAO.leggiImpiegato(dbm, username, password).getCognome(), username, password, ImpiegatoDAO.leggiImpiegato(dbm, username, password).getDataDiNascita());
			} else {
				ImpiegatoSingleton impiegato = ImpiegatoSingleton.getInstance();
				//Inizializzo i campi dell'impiegato
				impiegato.init(ImpiegatoDAO.leggiImpiegato(dbm, username, password).getCodiceFiscale(), 
						ImpiegatoDAO.leggiImpiegato(dbm, username, password).getIdImpiegato(), ImpiegatoDAO.leggiImpiegato(dbm, username, password).getNome(), 
						ImpiegatoDAO.leggiImpiegato(dbm, username, password).getCognome(), username, password, ImpiegatoDAO.leggiImpiegato(dbm, username, password).getDataDiNascita());
			}

		}
		return esito;

	}

	public String proceduraDiRegistrazione(String codiceFiscale, String nome, String cognome, String username,
			String password, Integer tipo,Date data) {
		DBManager dbm = DBManager.getInstance();
		String esito = null;
		//Controllo per verificare se il codice fiscale e il nome utente esistono già all'interno del DB
		if (ImpiegatoDAO.leggiImpiegatoCF(dbm, codiceFiscale).getCodiceFiscale() == null) {
			if (ImpiegatoDAO.leggiImpiegatoUser(dbm, username).getUsername() == null) {
				;
				ImpiegatoSingleton impiegato = ImpiegatoSingleton.getInstance();
				impiegato.init(codiceFiscale, 0, nome, cognome, username, password, data);
				ImpiegatoDAO.creaImpiegato(dbm, impiegato, tipo);
				this.check = true;
				esito = "Username e password disponibili, registrazione effettuata!";
			} else {
				esito = "Username già utilizzato.";
				this.check = false;
			}
		} else {
			esito = "Codice Fiscale già esistente.";
		}
		return esito;
	}
//ritorna la variabile check per controllare se l' utente è loggato
	public boolean isAutenticated() {
		
		return this.check;
	}
//ritorna il tipo del dipendente che ha effettuato il login
	public Integer identificazioneTipo() {
		return this.type;
	}
	//Al logout la connessione viene chiusa
	public void logout() {
		DBManager dbm = DBManager.getInstance();
		dbm.closeConnection();
	}

	private boolean check;
	private Integer type;
	private static SistemaDiAutenticazione single_instance = null;

}
