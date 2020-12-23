package ISBiglietteria.entities;
import ISBiglietteria.entities.ImpiegatoSingleton;

import java.sql.Date;
import java.util.ArrayList;

//E' un singleton, perchè per ogni applicazione in esecuzione è necessario 
//che sia possibile avere soltanto un istanza di DirigenteSingleton
public class DirigenteSingleton extends ImpiegatoSingleton {


    public static DirigenteSingleton getInstance(){
        if(DirigenteSingleton.single_instance == null){
            DirigenteSingleton.single_instance = new DirigenteSingleton();
        }
        return DirigenteSingleton.single_instance;

    }
    
    private static DirigenteSingleton single_instance;
    private ArrayList<Integer> idCorse = new ArrayList<Integer>();

}