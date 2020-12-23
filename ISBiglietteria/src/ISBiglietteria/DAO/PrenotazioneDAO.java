package ISBiglietteria.DAO;

import ISBiglietteria.entities.Prenotazione;
import java.util.ArrayList;
import java.sql.*;

public class PrenotazioneDAO {

	private static Prenotazione deserializeCurrentRecord(ResultSet rs) throws SQLException {

		Prenotazione prenotazione = new Prenotazione(rs.getInt("idPrenotazione"), rs.getString("targaVeicolo"),
				rs.getString("codiceFiscale"), rs.getInt("idCorsa"), rs.getInt("idBiglietto"), rs.getString("nome"),
				rs.getString("cognome"));

		return prenotazione;

	}

//legge tutte le prenotazioni nel db
	public static ArrayList<Prenotazione> leggiPrenotazioni(DBManager dbManager) {
		ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		Prenotazione prenotazione = null;

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM prenotazioneJava")) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					prenotazione = deserializeCurrentRecord(resultSet);
					prenotazioni.add(prenotazione);
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
				// e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
		}

		return prenotazioni;

	}

//ricerca prenotazione per id
	public static Prenotazione leggiPrenotazione(DBManager dbManager, int idPrenotazione) {
		Prenotazione prenotazione = null;

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM prenotazioneJava WHERE idPrenotazione = ?")) {
			preparedStatement.setInt(1, idPrenotazione);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next())
					prenotazione = deserializeCurrentRecord(resultSet);

			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				// e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			// e.printStackTrace(System.out);
		}

		return prenotazione;
	}

//ricerca le prenotazioni per il codice fiscale del cliente
	public static Prenotazione leggiPrenotazione(DBManager dbManager, String codiceFiscale) {
		Prenotazione prenotazione = null;

		Connection conn = dbManager.getConnection();
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT * FROM prenotazioneJava WHERE codiceFiscale = ?")) {
			preparedStatement.setString(1, codiceFiscale);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next())
					prenotazione = deserializeCurrentRecord(resultSet);

			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				// e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			// e.printStackTrace(System.out);
		}

		return prenotazione;

	}
	// Una volta confermata la prenotazione necessita di essere collegata al
	// biglietto appena creato tramite il nuovo ID

	public static void aggiornaPrenotazione(DBManager dbManager, int idBiglietto, int idPrenotazione) {
		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("UPDATE prenotazioni SET idBiglietto = ? WHERE id = ? ")) {
			preparedStatement.setInt(1, idBiglietto);
			preparedStatement.setInt(2, idPrenotazione);

			preparedStatement.executeUpdate();
			dbManager.getConnection().commit();
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			// e.printStackTrace(System.out);
		}
	}

}
