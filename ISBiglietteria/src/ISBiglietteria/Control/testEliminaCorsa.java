package ISBiglietteria.Control;

import static org.junit.jupiter.api.Assertions.*;
import ISBiglietteria.DAO.*;
import ISBiglietteria.entities.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

class testEliminaCorsa {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		dbm = DBManager.getInstance();
		dbm.openConnection();
		try {
			dbm.getConnection().setAutoCommit(false);
		} catch (SQLException e) {

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
	void testEliminaCorsa() {

		int orarioPartenza = 10;
		int orarioArrivo = 11;
		String portoPartenza = "molo test1";
		String portoArrivo = "molo test2";
		int idNave = 1;
		int prezzo = 23;
		CorsaDAO.creaCorsa(dbm, orarioPartenza, orarioArrivo, portoPartenza, portoArrivo, idNave, prezzo);
		// non ho modo di cercare la corsa perchè non ne posso ottenere
		SistemaDirigentiGestione.eliminaCorsa(lastId(dbm));
		Corsa corsa = CorsaDAO.leggiCorsa(dbm, lastId(dbm));
		assertNull(corsa);

	}

	public static DBManager dbm;
}
