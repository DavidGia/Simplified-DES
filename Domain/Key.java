package Domain;

/**
 *
 * Created by Davide on 30/05/2016.
 */
public class Key {

    private int chiave;
    private int sottochiavi[];

    /**
     *  Il metodo setChiave(int chiave) prende come paramentro un intero che rappresenta la chiave,
     *  imposta la chiave e calcola le sottochiavi per la criptazione o decriptazione a 4 round
     *  con una serie opportuna di shift
     *
     */
    public void setChiave(int chiave){
        Utility U=new Utility();
        this.chiave=chiave;
        this.sottochiavi=new int[4];
        this.sottochiavi[0]=U.ultimi(8,chiave>>1); // si prendono 8 bit saltando l'ultimo
        this.sottochiavi[1]=U.ultimi(8,chiave); // si prendono gli ultimi 8 bit

        this.sottochiavi[2]=U.ultimi(7,chiave);
        this.sottochiavi[2]=(this.sottochiavi[2]<<1)+(U.ultimi(1,chiave >> 8)); // si prendono gli ultimi 7 bit e il "primo" bit
        this.sottochiavi[3]=U.ultimi(6,chiave);
        this.sottochiavi[3]=(this.sottochiavi[3]<<2)+(U.ultimi(2,chiave>>7)); // si prendono gli ultimi 6 e i "primi" 2 bit

    }


    /**
     * Il metodo getChiave ritorna un intero che rappresenta il valore della chiave a 9 bit
     *
     */
    public int getChiave(){
        return this.chiave;
    }


    /**
     * Il metodo getChiave prende cime paramentro un intero i e ritorna come valore
     * un intero rappresentante la sottochiave i-esima di 8 bit
     *
     */
    public int getSottochiave(int i){
        return this.sottochiavi[i];
    }

}
