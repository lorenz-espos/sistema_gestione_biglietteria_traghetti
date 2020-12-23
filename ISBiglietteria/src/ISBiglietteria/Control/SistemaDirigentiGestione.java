package ISBiglietteria.Control;

import ISBiglietteria.entities.*;
import ISBiglietteria.DAO.*;
import java.sql.Date;
import java.util.*;

public class SistemaDirigentiGestione extends SistemaImpiegatoBiglietteria {

    public static void trovaCorsa(int idCorsa) {
        DBManager dbm = DBManager.getInstance();
        Corsa corsa = CorsaDAO.leggiCorsa(dbm, idCorsa);
    }

    public static void eliminaCorsa(int idCorsa) {
    	DBManager dbm = DBManager.getInstance();
    	CorsaDAO.eliminaCorsa(dbm, idCorsa);
    }


    public static ArrayList<Corsa> visualizzaCorse() {
        DBManager dbm = DBManager.getInstance();
        ArrayList<Corsa> corse = new ArrayList<Corsa>();
        corse = CorsaDAO.leggiCorse(dbm);
        
        return corse;
    } 

    /*public static void inserisciCorsa(String portoDiPartenza, String portoDiArrivo, 
        int orarioDiPartenza, int orarioDiArrivo, int prezzo, Date data ,idCompagnia) {
        DBManager dbm = DBManager.getInstance();
        int idNave = //leggi qualcosa
        CorsaDAO.creaCorsa(dbm, orarioDiArrivo, orarioDiPartenza, portoDiArrivo, portoDiPartenza, idNave, prezzo,data,idCompagnia);

    }*/

   /* public static void aggiornaCorsa(int idCorsa) {
        DBManager dbm = DBManager.getInstance();
        Scanner in = new Scanner(System.in);
        eliminaCorsa(idCorsa);
        //SistemaDirigentiGestione.visualizzaCompanie();
        //System.out.print("Inserisci l'ID della compagnia da inserire: ");
        //Integer idCompagnia = Integer.parseInt(in.nextLine());
        System.out.print("Inserisci i dati della corsa.\nPorto di partenza: ");
        String portoDiPartenza = in.nextLine();
        System.out.println("Orario di partenza: ");
        Integer orarioDiPartenza = Integer.parseInt(in.nextLine());
        System.out.print("Porto di arrivo: ");
        String portoDiArrivo = in.nextLine();
        System.out.print("Orario di arrivo: ");
        Integer orarioDiArrivo = Integer.parseInt(in.nextLine());
        System.out.print("Data di partenza [dd-MM-yyyy]: ");
        String datas=in.nextLine();
        System.out.print("Prezzo: ");
        Integer prezzo = Integer.parseInt(in.nextLine());
        try {
    	java.util.Date dataJava = new SimpleDateFormat("dd-MM-yyyy").parse(datas);
		Date dataSQL = new Date(dataJava.getTime());
        inserisciCorsa(portoDiPartenza, portoDiArrivo, orarioDiPartenza, orarioDiArrivo, prezzo, dataSQL/*, idCompagnia);
            
        } catch (ParseException e) {
            System.err.println("Errore inserimentod data.");
        }
        in.close();
    } */
}