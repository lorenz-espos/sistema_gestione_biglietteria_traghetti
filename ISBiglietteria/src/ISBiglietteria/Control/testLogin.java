package ISBiglietteria.Control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;

import ISBiglietteria.Control.*;
import ISBiglietteria.DAO.DBManager;
import ISBiglietteria.DAO.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

class testLogin {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {

		DBManager.getInstance().openConnection();
		try {
			DBManager.getInstance().getConnection().setAutoCommit(false);
		} catch (SQLException e) {
		}
	}

	@Test
	void test() {
		SistemaDiAutenticazione stm = SistemaDiAutenticazione.getInstance();
		String username1 = new String("test");
		String password1 = new String("test");
		String username2 = new String("is");
		String password2 = new String("is");
		String esito1 = new String();
		String esito2 = new String();
		esito1 = stm.login(username1, password1);
		esito2 = stm.login(username2, password2);
		assertEquals("Accesso effettuato.", esito1);
		assertEquals("Username o password errati!", esito2);
	}
}
