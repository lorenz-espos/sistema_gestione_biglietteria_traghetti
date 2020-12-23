package ISBiglietteria.entities;

import java.sql.Date;
import java.util.Objects;

public class Corsa {

	private Corsa() {
	}

	// Costruttore di Copia
	public Corsa(Corsa corsa) {
		this.idCorsa = corsa.getIdCorsa();
		this.orarioDiPartenza = corsa.getOrarioDiPartenza();
		this.orarioDiArrivo = corsa.getOrarioDiArrivo();
		this.portoDiPartenza = corsa.getPortoDiPartenza();
		this.portoDiArrivo = corsa.getPortoDiArrivo();
		this.data = corsa.getData();
		this.postiDisponibili = corsa.getPostiDisponibili();
	}

	// Corstruttore con idCorsa nullo
	public Corsa(int orarioDiPartenza, int orarioDiArrivo, String portoDiPartenza, String portoDiArrivo, Date data,
			int postiDisponibili) {
		this.idCorsa = null;
		this.orarioDiPartenza = orarioDiPartenza;
		this.orarioDiArrivo = orarioDiArrivo;
		this.portoDiPartenza = portoDiPartenza;
		this.portoDiArrivo = portoDiArrivo;
		this.data = data;
		this.postiDisponibili = postiDisponibili;
	}

	// Corstruttore con idCorsa non nullo, assegnato automaticamente dal DataBase
	public Corsa(Integer idCorsa, int orarioDiPartenza, int orarioDiArrivo, String portoDiPartenza,
			String portoDiArrivo, Date data, int postiDisponibili) {
		this.idCorsa = idCorsa;
		this.orarioDiPartenza = orarioDiPartenza;
		this.orarioDiArrivo = orarioDiArrivo;
		this.portoDiPartenza = portoDiPartenza;
		this.portoDiArrivo = portoDiArrivo;
		this.data = data;
		this.postiDisponibili = postiDisponibili;
	}

	public int getIdCorsa() {
		return idCorsa;
	}

	public void setIdCorsa(int idCorsa) {
		this.idCorsa = idCorsa;
	}

	public int getOrarioDiPartenza() {
		return orarioDiPartenza;
	}

	public void setOrarioDiPartenza(int orarioDiPartenza) {
		this.orarioDiPartenza = orarioDiPartenza;
	}

	public int getOrarioDiArrivo() {
		return orarioDiArrivo;
	}

	public void setOrarioDiArrivo(int orarioDiArrivo) {
		this.orarioDiArrivo = orarioDiArrivo;
	}

	public String getPortoDiPartenza() {
		return portoDiPartenza;
	}

	public void setPortoDiPartenza(String portoDiPartenza) {
		this.portoDiPartenza = portoDiPartenza;
	}

	public String getPortoDiArrivo() {
		return portoDiArrivo;
	}

	public void setPortoDiArrivo(String portoDiArrivo) {
		this.portoDiArrivo = portoDiArrivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPostiDisponibili() {
		return postiDisponibili;
	}

	public void setPostiDisponibili(int postiDisponibili) {
		this.postiDisponibili = postiDisponibili;
	}

	@Override
	public String toString() {
		return "Corsa [idCorsa=" + idCorsa + ", orarioDiPartenza=" + orarioDiPartenza + ", orarioDiArrivo="
				+ orarioDiArrivo + ", portoDiPartenza=" + portoDiPartenza + ", portoDiArrivo=" + portoDiArrivo
				+ ", data=" + data + ", postiDisponibili=" + postiDisponibili + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corsa other = (Corsa) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (idCorsa != other.idCorsa)
			return false;
		if (orarioDiArrivo != other.orarioDiArrivo)
			return false;
		if (orarioDiPartenza != other.orarioDiPartenza)
			return false;
		if (portoDiArrivo == null) {
			if (other.portoDiArrivo != null)
				return false;
		} else if (!portoDiArrivo.equals(other.portoDiArrivo))
			return false;
		if (portoDiPartenza == null) {
			if (other.portoDiPartenza != null)
				return false;
		} else if (!portoDiPartenza.equals(other.portoDiPartenza))
			return false;
		if (postiDisponibili != other.postiDisponibili)
			return false;
		return true;
	}

	private Integer idCorsa; // Integer poichè serve che sia un riferimento null all' inizializzazione quando
								// si va a creare una corsa non presente inizialmente nel DB
	private int orarioDiPartenza;
	private int orarioDiArrivo;
	private String portoDiPartenza;
	private String portoDiArrivo;
	private Date data;
	private int postiDisponibili;
}