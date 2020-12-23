package ISBiglietteria.Boundary;

import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ISBiglietteria.DAO.DBManager;
import ISBiglietteria.entities.DirigenteSingleton;
import ISBiglietteria.entities.ImpiegatoSingleton;
import ISBiglietteria.Control.*;

public final class ApplicationConsoleBoundary {

	private ApplicationConsoleBoundary() {
	}

	public static void start() {
		System.out.println("********** WELCOME **********");
		Scanner in = new Scanner(System.in);
		int opzione = 0;
		DBManager dbm = DBManager.getInstance();
		dbm.openConnection("is", "is");
		try {
			System.out.print("1. Login\n2. Registrazione\n3. Esci dal sistema.\n: ");
			opzione = in.nextInt();
			switch(opzione) {
			case 1:
				doLogin();
				break;
			case 2:
				registrazione();
				break;
			case 3:
				doLogout();
				break;
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.err.println("Valore non valido.");
		}
		in.close();

	}
	//Boundary per l'accesso dell'impiegato al sistema
	private static void doLogin() throws IOException {
		Scanner in = new Scanner(System.in);
		String esito = null; //Stringa per l'esito della procedura di accesso
		SistemaDiAutenticazione sistemaAutenticazione = SistemaDiAutenticazione.getInstance();
		do {
			System.out.println("********** LOGIN **********");
			System.out.print("Username: ");
			String username = in.nextLine();
			System.out.print("Password: ");
			String password = in.nextLine();
			try {
				esito = sistemaAutenticazione.login(username, password);
				System.out.println(esito);
				if (!sistemaAutenticazione.isAutenticated()) {
					throw new SistemaDiAutenticazione.InvalidCredentials();
				}
				if (sistemaAutenticazione.identificazioneTipo() == 0) {
					ImpiegatoConsoleBoundary.dipendenteBoundary();
				} else {
					DirigenteConsoleBoundary.dipendenteBoundary();
				}
			} catch (SistemaDiAutenticazione.InvalidCredentials e) {
				System.out.println("Invalid Credentials! Retry.");
				// TODO: handle exception
			}

		} while (sistemaAutenticazione.isAutenticated() == false);
		in.close();
	}
	
    //Boundary per la registrazione di un nuovo impiegato
	private static void registrazione() throws IOException {
		SistemaDiAutenticazione sistemaAutenticazione = SistemaDiAutenticazione.getInstance();
		String esito = null; //Stringa utilizzata per ottenere l'esito dell'operazione
		Scanner in = new Scanner(System.in);
		System.out.println("********** REGISTRAZIONE **********");
		System.out.print("Inserisci Codice Fiscale: ");
		String codiceFiscale = in.nextLine();
		System.out.print("Inserisci il tuo nome: ");
		String nome = in.nextLine();
		System.out.print("Inserisci il tuo cognome: ");
		String cognome = in.nextLine();
		System.out.print("Inserisci il tuo user: ");
		String username = in.nextLine();
		System.out.print("Inserisci la tua password: ");
		String password = in.nextLine();
		System.out.print("Inserisci la data di nascita [dd-MM-yyyy]:");
		String datas = in.nextLine();
		try {
			//cast effettuato per poter inserire la data nel DB
			java.util.Date dataJava = new SimpleDateFormat("dd-MM-yyyy").parse(datas);
			Date dataSQL = new Date(dataJava.getTime());

			Integer tipo = 0;

			System.out.print("Sei un dirigente? [y/n]\n: ");
			if (in.nextLine().equals("y"))
				tipo = 1;
			try {
				esito = sistemaAutenticazione.proceduraDiRegistrazione(codiceFiscale, nome, cognome, username, password, tipo,
						dataSQL);
						System.out.println(esito); //Stampa l'esito della procedura
				if (!sistemaAutenticazione.isAutenticated())
					throw new SistemaDiAutenticazione.InvalidCredentials();
			} catch (SistemaDiAutenticazione.InvalidCredentials e) {
				// TODO: handle exception
				System.out.println("Invalid Credentials! Retry.");
			}

		} catch (ParseException e) {
			// TODO: handle exception
			System.err.println("Errore SQL");
		}

		in.close();
	}

	private static void doLogout() {
		SistemaDiAutenticazione sistemaAutenticazione = SistemaDiAutenticazione.getInstance();
		sistemaAutenticazione.logout();
		System.out.println("********** GOODBYE **********");
	}

}