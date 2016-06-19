package Domain;

import java.util.Random;

/**
 *
 * Created by Davide on 30/05/2016.
 */
public class Utility {


    /**
     *  La funzione ultimi(int k, int n) prende come paramentri du einteri k,n.
     *  Restituisci un intero contenente gli ultimi k bit dell'intero n
     *
     */
    public int ultimi(int k, int n){
        int ris;
        int t=0;
        for(int i=0; i<k; i++){
            t=t+((int)Math.pow(2,i));
        }
        ris=n&t;
        return ris;
    }

    public String stampa(int intero){
        return this.stampaBinario(intero,0);
    }

    public String stampa(int intero, int lunghezza){
        return this.stampaBinario(intero, lunghezza);
    }


    //la funzone stanpa il binario dell'intero dato in input

    /**
     *  La funzione stampaBinario prende in input due interi, 'intero' e 'lunghezza'
     *  Ritorna come valore una stringa contente l'intero scritto in binario, eventualmente
     *  con un padding di zeri fino a raggiungere un numero di caratteri pari a lunghezza
     *
     */
    public String stampaBinario(int intero, int lunghezza){
        String bin=" ";
        while( intero>0 ) {
            int resto = intero % 2;
            intero = intero / 2;
            bin = resto + bin;
        }
        //System.out.print(" ");
        int l=12;
        if(lunghezza!=0){
            l=lunghezza;
        }

        if((bin.length()-1)<l){
            int mancanti =l-(bin.length()-1);
            for(int i=0; i<mancanti;i++){
                bin="0"+bin;
            }
        }
        //System.out.println(bin );
        return bin;
    }


    /**
     *  Il metodo daStringaBinariaAIntero prende come paramentro una stringa di binari
     *  e ritorna come valore un intero corripondente al binario rappresentato dalla stringa
     */
    public int daStringaBinariaAIntero(String binario){
        int intero=0;
        char bit;
        for(int i=binario.length(); i>0; i--){
            bit=binario.charAt(i-1);
            if(bit=='0'){
                intero=intero+0;
            }
            if(bit=='1') {
                intero = intero + ((int)Math.pow(2,binario.length()-i));
            }
        }


        return intero;
    }

    /**
     *  Il metodo generaL1casuale sfruttando la classe java.util.Random genera in modo pseudocasuale
     *  un intero da zero a 63 che in binario corrisponde a un valore da 000000 a 111111
     */
    public int generaL1Casuale(){
        Random random = new Random();
        int risultato = random.nextInt(63);
        return risultato;
    }
}
