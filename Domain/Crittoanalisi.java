package Domain;

/**
 *
 * Created by Davide on 02/06/2016.
 */
public class Crittoanalisi {

    private Message m;       //messaggio 1
    private Message mStar;   //messaggio 2
    private Message mPrimo;  //XOR tra messaggio 1 e messaggio 2

    private int Sinput_S1;  //somma xor degli input della S-Boxes 1
    private int Sinput_S2;  //somma xor degli input della S-Boxes 2
    private int Soutput_S1; //somma xor degli output della S-Boxes 1
    private int Soutput_S2; //somma xor degli output della S-Boxes 2

    private int primiL4;
    private int ultimiL4;

    private int S1[][]={{5, 2, 1, 6, 3, 4, 7, 0 },{1, 4, 6, 2, 0, 7, 5, 3}};
    private int S2[][]={{4, 0, 6, 5, 7, 1, 3, 2 },{5, 3, 0, 7, 6, 2, 1, 4}};

    private int coppiePrimi1[];
    private int coppieSecondi1[];
    private int coppiePrimi2[];
    private int coppieSecondi2[];

    private int messaggiGenerati[]; //array di messaggi generati


    /**
     * La funzione A3Round è la funzione principale della classe Crittoanalisi ed è quella che
     * richiama direttamente o indirettamente tutte le altre. Prende in input un messaggio di 12 bit
     * e una chiave a 9 bit che viene utilizzata esclusivamente per calcolare l'output della criptazione
     * a 3 round. Dal messaggio iniziale vengono genereati casualmenti altri messaggi che hanno però la caratterisctica
     * di avere gli ultimi sei bit in comune con il messaggio 'messaggio1'.
     * La funzione ritorna il valore della chiave individuata attraverso la crittoanailisi
     * differenziale a 3 round e se funziona corrattamente ritorna un valore pari al parametro chiave
     * passato inizialmente.
     *
     *
     * @param messaggio1 messaggio in chiaro
     * @param chiave utilizzata per il calcolo dei messaggi cifrati
     * @return il valore della chiave individuato dalla crittoanalisi
     */
    public int A3Round(int messaggio1, int chiave){
        Utility U=new Utility();

        // La seguente linea di codice calcola K1 ossia la chiave effettiva in input per la cripazione a 3 round
        chiave=(U.ultimi(8,chiave)<<1)+U.ultimi(1,(chiave>>8));

        int Runo=U.ultimi(6,messaggio1);
        int messaggio2=(U.generaL1Casuale()<<6)+Runo;   //generazione di un messaggio casuale
        int messaggio3=(U.generaL1Casuale()<<6)+Runo;   //generazione di un messaggio casuale

        this.messaggiGenerati=new int[2];       //immagazzinamento dei messaggi casuali generati
        this.messaggiGenerati[0]=messaggio2;
        this.messaggiGenerati[1]=messaggio3;



        //La seguente funzione prepara tutte le componenti necessagie per la crittoanalisi con i messaggi 1 e 2
        this.preparaMessaggi(messaggio1, messaggio2, chiave);

        // La seguente funzione calcola tutte le possibili coppie per i primi e gli ultimi 4 bit di K4 con i messaggi 1 e 2
        // salvandole negli array coppiePrimi1 e coppieSecondi1
        this.cercaChiave(1);




        // La seguente funzione prepara tutte le componenti necessagie per la crittoanalisi con i messaggi 2 e 3
        this.preparaMessaggi(messaggio3, messaggio2, chiave);

        // La seguente funzione calcola tutte le possibili coppie per i primi e gli ultimi 4 bit di K4 con i messaggi 2 e 3
        // salvandole negli array coppiePrimi2 e coppieSecondi2
        this.cercaChiave(2);




        // La seguente sezione di codice confronta le coppie possili per i primi 4 bit di K4 individuandoli
        // e salvandolo in primiK4
        int primiK4[]=new int[16];
        int contatorePrimi=0;
        for(int i=0; i<coppiePrimi1.length; i++){
            for(int j=0; j<coppiePrimi2.length; j++){
                if(coppiePrimi1[i]==coppiePrimi2[j]){
                    primiK4[contatorePrimi]=coppiePrimi1[i];
                    contatorePrimi++;
                }
            }
        }




        // La seguente sezione di codice confronta le coppie possili per gli ultimi 4 bit di K4 individuandoli
        // e salvandolo in ultimiK4
        int ultimiK4[]=new int[16];
        int contatoreSecondi=0;
        for(int i=0; i<coppieSecondi1.length; i++){
            for(int j=0; j<coppieSecondi2.length; j++){
                if(coppieSecondi1[i]==coppieSecondi2[j]){
                    ultimiK4[contatoreSecondi]=coppieSecondi1[i];
                    contatoreSecondi++;
                }
            }
        }


        // Invece di trovare la chiave per tentativi provando a utilizzare l'intersezione tra i probabili bit
        // tra la prima coppia di messaggi e la seconda coppia di messaggi, si può pensare di modificare il codice
        // andando a prendere un quarto messaggio, e se necessario poi un quinto e un sesto e così via, fino a che
        // l'intersezione tra i probabili primi 4 bit e gli ultimi 4 bit di K4 derivanti da tutte le coppie di messaggi
        // utilizzati sia uno. Questo vuol dire che si trova K4 non per tentativi ma utilizzando un numero maggiore
        // di messaggi.

        // La seguente porzione di codice prova ad aggiungere l'ultimo bit e a tentativi viene
        // individuato e ritornato il valore della chiave trovato
        int K=512;
        for(int f=0;f<contatorePrimi && K==512; f++) {
            for(int p=0; p<contatoreSecondi && K==512; p++){
                K=this.calcolaK(primiK4[f],ultimiK4[p]);
            }
        }

        return K;

    }


