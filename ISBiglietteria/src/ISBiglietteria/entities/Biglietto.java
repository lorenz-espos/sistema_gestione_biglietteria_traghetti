package ISBiglietteria.entities;



public class Biglietto {

	// costrutture public di biglietto che non prevede il veicolo
	public Biglietto(int idBiglietto, int idImpiegato, String nomePasseggero, String cognomePassegero, Corsa corsa) {
		this.idBiglietto = idBiglietto;
		this.idImpiegato = idImpiegato;
		this.targaVeicolo = null;
		this.nomePasseggero = nomePasseggero;
		this.cognomePassegero = cognomePassegero;
		this.corsa = corsa;
	}

	// Costruttore per il biglietto che incorpora anche un veicolo
	public Biglietto(int idBiglietto, int idImpiegato, String nomePasseggero, String cognomePassegero, Corsa corsa,
			String targaVeicolo) {
		this.idBiglietto = idBiglietto;
		this.idImpiegato = idImpiegato;
		this.nomePasseggero = nomePasseggero;
		this.cognomePassegero = cognomePassegero;
		this.targaVeicolo = targaVeicolo;
		this.corsa = corsa;
	}

	public int getIdBiglietto() {
		return this.idBiglietto;
	}

	public int getIdImpiegato() {
		return this.idImpiegato;
	}

	public String getNomePasseggero() {
		return this.nomePasseggero;
	}

	public String getCognomePassegero() {
		return this.cognomePassegero;
	}

	public String getTargaVeicolo() {
		return this.targaVeicolo;
	}

	public Corsa getCorsa() {
		return this.corsa;
	}

	@Override
	public String toString() {
		return "Biglietto [idBiglietto=" + idBiglietto + ", idImpiegato=" + idImpiegato + ", nomePasseggero="
				+ nomePasseggero + ", cognomePassegero=" + cognomePassegero + ", targaVeicolo=" + targaVeicolo
				+ ", aliscafo=" + corsa + "]";
	}

	// Le variabili membro sono tutte final perchè una volta creato un istanza
	// dell'oggetto biglietto
	// i campi non possono essere modificati
	private final int idBiglietto;
	private final int idImpiegato;
	private final String nomePasseggero;
	private final String cognomePassegero;
	private final String targaVeicolo;
	private final Corsa corsa;// il biglietto non può esistere senza la corsa quindi la incorpora
}