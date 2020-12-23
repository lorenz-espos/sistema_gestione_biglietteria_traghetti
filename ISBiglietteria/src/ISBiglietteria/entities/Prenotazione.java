package ISBiglietteria.entities;

public class Prenotazione {

	// Costruttore privato per prevenire l'istanziamento
	private Prenotazione() {
	}

//Costruttore public con parametri
	public Prenotazione(int idPrenotazione, String targaVeicolo, String codiceFiscale, int idCorsa, int idBiglietto,
			String nome, String cognome) {
		super();
		this.idPrenotazione = idPrenotazione;
		this.targaVeicolo = targaVeicolo;
		this.codiceFiscale = codiceFiscale;
		this.idCorsa = idCorsa;
		this.idBiglietto = idBiglietto;
		this.nome = nome;
		this.cognome = cognome;
	}

	public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public String getTargaVeicolo() {
		return targaVeicolo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public int getIdCorsa() {
		return idCorsa;
	}

	/*
	 * 
	 * public void setIdCorsa(int idCorsa) { this.idCorsa = idCorsa; } public void
	 * setNome(String nome) { this.nome = nome; }
	 * 
	 * public void setCognome(String cognome) { this.cognome = cognome; }
	 * 
	 * public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale =
	 * codiceFiscale; }
	 * 
	 * 
	 * public void setTargaVeicolo(String targaVeicolo) { this.targaVeicolo =
	 * targaVeicolo; }
	 * 
	 * public void setIdPrenotazione(int idPrenotazione) { this.idPrenotazione =
	 * idPrenotazione; }
	 * 
	 * 
	 */

	public int getIdBiglietto() {
		return idBiglietto;
	}

	public void setIdBiglietto(int idBiglietto) {
		this.idBiglietto = idBiglietto;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prenotazione other = (Prenotazione) obj;
		if (codiceFiscale == null) {
			if (other.codiceFiscale != null)
				return false;
		} else if (!codiceFiscale.equals(other.codiceFiscale))
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (idBiglietto != other.idBiglietto)
			return false;
		if (idCorsa != other.idCorsa)
			return false;
		if (idPrenotazione != other.idPrenotazione)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (targaVeicolo == null) {
			if (other.targaVeicolo != null)
				return false;
		} else if (!targaVeicolo.equals(other.targaVeicolo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prenotazione [idPrenotazione=" + idPrenotazione + ", targaVeicolo=" + targaVeicolo + ", codiceFiscale="
				+ codiceFiscale + ", idCorsa=" + idCorsa + ", idBiglietto=" + idBiglietto + ", nome=" + nome
				+ ", cognome=" + cognome + "]";
	}

	private int idPrenotazione;
	private String targaVeicolo;
	private String codiceFiscale;
	private int idCorsa;
	private int idBiglietto; // Questo è l'id del biglietto che viene creato una volta che la prenotazione
								// vienconfermata
	private String nome;
	private String cognome;
}
