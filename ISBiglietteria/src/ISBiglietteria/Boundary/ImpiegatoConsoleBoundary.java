package ISBiglietteria.Boundary;

import java.util.*;
import ISBiglietteria.Control.SistemaImpiegatoBiglietteria;
import ISBiglietteria.entities.Corsa;
import ISBiglietteria.entities.Prenotazione;

public class ImpiegatoConsoleBoundary {

	protected static void dipendenteBoundary() {
		System.out.println("********** IMPIEGATO **********");
		Scanner in = new Scanner(System.in);
		int opzione = 0;
		// Stampa il menu dell'impiegato
		System.out.print("1. Vendita biglietto.\n2. Conferma Prenotazione\n3. Esci dal sistema.\n: ");
		opzione = in.nextInt();
		// QUI DEVE ESSERE SOSTITUITO CON UNO SWITCH
		switch (opzione) {
		case 1:
			venditaBiglietto();
			break;
		case 2:
			gestisciPrenotazione();
			break;
		}

		in.close();
	}

	protected static void venditaBiglietto() {
		System.out.println("********** ACQUISTO **********");

		String targa = new String("N");// Utilizzato nel caso si voglia inserire una targa
		int idCorsa = 0;
		String targaVeicolo = null;// targaVeicolo inserito dal dipendente, nel caso non si inserisce nulla rimane
									// null
		// INPUT DEI DATI NECESSARI QUI
		Scanner in = new Scanner(System.in);

		System.out.println("Ricerca per porto di Arrivo-Partenza");
		System.out.print("Inserisci il porto di partenza: ");
		String portoDiPartenza = in.nextLine();
		System.out.print("Inserisci il porto di arrivo: ");
		String portoDiArrivo = in.nextLine();
		ArrayList<Corsa> corse = SistemaImpiegatoBiglietteria.visualizzaCorse(portoDiPartenza, portoDiArrivo);
		//CONTROLLO SE CI SONO CORSE COMPATIBILI CON QUESTI DATI
		if (!corse.isEmpty()) {
			//ITERA E STAMPA TUTTE LE CORSE RITORNATE
			Iterator<Corsa> i = corse.iterator();

			while (i.hasNext()) {
				Corsa corsa = i.next();
				System.out.println(corsa.toString());

			}
			System.out.print("Inserisci l'ID della corsa da acquistare: ");
			idCorsa = Integer.parseInt(in.nextLine());

			// SE DISPONIBILE LA DISPONIBILITA' VIENE DECREMENTATA PREVENTIVAMENTE
			// IN CASO LA PROCEDURA NON VADA A BUON FINE IL ROLLBACK RIPRISTINA LA
			// DISPONIBILITA' PRECEDENTE: tbd
			if (SistemaImpiegatoBiglietteria.verificaDisponibilita(idCorsa)) {
				System.out.println("***INSERIMENTO DATI CLIENTE***");
				System.out.print("Inserisci il nome del passeggero: ");
				String nomePasseggero = in.nextLine();
				System.out.print("Inserisci il cognome del passeggero: ");

				String cognomePasseggero = in.nextLine();
				System.out.print("Vuoi inseriere anche una targa? [y/n]\n: ");
				targa = in.nextLine();
				if (targa.equals("y") || targa.equals("Y")) {
					System.out.print("Inserisci la targa del veicolo: ");
					targaVeicolo = in.nextLine();
				}
				SistemaImpiegatoBiglietteria.acquistaBiglietto(idCorsa, nomePasseggero, cognomePasseggero,
						targaVeicolo);
				System.out.println("Biglietto acquistato con successo.");
			} else {
				System.out.println("I biglietto non sono più disponibili.");
			}
		} else {
			System.out.println("Non ci sono corse disponibili per i dati inseriti.");

		}
		in.close();

	}

	protected static void gestisciPrenotazione() {
		Scanner in = new Scanner(System.in);
		int idPrenotazione;
		System.out.println("**** PRENOTAZIONI *****");
		ArrayList<Prenotazione> prenotazioni = SistemaImpiegatoBiglietteria.visualizzaPrenotazioni();
		//CONTROLLO SE CI SONO PRENOTAZIONI COMPATIBILI CON QUESTI DATI
		if (!prenotazioni.isEmpty()) {
			//ITERA E STAMPA TUTTE LE PRENOTAZIONI RITORNATE
			Iterator<Prenotazione> i = prenotazioni.iterator();

			while (i.hasNext()) {
				Prenotazione prenotazione = i.next();
				if (prenotazione.getIdBiglietto() == 0)
					System.out.println(prenotazione.toString());
			}
			System.out.print("Inserisci l'ID della prenotazione da confermare: ");
			idPrenotazione = Integer.parseInt(in.nextLine());
			SistemaImpiegatoBiglietteria.confermaPrenotazione(idPrenotazione);
			System.out.println("Prenotazione confermata.");

		} else {
			System.out.println("Non ci sono prenotazione da confermare.");
		}

		in.close();

	}

}