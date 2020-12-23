package ISBiglietteria.entities;

import java.sql.Date;
import java.util.ArrayList;

//E' un singleton, perchè per ogni applicazione in esecuzione è necessario che sia possibile avere soltanto un istanza di ImpiegatoSingleton
public class ImpiegatoSingleton {

	// init è la procedura che deve essere chiamata post-costruzione
	// dell'istanza di ImpiegatoSingleton per inizializzare le variabili membro
	public void init(String codiceFiscale, int idImpiegato, String nome, String cognome, String username,
			String password, Date dataDiNascita) {
		this.codiceFiscale = codiceFiscale;
		this.idImpiegato = idImpiegato;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.dataDiNascita = dataDiNascita;
        this.idBiglietti = new ArrayList<Integer>();
        this.idPrenotazioni = new ArrayList<Integer>();

	}

//getInstance crea un' istanza del dipendente nel caso in cui non ce ne sia una già istanziata altrimenti ritona l'istanza già presente nel programmma
	public static ImpiegatoSingleton getInstance() {
		if (ImpiegatoSingleton.single_instance == null) {
			ImpiegatoSingleton.single_instance = new ImpiegatoSingleton();
		}
		return ImpiegatoSingleton.single_instance;

	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public int getIdImpiegato() {
		return this.idImpiegato;
	}

	public String getNome() {
		return this.nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public Date getDataDiNascita() {
		return this.dataDiNascita;
	}

	@Override
	public String toString() {
		return "{" + " codiceFiscale='" + getCodiceFiscale() + "'" + ", idImpiegato='" + getIdImpiegato() + "'"
				+ ", nome='" + getNome() + "'" + ", cognome='" + getCognome() + "'" + ", username='" + getUsername()
				+ "'" + ", password='" + getPassword() + "'" + ", dataDiNascita='" + getDataDiNascita();
	}

	private static ImpiegatoSingleton single_instance = null;
	private String codiceFiscale;
	private int idImpiegato;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private Date dataDiNascita;
	private ArrayList<Integer> idBiglietti; // Tracciamento di tutti i biglietti creati
																		// dall'impiegato
	private ArrayList<Integer> idPrenotazioni; // Tracciamento di tutte le prenotazioni
																			// confermate dall'impiegato

}