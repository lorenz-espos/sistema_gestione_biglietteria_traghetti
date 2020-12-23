package ISBiglietteria.entities;

import java.sql.Date;
import java.util.*;

public class Aliscafo {

	// Costruttore privato per prevenire l'instanziazione dell'oggetto senza
	// parametri
	// L'oggetto può esistere solo in uno stato valido in cui i parametri sono
	// presenti
	private Aliscafo() {
		this.corsa = null;
		this.idNave = 0;
		this.capienzaPasseggeri = 0;
	}

	// Copy Constructor
	public Aliscafo(Aliscafo aliscafo) {
		this.idNave = aliscafo.getIdNave();
		this.capienzaPasseggeri = aliscafo.getCapienzaPasseggeri();
		this.corsa = new Corsa(aliscafo.corsa);
		this.idCompagnia = aliscafo.idCompagnia;
		this.nomeCompagnia = aliscafo.nomeCompagnia;
	}

//Costruttore public con parametri id della nave ,capienza della nave e oggetto corsa
	public Aliscafo(int idNave, int capienzaPasseggeri, Corsa corsa) {
		this.idNave = idNave;
		this.capienzaPasseggeri = capienzaPasseggeri;
		this.corsa = new Corsa(corsa);
		this.idCompagnia = 0;
		this.nomeCompagnia = null;
	}

	// Costruttore public con parametri id della nave oggetto corsa id della
	// compagnia nome della compagnia
	public Aliscafo(int idNave, int capienzaPasseggeri, Corsa corsa, int idCompagnia, String nomeCompagnia) {
		this.idNave = idNave;
		this.capienzaPasseggeri = capienzaPasseggeri;
		this.corsa = new Corsa(corsa);
		this.idCompagnia = idCompagnia;
		this.nomeCompagnia = nomeCompagnia;

	}

	public int getIdNave() {
		return this.idNave;
	}

	public int getIdCompagnia() {
		return this.idCompagnia;
	}

	public String getNomeCompagnia() {
		return this.nomeCompagnia;
	}

	public int getCapienzaPasseggeri() {
		return this.capienzaPasseggeri;
	}

	public Corsa getCorsa() {
		return this.corsa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aliscafo other = (Aliscafo) obj;
		if (capienzaPasseggeri != other.capienzaPasseggeri)
			return false;
		if (idNave != other.idNave)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{" + " idNave='" + getIdNave() + "'" + ", capienzaPasseggeri='" + getCapienzaPasseggeri() + "'" + "}";
	}

	private final int idNave;
	private final int capienzaPasseggeri;
	private final Corsa corsa; // L'oggetto Aliscafo si occupa di gestire il ciclo di vita dell'oggetto Corsa
	private int idCompagnia;
	private String nomeCompagnia;
}