package ISBiglietteria.Boundary;

import java.util.*;
import ISBiglietteria.Control.SistemaDirigentiGestione;
import ISBiglietteria.DAO.DBManager;
import ISBiglietteria.entities.Corsa;

public class DirigenteConsoleBoundary extends ImpiegatoConsoleBoundary {

	protected static void dipendenteBoundary() {
		System.out.println("********** IMPIEGATO **********");
		Scanner in = new Scanner(System.in);
		int opzione = 0;
		DBManager dbm = DBManager.getInstance();
		try {
			System.out.print(
					"1. Vendita biglietto.\n2. Conferma Prenotazione\n3. Aggiungi una corsa.\n4. Annulla una corsa.\n5. Modifica corsa: ");
			opzione = in.nextInt();
			// QUI DEVE ESSERE SOSTITUITO CON UNO SWITCH
			if (opzione == 1)
				venditaBiglietto();
			else if (opzione == 2)
				gestisciPrenotazione();
			else if (opzione == 3)
				annullaCorsa();
			else if (opzione == 4)
				System.out.println("In fase di sviluppo...");
			else if (opzione == 5)
				System.out.println("In fase di sviluppo...");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Valore non valido.");
		}
		in.close();
	}

	// Elimina una corsa dal database
	private static void annullaCorsa() {
		Scanner in = new Scanner(System.in);
		System.out.println("**** ELIMINAZIONE DI UNA CORSA *****");

		// STAMPA TUTTE LE CORSE
		ArrayList<Corsa> corse = SistemaDirigentiGestione.visualizzaCorse();
		Iterator<Corsa> i = corse.iterator();
		while (i.hasNext()) {
			Corsa corsa = i.next();
			System.out.println(corsa.toString());
		}
		System.out.print("Inserisci l'ID della corsa da eliminare: ");
		Integer idCorsa = Integer.parseInt(in.nextLine());
		SistemaDirigentiGestione.eliminaCorsa(idCorsa);
		System.out.println("********** CORSA ELIMINATA. **********");
		in.close();
	}

	// TBD in fase di sviluppo
	/*
	 * private static void creaCorsa() { Scanner in = new Scanner(System.in);
	 * System.out.println("**** INSERIMENTO DI UNA NUOVA CORSA *****");
	 * //SistemaDirigentiGestione.visualizzaCompanie();
	 * //System.out.print("Inserisci l'ID della compagnia da inserire: "); //Integer
	 * idCompagnia = Integer.parseInt(in.nextLine());
	 * System.out.print("Inserisci i dati della corsa.\nPorto di partenza: ");
	 * String portoDiPartenza = in.nextLine();
	 * System.out.println("Orario di partenza: "); Integer orarioDiPartenza =
	 * Integer.parseInt(in.nextLine()); System.out.print("Porto di arrivo: ");
	 * String portoDiArrivo = in.nextLine(); System.out.print("Orario di arrivo: ");
	 * Integer orarioDiArrivo = Integer.parseInt(in.nextLine());
	 * System.out.print("Data di partenza [dd-MM-yyyy]: "); String
	 * datas=in.nextLine(); System.out.print("Prezzo: "); Integer prezzo =
	 * Integer.parseInt(in.nextLine()); try { java.util.Date dataJava = new
	 * SimpleDateFormat("dd-MM-yyyy").parse(datas); Date dataSQL = new
	 * Date(dataJava.getTime());
	 * SistemaDirigentiGestione.inserisciCorsa(portoDiPartenza, portoDiArrivo,
	 * orarioDiPartenza, orarioDiArrivo, prezzo, dataSQL, idCompagnia);
	 * 
	 * } catch (ParseException e) { System.err.println("Errore inserimentod data.");
	 * } in.close(); }
	 */

}