    /**
     * La funzine preparaMessaggi prende in input 2 messaggi 'primo' e 'secondo' e sfruttando
     * la chiave 'chiaveS' riesce a calcolare la somma XOR degli input di S1 e S2 e la
     * somma XOR degli output di S1 e S2 relativi a messaggi
     *
     * @param primo rappresenta il primo messaggio
     * @param secondo rappresenta il secondo messaggio
     * @param chiaveS rappresenta la chiave di criptazione
     */
    public void preparaMessaggi(int primo, int secondo, int chiaveS){
        int round=3;
        SDES des=new SDES();
        Utility U=new Utility();

        //settato m
        des.cripta(primo, chiaveS, round);
        this.m=new Message();
        this.m= des.getMessaggio();

        //settato m*
        des.cripta(secondo, chiaveS, round);
        this.mStar=new Message();
        this.mStar= des.getMessaggio();

        //settato m'
        this.mPrimo=new Message();
        this.mPrimo.setMessaggio((primo^secondo),round);
        for(int i=0; i<round; i++){
            this.mPrimo.setRisultato(i+1,(this.m.getRisultato(i+1)^this.mStar.getRisultato(i+1)));
            this.mPrimo.setR(i+1,(this.m.getR(i+1)^(this.mStar.getR(i+1))));
            this.mPrimo.setL(i+1,(this.m.getL(i+1)^(this.mStar.getL(i+1))));
        }


        //calcolo della somma degli input di S1 e S2
        int EL4Primo=des.espansione(this.mPrimo.getL(3));  //E(L4')
        this.Sinput_S2=U.ultimi(4,EL4Primo);
        this.Sinput_S1=U.ultimi(4,EL4Primo>>4);


        //calcolo della somma degli output di S1 e S2
        int t=(this.mPrimo.getR(3))^(this.mPrimo.getL(0)); // R4' XOR L1'
        this.Soutput_S2=U.ultimi(3, t);
        this.Soutput_S1=U.ultimi(3, t>>3);


        //calcola E(L4')
        int EL4=des.espansione(this.m.getL(3));  //E(L4)
        this.ultimiL4=U.ultimi(4,EL4);
        this.primiL4=U.ultimi(4,EL4>>4);

    }


    /**
     * Il metodo cercaChiave prende in input un intero il cui valore puo essere 1 o 2 che
     * specifica se sis tanno cercando le coppie di possibili valori di K4, dalla prima o dalla seconda
     * combinazione di messaggi.
     * Il metodo richiama la funzione cerca4Bit che si occupa di cercare effettivamente il valore dei (primi o
     * ultimi) 4 bit.
     *
     */
    public void cercaChiave(int k){
        //cerca i primi 4
        Utility U=new Utility();
        int coppia1[]=this.cerca4bit(this.Sinput_S1, this.Soutput_S1, 1);

        //cerca gli ultimi 4
        int coppia2[]=this.cerca4bit(this.Sinput_S2, this.Soutput_S2, 2);


        for(int i=0;i<coppia1.length; i++){
            coppia1[i]=coppia1[i]^this.primiL4;
        }

        for(int i=0;i<coppia2.length; i++) {
            coppia2[i]=coppia2[i]^this.ultimiL4;
        }

        if(k==1){
            this.coppiePrimi1=new int[coppia1.length];
            this.coppieSecondi1=new int[coppia2.length];
            this.coppiePrimi1=coppia1;
            this.coppieSecondi1=coppia2;
        }
        else{
            this.coppiePrimi2=new int[coppia1.length];
            this.coppieSecondi2=new int[coppia2.length];
            this.coppiePrimi2=coppia1;
            this.coppieSecondi2=coppia2;
        }


    }


