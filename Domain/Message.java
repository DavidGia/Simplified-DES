package Domain;

/**
 *
 * Created by Davide on 30/05/2016.
 */
public class Message {


    private int R[];
    private int L[];
    private int risultato[];

    /**
     *  Il metodo setMessaggio(int m, int round) prende come parametri due interi che rappresentano
     *  rispettivamente il messaggioiniziale e il numero di round con il quale si sta operando.
     *  Imposta questi valori negli opportuni attributi
     *
     */
    public void setMessaggio(int m, int round){
        Utility U=new Utility();
        this.R=new int[round+1];
        this.L=new int[round+1];
        this.risultato=new int[round+1];
        this.risultato[0]=m;
        this.R[0]=U.ultimi(6,m);
        this.L[0]=U.ultimi(6,m>>6);
    }


    /**
     *  Il metodo setR(int i, int valore) prende come parametri due interi che rappresentano
     *  rispettivamente il round i-esimo e il valore Ri in modo da poter opportunamente settare gli attributi.
     *  In modo analogo lavorano i metodi setL(int i,int valore) e setRisultato(int i, int valore)
     *
     */
    public void setR(int i,int valore){
        this.R[i]=valore;
    }

    public void setL(int i,int valore){
        this.L[i]=valore;
    }

    public void setRisultato(int i, int valore){
        this.risultato[i]=valore;
    }


    /**
     *  Il metodo getR(int i) prende come parametro un intero che rappresentano
     *  il round i-esimo per il quale si vuole avere il valore Ri.
     *  In modo analogo lavorano i metodi getL(int i) e getRisultato(int i)
     *
     */
    public int getR(int i){
        return this.R[i];
    }

    public int getL(int i){
        return this.L[i];
    }

    public int getRisultato(int i){
        return this.risultato[i];
    }

}
