package ISBiglietteria.entities;


public class Traghetto extends Aliscafo {

    /*public Traghetto(Traghetto traghetto) {
        super(traghetto.getIdNave(), traghetto.getCapienzaPasseggeri(), traghetto.getCorsa());
        this.capienzaAutoveicoli = traghetto.capienzaAutoveicoli;
    }*/

    public Traghetto(int idNave, int capienzaPasseggeri, int capienzaAutoveicoli, Corsa corsa, int idCompagnia, String nomeCompagnia) {
        super(idNave, capienzaPasseggeri,corsa,idCompagnia,nomeCompagnia);
        this.capienzaAutoveicoli = capienzaAutoveicoli;
    }

    public int getCapienzaAutoveicoli() {
        return this.capienzaAutoveicoli;
    }
    
    public void setCapienzaAutoveicoli(int capienzaAutoveicoli) {
        this.capienzaAutoveicoli = capienzaAutoveicoli;
    }

    public Traghetto capienzaAutoveicoli(int capienzaAutoveicoli) {
        this.capienzaAutoveicoli = capienzaAutoveicoli;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
            " capienzaAutoveicoli='" + getCapienzaAutoveicoli() + "'" +
            "}";
    }
    
    private int capienzaAutoveicoli;//aggiunge rispetto ad aliscafo la capienza autoveicoli
}