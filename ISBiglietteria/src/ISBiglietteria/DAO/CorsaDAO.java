package ISBiglietteria.DAO;

import ISBiglietteria.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class CorsaDAO {

	private static Corsa deserializeCurrentRecord(ResultSet rs) throws SQLException {

		// CONVIENE UTILIZZARE IL COSTRUTTORE
		// L'oggetto Aliscafo si occupa di costruire l'istanza di corsa
		Aliscafo aliscafo = new Aliscafo(rs.getInt("codiceNave"), rs.getInt("capienza"),
				new Corsa(rs.getInt("idCorsa"), rs.getInt("oraPartenza"), rs.getInt("oraArrivo"),
						rs.getString("portoPartenza"), rs.getString("portoArrivo"), rs.getDate("data"),
						rs.getInt("postiDisponibili")),
				rs.getInt("idCompagnia"), rs.getString("nomeCompagnia"));
		return aliscafo.getCorsa();

	}

// ritorna tutte le corse disponibili
	public static ArrayList<Corsa> leggiCorse(DBManager dbManager) {
		ArrayList<Corsa> corse = new ArrayList<Corsa>();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM corsaJava2 WHERE postiDisponibili > 0")) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					corse.add(deserializeCurrentRecord(resultSet));
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
				// e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
		}

		return corse;
	}

//ritorna le corse disponibili in base al porto di partenza e porto di arrivo
	public static ArrayList<Corsa> leggiCorse(DBManager dbManager, String portoDiPartenza, String portoDiArrivo) {
		ArrayList<Corsa> corse = new ArrayList<Corsa>();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM corsaJava2 WHERE portoPartenza = ? AND portoArrivo = ? AND postiDisponibili > 0")) {
			preparedStatement.setString(1, portoDiPartenza);
			preparedStatement.setString(2, portoDiArrivo);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					corse.add(deserializeCurrentRecord(resultSet));
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
				// e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
		}

		return corse;
	}

//ritorna le corse disponibili in base all' orario di partenza e di arrivo 
	public static ArrayList<Corsa> leggiCorse(DBManager dbManager, int orarioDiPartenza, int orarioDiArrivo) {
		ArrayList<Corsa> corse = new ArrayList<Corsa>();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM corsaJava2 WHERE oraArrivo = ? AND oraPartenza = ? AND postiDisponibili > 0")) {
			preparedStatement.setInt(1, orarioDiArrivo);
			preparedStatement.setInt(2, orarioDiPartenza);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					corse.add(deserializeCurrentRecord(resultSet));
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
				// e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
		}

		return corse;

	}

//ritona la corsa in base all id
	public static Corsa leggiCorsa(DBManager dbManager, int idCorsa) {
		Corsa corsa = null;
		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM corsaJava2 WHERE idCorsa = ?")) {
			preparedStatement.setInt(1, idCorsa);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					corsa = deserializeCurrentRecord(resultSet);
				}
			} catch (SQLException e) {
				System.err.println("Error: " + e);
				// e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			// e.printStackTrace();
		}

		return corsa;

	}

	// INSERIMENTO TRATTA all'interno del database
	public static void creaCorsa(DBManager dbManager, int orarioDiArrivo, int orarioDiPartenza, String portoDiArrivo,
			String portoDiPartenza, int idNave, int prezzo) {
		try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(
				"INSERT INTO tratta (oraArrivo, oraPartenza, portoArrivo, portoPartenza, idNave, prezzo) VALUES (?,?,?,?,?,?)")) {
			preparedStatement.setInt(1, orarioDiArrivo);
			preparedStatement.setInt(2, orarioDiPartenza);
			preparedStatement.setString(3, portoDiArrivo);
			preparedStatement.setString(4, portoDiPartenza);
			preparedStatement.setInt(5, idNave);
			preparedStatement.setInt(6, prezzo);

			preparedStatement.executeUpdate();
			// COMMIT
			dbManager.getConnection().commit();

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			// e.printStackTrace();
			try {
				dbManager.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO: handle exception
				System.err.println("Error: " + e1);
				// e1.printStackTrace();
			}

		}

	}

	// Elimina una corsa dal database, si ha la necessità di eliminare prima i
	// biglietti e le prenotazioni associate alla corsa
	public static void eliminaCorsa(DBManager dbManager, int idCorsa) {
		Connection conn = dbManager.getConnection();
		// poichè sono stati innestate 3 query sono risultati necessari 3 blocchi try catch
		// inoltre per ogni query bisogna considerare un rollback nel catch e per questo
		// è presente un ulteriore try con il suo rispettivo blocco catch
		try (PreparedStatement deletePrenotazioni = conn
				.prepareStatement("DELETE FROM prenotazioni WHERE idCorsa = ?")) {
			deletePrenotazioni.setInt(1, idCorsa);
			deletePrenotazioni.executeUpdate();
			conn.commit();
			try (PreparedStatement deleteBiglietto = conn.prepareStatement("DELETE FROM biglietti WHERE idCorsa = ?")) {
				deleteBiglietto.setInt(1, idCorsa);
				deleteBiglietto.executeUpdate();
				conn.commit();
				try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM corse WHERE id = ?")) {
					preparedStatement.setInt(1, idCorsa);
					preparedStatement.executeUpdate();
					conn.commit();
				} catch (SQLException e) {
					System.err.println("Error: " + e);
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error: " + e1);
					}
				}
			} catch (SQLException e2) {
				System.err.println("Error: " + e2);
				try {
					conn.rollback();
				} catch (SQLException e3) {
					System.err.println("Error: " + e3);
				}
			}
		} catch (SQLException e4) {
			System.err.println("Error: " + e4);
			try {
				conn.rollback();
			} catch (SQLException e5) {
				System.err.println("Error: " + e5);

			}

		}
	}

	// AGGIORNA LA DISPONIBILITA' DEI POSTI VENDUTI PER UNA DETERMINATA CORSA
	//Nel database si tiene traccia dei posti venduti e non della disponibilità dei posti
	public static void aggiornaCorsa(DBManager dbManager, int idCorsa) {
		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("UPDATE corse SET postiVenduti = postiVenduti + 1 WHERE id = ? ")) {
			preparedStatement.setInt(1, idCorsa);
			preparedStatement.executeUpdate();
			dbManager.getConnection().commit();
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			try {
				dbManager.getConnection().rollback();
			} catch (SQLException e3) {
				System.err.println("Error: " + e3);
			}
		}
	}
}
