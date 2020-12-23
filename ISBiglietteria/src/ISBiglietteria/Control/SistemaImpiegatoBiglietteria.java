package ISBiglietteria.Control;

import java.util.*;
import ISBiglietteria.DAO.*;
import ISBiglietteria.entities.*;

public class SistemaImpiegatoBiglietteria {

	// Conferma una prenotazione conoscendone l'ID
	public static void confermaPrenotazione(int idPrenotazione) {
		DBManager dbm = DBManager.getInstance();
		Prenotazione prenotazione = PrenotazioneDAO.leggiPrenotazione(dbm, idPrenotazione);
		System.out.println(CorsaDAO.leggiCorsa(dbm, prenotazione.getIdCorsa()));
		// System.out.println("id"+ImpiegatoSingleton.getInstance().getIdImpiegato());
		if (ImpiegatoDAO.leggiTipoImpiegato(dbm, DirigenteSingleton.getInstance().getUsername(),
				DirigenteSingleton.getInstance().getPassword()) == 0) {
			// Viene creato un nuovo biglietto utilizzando le informazioni della
			// prenotazione
			Biglietto biglietto = new Biglietto(0, ImpiegatoSingleton.getInstance().getIdImpiegato(),
					prenotazione.getNome(), prenotazione.getCognome(),
					CorsaDAO.leggiCorsa(dbm, prenotazione.getIdCorsa()), prenotazione.getTargaVeicolo());
			BigliettoDAO.creaBiglietto(dbm, biglietto);
		} else {
			Biglietto biglietto = new Biglietto(0, DirigenteSingleton.getInstance().getIdImpiegato(),
					prenotazione.getNome(), prenotazione.getCognome(),
					CorsaDAO.leggiCorsa(dbm, prenotazione.getIdCorsa()), prenotazione.getTargaVeicolo());
			BigliettoDAO.creaBiglietto(dbm, biglietto);
		}
		int idBiglietto = BigliettoDAO.lastId(dbm);
		PrenotazioneDAO.aggiornaPrenotazione(dbm, idBiglietto, idPrenotazione);
		System.out.println(PrenotazioneDAO.leggiPrenotazione(dbm, idPrenotazione).toString());
	}

	public static ArrayList<Prenotazione> visualizzaPrenotazioni() {
		DBManager dbm = DBManager.getInstance();
		ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		prenotazioni = PrenotazioneDAO.leggiPrenotazioni(dbm);

		return prenotazioni;
	}

	public static void acquistaBiglietto(int idCorsa, String nomePasseggero, String cognomePasseggero,
			String targaVeicolo) {
		DBManager dbm = DBManager.getInstance();
		CorsaDAO.aggiornaCorsa(dbm, idCorsa);
		Biglietto biglietto = new Biglietto(idCorsa, ImpiegatoSingleton.getInstance().getIdImpiegato(), nomePasseggero,
				cognomePasseggero, CorsaDAO.leggiCorsa(dbm, idCorsa), targaVeicolo);
		BigliettoDAO.creaBiglietto(dbm, biglietto);
	}

	public static String emissioneBiglietto(Biglietto biglietto) {
		return biglietto.toString();

	}

	public static boolean verificaDisponibilita(int idCorsa) {
		DBManager dbm = DBManager.getInstance();
		int numeroPosti = CorsaDAO.leggiCorsa(dbm, idCorsa).getPostiDisponibili();
		if (numeroPosti <= 0) {
			return false;
		}
		return true;

	}

	public static ArrayList<Corsa> visualizzaCorse(String portoDiPartenza, String portoDiArrivo) {
		ArrayList<Corsa> corse = new ArrayList<Corsa>();
		DBManager dbm = DBManager.getInstance();
		corse = CorsaDAO.leggiCorse(dbm, portoDiPartenza, portoDiArrivo);

		return corse;
	}
}