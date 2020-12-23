package ISBiglietteria.DAO;

import java.sql.*;
import ISBiglietteria.entities.*;

public class ImpiegatoDAO {

	private static ImpiegatoSingleton deserializeCurrentRecord(ResultSet rs) throws SQLException {

		ImpiegatoSingleton impiegato = new ImpiegatoSingleton();
		while (rs.next()) {
			impiegato.init(rs.getString("codiceFiscale"), rs.getInt("id"), rs.getString("nome"),
					rs.getString("cognome"), rs.getString("username"), rs.getString("userpass"),
					rs.getDate("dataDiNascita"));
		}
		return impiegato;
	}
//la funzione viene usata per distinguere l'impiegato o il dirigente all' interno del DB poichè sono identificati dall' attributo tipo
	public static Integer leggiTipoImpiegato(DBManager dbManager, String user, String password) {
		Integer tipo = null;
		//LA FUNZIONE PASSWORD(?) DI MYSQL USA UN ALGORITMO DI HASHING PER MASKERARE LA PASSWORD CHE VIENE INVIATA AL DATABASE
		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT tipo FROM impiegato WHERE username = ? AND userpass = PASSWORD(?) ")) {
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next())
					tipo = resultSet.getInt("tipo");
			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				//e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			//e.printStackTrace(System.out);

		}

		return tipo;
	}
//ricerca impiegato per codice fiscale
	public static ImpiegatoSingleton leggiImpiegatoCF(DBManager dbManager, String codiceFiscale) {

		ImpiegatoSingleton impiegato = new ImpiegatoSingleton();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM impiegato WHERE codiceFiscale=? ")) {
			preparedStatement.setString(1, codiceFiscale);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				impiegato = deserializeCurrentRecord(resultSet);
			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				//e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			//e.printStackTrace(System.out);

		}

		return impiegato;
	}
//ricerca impiegato per l'user al fine di verificare se l'utente specificato è già presente nel DB per prevenire dei duplicati
	public static ImpiegatoSingleton leggiImpiegatoUser(DBManager dbManager, String user) {

		ImpiegatoSingleton impiegato = new ImpiegatoSingleton();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM impiegato WHERE username = ? ")) {
			preparedStatement.setString(1, user);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				impiegato = deserializeCurrentRecord(resultSet);
			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				//e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			//e.printStackTrace(System.out);

		}

		return impiegato;
	}
//ricerca impiegato per codice e password
	public static ImpiegatoSingleton leggiImpiegato(DBManager dbManager, String user, String password) {

		ImpiegatoSingleton impiegato = new ImpiegatoSingleton();

		try (PreparedStatement preparedStatement = dbManager.getConnection()
				.prepareStatement("SELECT * FROM impiegato WHERE username = ? AND userpass = PASSWORD(?) ")) {
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				impiegato = deserializeCurrentRecord(resultSet);
			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Error: " + e);
				//e.printStackTrace(System.out);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			//e.printStackTrace(System.out);

		}

		return impiegato;
	}

	public static void creaImpiegato(DBManager dbManager, ImpiegatoSingleton impiegato, int ruolo) {
		//LA FUNZIONE PASSWORD(?) DI MYSQL USA UN ALGORITMO DI HASHING PER MASKERARE LA PASSWORD CHE VIENE INVIATA AL DATABASE
		//LE PASSWORD VENGONO MEMORIZZATE COME HASH PER UNA MAGGIORE SICUREZZA
		try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement(
				"INSERT INTO impiegato(codiceFiscale, nome, cognome, userpass, username, tipo, dataDiNascita) VALUES (?, ?, ?, PASSWORD(?), ?, ?, ? )")) {
			preparedStatement.setString(1, impiegato.getCodiceFiscale());
			preparedStatement.setString(2, impiegato.getNome());
			preparedStatement.setString(3, impiegato.getCognome());
			preparedStatement.setString(4, impiegato.getPassword());
			preparedStatement.setString(5, impiegato.getUsername());
			preparedStatement.setInt(6, ruolo);
			preparedStatement.setDate(7, impiegato.getDataDiNascita());

			preparedStatement.executeUpdate();
	
			//COMMIT
			dbManager.getConnection().commit();

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Error: " + e);
			//e.printStackTrace(System.out);
			try {
				dbManager.getConnection().rollback();
			}
			catch(SQLException e1) {
				System.err.println("Error: " + e1);
				//e1.printStackTrace(System.out);
				
			}
		}
	}
}
