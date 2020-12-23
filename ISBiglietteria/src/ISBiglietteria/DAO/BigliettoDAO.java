package ISBiglietteria.DAO;

import java.util.ArrayList;
import java.sql.*;

import ISBiglietteria.entities.*;

public class BigliettoDAO {

	private static Biglietto deserializeCurrentRecord(ResultSet rs) throws SQLException {

		Aliscafo aliscafo = new Aliscafo(rs.getInt("codiceNave"), rs.getInt("capienzaPasseggeri"),
				new Corsa(rs.getInt("codiceCorsa"), rs.getInt("oraPartenza"), rs.getInt("oraArrivo"),
						rs.getString("portoPartenza"), rs.getString("portoArrivo"), rs.getDate("data"),
						rs.getInt("postiDisponibili")));
		Biglietto biglietto = new Biglietto(rs.getInt("id"), rs.getInt("idImpiegato"), rs.getString("nome"),
				rs.getString("cognome"), aliscafo.getCorsa(), rs.getString("targaVeicolo"));

		return biglietto;
	}

//legge tutti i biglietti presenti nel DB
	public static ArrayList<Biglietto> leggiBiglietti(DBManager dbManager) {
		ArrayList<Biglietto> biglietti = new ArrayList<Biglietto>();
		Biglietto biglietto = null;

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM viewBiglietto")) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					biglietto = deserializeCurrentRecord(resultSet);
					biglietti.add(biglietto);
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
			}
		} catch (SQLException e1) {
			System.err.println("Error: " + e1);
		}

		return biglietti;

	}

//ricerca biglietto per id 
	public static Biglietto leggiBiglietto(DBManager dbManager, int idPrenotazione) {
		Biglietto biglietto = null;

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM viewBiglietto WHERE id = ?")) {
			preparedStatement.setInt(1, idPrenotazione);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					biglietto = deserializeCurrentRecord(resultSet);
				}

			} catch (SQLException e) {
				System.err.println("Error: " + e);
			}

		} catch (SQLException e) {
			System.err.println("Error: " + e);
		}

		return biglietto;
	}

//funzione che permette di creare un nuovo biglietto nel DB
	public static void creaBiglietto(DBManager dbManager, Biglietto biglietto) {

		try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(
				"INSERT INTO biglietti(idCorsa, data, nome, cognome, idImpiegato, targaVeicolo) VALUES (?, ?, ?, ?, ?, ?)")) {

			Date dataSQL = new Date(System.currentTimeMillis());

			preparedStatement.setInt(1, biglietto.getCorsa().getIdCorsa());
			preparedStatement.setDate(2, dataSQL);
			preparedStatement.setString(3, biglietto.getNomePasseggero());
			preparedStatement.setString(4, biglietto.getCognomePassegero());
			preparedStatement.setInt(5, biglietto.getIdImpiegato());
			preparedStatement.setString(6, biglietto.getTargaVeicolo());

			preparedStatement.executeUpdate();
			// COMMIT
			dbManager.getConnection().commit();

		} catch (SQLException e) {
			System.err.println("Error: " + e);
			try {
				dbManager.getConnection().rollback();
			} catch (SQLException e1) {
				System.err.println("Error: " + e1);

			}
		}

	}

	// Funzione che preleva l'ultimo id del biglietto inserito all'interno del
	// database tramite l'istanza di connessione attuale
	// Necessaria in quanto l'id è autogenerato dal database e non noto inizialmente
	// al programma in caso di creazione ex novo
	public static int lastId(DBManager dbManager) {
		int id = 0;
		// LAST_INSERT_ID() è una funzione concessa da MySQL
		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT LAST_INSERT_ID() AS id")) {
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					id = rs.getInt("id");

				}
			} catch (SQLException e) {
				System.err.println("Error" + e);

			}
		} catch (SQLException e1) {

			System.err.println("Error: " + e1);

		}
		return id;
	}

}
