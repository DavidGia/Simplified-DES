package Controller;

import Domain.SDES;
import Domain.Utility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Davide on 06/06/2016.
 */
public class CriptaController implements Initializable {


    @FXML private  Button button1;
    @FXML private ScrollPane scroll1;
    @FXML private Label labelOutput;
    @FXML private TextField outputCripta;

    @FXML private  TextField inputCripta;
    @FXML private  TextField keyCripta;

    /** Label con risultati intermedi**/
    @FXML private Label l0;
    @FXML private Label l1;
    @FXML private Label l2;
    @FXML private Label l3;
    @FXML private Label l4;
    @FXML private Label r0;
    @FXML private Label r1;
    @FXML private Label r2;
    @FXML private Label r3;
    @FXML private Label r4;
    @FXML private Label k1;
    @FXML private Label k2;
    @FXML private Label k3;
    @FXML private Label k4;


    @FXML private void button1Clicked(ActionEvent event){
        this.scroll1.setVisible(true);
        this.labelOutput.setVisible(true);
        this.outputCripta.setVisible(true);

        String messaggioInChiaro=this.inputCripta.getText();
        String chiave=this.keyCripta.getText();

        Utility U=new Utility();

        int messaggio=U.daStringaBinariaAIntero(messaggioInChiaro);
        int key=U.daStringaBinariaAIntero(chiave);

        SDES sdes = new SDES();
        sdes.cripta(messaggio, key, 4);
        String risultato=sdes.getStringaRisultato();
        this.outputCripta.setText(risultato);


        /**La seguente sezione di codice riempie i campi con i risultati intermedi
         * della criptazione del messaggio in chiaro**/

        this.l0.setText(U.stampa(sdes.getMessaggio().getL(0),6));
        this.l1.setText(U.stampa(sdes.getMessaggio().getL(1),6));
        this.l2.setText(U.stampa(sdes.getMessaggio().getL(2),6));
        this.l3.setText(U.stampa(sdes.getMessaggio().getL(3),6));
        this.l4.setText(U.stampa(sdes.getMessaggio().getL(4),6));

        this.r0.setText(U.stampa(sdes.getMessaggio().getR(0),6));
        this.r1.setText(U.stampa(sdes.getMessaggio().getR(1),6));
        this.r2.setText(U.stampa(sdes.getMessaggio().getR(2),6));
        this.r3.setText(U.stampa(sdes.getMessaggio().getR(3),6));
        this.r4.setText(U.stampa(sdes.getMessaggio().getR(4),6));

        this.k1.setText(U.stampa(sdes.getChiave().getSottochiave(0),8));
        this.k2.setText(U.stampa(sdes.getChiave().getSottochiave(1),8));
        this.k3.setText(U.stampa(sdes.getChiave().getSottochiave(2),8));
        this.k4.setText(U.stampa(sdes.getChiave().getSottochiave(3),8));


        /**********  la seguente sezione passa al controlled di decriptazione l'ultimo messaggio criptato */



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.scroll1.setVisible(false);
        this.labelOutput.setVisible(false);
        this.outputCripta.setVisible(false);

    }
}