    /**
     * Il metodo cercbit prende come input 'input' che corrisponde alla somma XOR
     * dell'input dell s-box 'Sboxes' e 'output' che corrisponde alla somma XOR dell'output
     * della s-box 'SBoxes'.
     * Ritorna come valore le coppie di bit trovati
     *
     * @param input somma XOR dell'input della s-box
     * @param output somma XOR dell'output della s-box
     * @param Sboxes s-box S1 o S2
     * @return
     */
    public int[] cerca4bit(int input, int output, int Sboxes){
        Utility U=new Utility();
        int coppia[];//=new int[2];
        int c=0;
        int S[][]=new int[2][8];

        if(Sboxes==1){S=this.S1;}
        else {S=this.S2;}
        int coppieInput[][]=new int[16][2];
        int coppieOutput[][]=new int[16][2];
        for(int i=0; i<16; i++){
            coppieInput[i][0]=i^input;  //sono tutte le coppie che danno come somma l'input della S-Boxes
            coppieInput[i][1]=i;


            int rigaS=U.ultimi(1,(coppieInput[i][0]>>3));
            int colonnaS=U.ultimi(3,coppieInput[i][0]);
            coppieOutput[i][0]=S[rigaS][colonnaS];  //sono tutte le coppie risulto delle S-Boxes delle
            //coppie di input

            rigaS=U.ultimi(1,(coppieInput[i][1]>>3));
            colonnaS=U.ultimi(3,coppieInput[i][1]);
            coppieOutput[i][1]=S[rigaS][colonnaS];

            if((coppieOutput[i][0]^coppieOutput[i][1])==output){
                c++;
            }
        }
        coppia=new int[c];
        int j=0;
        for(int i=0; i<16; i++){
            if((coppieOutput[i][0]^coppieOutput[i][1])==output){
                coppia[j]=coppieInput[i][0];
                j++;
            }
        }
        return coppia;
    }

    /**
     * Il metodo calcolaK prende in input 'primik4' e 'ultimik4' che corrispondono
     * ai primi e algi ultimi 4 bit di ka individuati. Attraverso due tentativi trova il bit
     * mancante per costruire la chiave finale.
     * Ritorna la chiave finale trovata p eventualmente un valore di controllo nel caso
     * qualunque sia il bit inserito la chiave finale calcolata è errata
     *
     * @param primik4 primi 4 bit di K
     * @param ultimik4 ultimi 4 bit di K4
     * @return ritorna la chiave finale trovata o un valore di controllo
     */
    public int calcolaK(int primik4, int ultimik4){
        int Kfinale=0;
        Utility U=new Utility();
        SDES des=new SDES();

        Kfinale=U.ultimi(2,ultimik4);
        Kfinale=(Kfinale<<7)+(primik4<<2)+(U.ultimi(2,ultimik4>>2));

        Kfinale=(U.ultimi(8,Kfinale)<<1)+U.ultimi(1,(Kfinale>>8));


        int criptato=this.m.getRisultato(3);
        des.cripta(m.getRisultato(0), Kfinale, 3);

        if(des.getMessaggio().getRisultato(3)!=criptato){
            Kfinale=Kfinale+128;        //128 in binario è 0100000000
            //non si aggiunge  0010000000 perchè lavorando
            //a tre round è come se si costrundo k3
        }


        des.cripta(m.getRisultato(0), Kfinale, 3);
        if(des.getMessaggio().getRisultato(3)!=criptato){
            Kfinale=512;    // 1000000000
        }
        else{
            //shift della chiave perchè si è partiti da k1 e non da k0
            int Kpiu=U.ultimi(1,Kfinale);
            Kpiu=(Kpiu<<8)+U.ultimi(8,Kfinale>>1);
            Kfinale=Kpiu;

        }

        return Kfinale;
    }


    /**
     * I seguenti metodi serevono solo per farsi restituire gli attributi della calssi Crittoanalisi
     */
    public int[] getMessaggiGenerati(){
        return this.messaggiGenerati;
    }

    public int[] getPrimi1(){
        return this.coppiePrimi1;
    }

    public int[] getSecondi1(){
        return this.coppieSecondi1;
    }

    public int[] getPrimi2(){
        return this.coppiePrimi2;
    }

    public int[] getSecondi2(){
        return this.coppieSecondi2;
    }

}
