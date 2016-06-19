package Domain;

/**
 *
 * Created by Davide on 28/05/2016.
 */

public class SDES {

    private Key key;
    private Message messaggio;


    private int S1[][]={{5, 2, 1, 6, 3, 4, 7, 0 },{1, 4, 6, 2, 0, 7, 5, 3}};
    private int S2[][]={{4, 0, 6, 5, 7, 1, 3, 2 },{5, 3, 0, 7, 6, 2, 1, 4}};



    /**
     *  La funzione cripta esegue l'algoritmo di cifrazione del DES semplificato
     *  sul testo in chiaro 'm', applicando la chiave a 9 bit 'k', a un numero di round pari a 'round'.
     *  Durante l'escuzione salva i valori di LiRi e Ki negli attributi messaggio di tipo Message
     *  e key di tipo Key
     *
     * @param m messaggio in chiaro
     * @param k chiave a 9 bit
     * @param round numero di round a cui cifrare
     */
    public void cripta(int m, int k, int round){

        this.key=new Key();
        this.messaggio=new Message();
        this.key.setChiave(k);
        this.messaggio.setMessaggio(m,round);

        for(int i=0; i<round; i++) {
            int RIt = this.messaggio.getR(i);

            // Nella seguente linea di codice vengono richiamate le funzioni espansione e Sboxes
            int f=this.sBoxes((this.espansione(this.messaggio.getR(i))) ^ this.key.getSottochiave(i));

            this.messaggio.setR(i+1,f^(this.messaggio.getL(i)));
            this.messaggio.setL(i+1,RIt);
            this.messaggio.setRisultato(i+1,(this.messaggio.getL(i+1)<<6)+this.messaggio.getR(i+1));
        }
    }



    /**
     *  La funzione decripta esegue l'algoritmo di decifrazione del DES semplificato
     *  sul testo criptato 'm', applicando la chiave a 9 bit 'k', a un numero di round pari a 'round'.
     *  Durante l'escuzione salva i valori di LiRi e Ki negli attributi messaggio di tipo Message
     *  e key di tipo Key
     *
     * @param m messaggio cifrato
     * @param k chiave a 9 bit
     * @param round numero di round con cui si opera
     * @return messaggio in chiaro
     */
    public int decripta(int m, int k, int round){

        this.key=new Key();
        this.messaggio=new Message();
        this.key.setChiave(k);
        Utility U=new Utility();


        // Nella seguente linea di codice viene scambiato l'ordine dell'input da L4R4 a R4L4
        m=((U.ultimi(6,m))<<6)+(U.ultimi(6,m>>6));
        this.messaggio.setMessaggio(m,round);

        for(int i=0; i<round; i++) {
            int RIt = this.messaggio.getR(i);

            // Nella seguente linea di codice vengono richiamate le funzioni espansione e Sboxes
            int f=this.sBoxes((this.espansione(this.messaggio.getR(i))) ^ this.key.getSottochiave(round-(i+1)));

            this.messaggio.setR(i+1,f^(this.messaggio.getL(i)));
            this.messaggio.setL(i+1,RIt);
            this.messaggio.setRisultato(i+1,(this.messaggio.getL(i+1)<<6)+this.messaggio.getR(i+1));
        }


        // Viene scambiato l'odine di L0 e R0 per poi ritornarlo come risultao
        int R0L0= this.messaggio.getRisultato(4);
        int messaggioInChiaro=((U.ultimi(6,R0L0))<<6)+(U.ultimi(6,R0L0>>6));
        return messaggioInChiaro;


    }


    /**
     * La funzione espansione prende il messaggio in input 'nesp' e lo espande da 6 a 8 bit
     * mettendondo i bit nell'ordine [1 2 4 3 4 3 5 6]
     *
     * @param nesp messaggio non ancora espanso
     * @return messaggio espanso
     */
    public int espansione(int nesp){
        int e[]=new int[6];
        int j=5;
        for(int i=0; i<6; i++){
            e[i]=(nesp>>j) & 1;
            j--;
        }

        int esp=e[0];
        esp=(esp<<1)+e[1];
        esp=(esp<<1)+e[3];
        esp=(esp<<1)+e[2];
        esp=(esp<<1)+e[3];
        esp=(esp<<1)+e[2];
        esp=(esp<<1)+e[4];
        esp=(esp<<1)+e[5];

        return esp;
    }



    /**
     * La funzione sBoxes prende come input un messaggio 'f'. Osservando gli ultimi 3 bit
     * e i primi 3 bit individua gli elementi nelle s-boxes opportune per poi concatenare il
     * risultato e restituirlo
     *
     * @param f messaggio in input alle s-box
     * @return concatenazione dei messaggi in output delle s-box
     */
    public int sBoxes(int f){
        Utility U=new Utility();
        int f1=U.ultimi(4,f>>4);  // (gli primi 4 bit)
        int f2=U.ultimi(4,f);  //(ultimi 4 bit))

        int rigaS1=U.ultimi(1,f1>>3);
        int colonnaS1=U.ultimi(3, f1);

        int rigaS2=U.ultimi(1,f2>>3);;
        int colonnaS2=U.ultimi(3, f2);;

        int fs=S1[rigaS1][colonnaS1];
        fs=(fs<<3)+S2[rigaS2][colonnaS2];
        return fs;
    }


    /**
     *  Le seguenti tre funzioni restituiscono semplicemnte gli attributi della classe
     */
    public Message getMessaggio(){
        return this.messaggio;
    }

    public Key getChiave(){
        return this.key;
    }

    public String getStringaRisultato(){
        Utility U=new Utility();
        return U.stampa(this.messaggio.getRisultato(4),12);
    }

}
