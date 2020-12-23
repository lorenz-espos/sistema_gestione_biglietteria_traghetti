package ISBiglietteria.Control;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ISBiglietteria.DAO.DBManager;

class testRegistrazione {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		dbm = DBManager.getInstance();
		dbm.openConnection();
		try {
			dbm.getConnection().setAutoCommit(false);
		} catch (SQLException e) {

		}

	}

	@AfterAll
	public static void setUpAfterClass() throws Exception {

		Connection conn = dbm.getConnection();
		// poichè sono stati innestate 3 query sono risultati necessari 3 blocchi try
		// catch
		// inoltre per ogni query bisogna considerare un rollback nel catch e per questo
		// è presente un ulteriore try con il suo rispettivo blocco catch
		try (PreparedStatement deleteTest = conn.prepareStatement("DELETE FROM impiegato WHERE id = ?")) {
			deleteTest.setInt(1, lastId(dbm));
			deleteTest.executeUpdate();
			conn.commit();
		} catch (SQLException e5) {
			System.err.println("Error: " + e5);

		}

	}

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

	@Test
	void test() {
		SistemaDiAutenticazione stm = SistemaDiAutenticazione.getInstance();
		String nome = new String("test");
		String cognome = new String("test");
		Integer tipo = 0;

		/* Username già utilizzato e cf disponibili */
		String codiceFiscale1 = new String("CF1");
		String password1 = new String("test");
		String username1 = new String("test");

		/* User pw e cf disponibili */
		String password2 = new String("test1");
		String username2 = new String("test1");
		String codiceFiscale2 = new String("testCF");

		/* CF già utilizzato */
		String password3 = new String("test3");
		String username3 = new String("test3");
		String codiceFiscale3 = new String("test");

		String esito1 = new String();
		String esito2 = new String();
		String esito3 = new String();
		esito1 = stm.proceduraDiRegistrazione(codiceFiscale1, nome, cognome, username1, password1, tipo, null);
		esito2 = stm.proceduraDiRegistrazione(codiceFiscale2, nome, cognome, username2, password2, tipo, null);
		esito3 = stm.proceduraDiRegistrazione(codiceFiscale3, nome, cognome, username3, password3, tipo, null);
		assertEquals("Username già utilizzato.", esito1);
		assertEquals("Username e password disponibili, registrazione effettuata!", esito2);
		assertEquals("Codice Fiscale già esistente.", esito3);

	}

	public static DBManager dbm;
}
