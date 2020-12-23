package ISBiglietteria.Control;

import ISBiglietteria.Control.SistemaImpiegatoBiglietteria;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ISBiglietteria.DAO.DBManager;

class testVerificaDisponibilita {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		dbm = DBManager.getInstance();
		dbm.openConnection();
		try {
			dbm.getConnection().setAutoCommit(false);
		} catch (SQLException e) {

		}

	}

	@Test
	void test() {
		int idCorsa1 = 53;
		int idCorsa2 = 54;
		boolean esito = SistemaImpiegatoBiglietteria.verificaDisponibilita(idCorsa1);
		assertTrue(esito);
		esito = SistemaImpiegatoBiglietteria.verificaDisponibilita(idCorsa2);
		assertFalse(esito);
	}

	public static DBManager dbm;
}
