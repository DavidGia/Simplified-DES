package Controller;

import Domain.Crittoanalisi;
import Domain.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Davide on 06/06/2016.
 */
public class AttaccaController implements Initializable {


    @FXML private TextField inputAttacco;
    @FXML private TextField chiaveAttacco;
    @FXML private  Button button2;
    @FXML private  Button button3;
    @FXML private Label outputAttacco;
    @FXML private Label labelOutput;


    /** Le seguenti variabili servono per mostrare risultati intermedi dell'attacco  */
    @FXML private Label messaggioInserito;
    @FXML private Label messaggioGenerato1;
    @FXML private Label messaggioGenerato2;
    @FXML private Pane labelPane;
    @FXML private ScrollPane scrollPrimi;
    @FXML private ScrollPane scrollUltimi;
    @FXML private Pane PanePrimi;
    @FXML private Pane PaneUltimi;


    @FXML private void button2Clicked(ActionEvent event){
        this.labelOutput.setVisible(true);
        this.outputAttacco.setVisible(true);
        this.labelPane.setVisible(true);
        this.scrollPrimi.setVisible(true);
        this.scrollUltimi.setVisible(true);

        String messaggioInChiaro=this.inputAttacco.getText();
        String chiave=this.chiaveAttacco.getText();

        Utility U=new Utility();

        int messaggio=U.daStringaBinariaAIntero(messaggioInChiaro);
        int key=U.daStringaBinariaAIntero(chiave);

        Crittoanalisi C=new Crittoanalisi();
        int chiaveTrovata=C.A3Round(messaggio, key);        //richiamo della funzione di attacco a 3 round Della
                                                            //classe Crittoanalisi

        String chiaveT=U.stampa(chiaveTrovata,9);
        this.outputAttacco.setText(chiaveT);


        /****** Il codice seguente serve per mostrare dei risultai intermedi dell'attacco */
        this.messaggioInserito.setText(U.stampa(messaggio,12));
        this.messaggioGenerato1.setText(U.stampa(C.getMessaggiGenerati()[0],12));
        this.messaggioGenerato2.setText(U.stampa(C.getMessaggiGenerati()[1],12));

        this.PanePrimi.getChildren().clear();
        this.PaneUltimi.getChildren().clear();

        int primi1[]=C.getPrimi1();
        int i=0;
        for (int elemento:primi1) {
            Label c= new Label();
            c.setText(U.stampa(elemento,4));
            c.setLayoutX(110);
            c.setLayoutY(47+(18*i));
            i++;
            this.PanePrimi.getChildren().add(c);
        }


        int primi2[]=C.getPrimi2();
        i=0;
        for (int elemento:primi2) {
            Label c= new Label();
            c.setText(U.stampa(elemento,4));
            c.setLayoutX(355);
            c.setLayoutY(47+(18*i));
            i++;
            this.PanePrimi.getChildren().add(c);
        }

        int ultimi1[]=C.getSecondi1();
        i=0;
        for (int elemento:ultimi1) {
            Label c= new Label();
            c.setText(U.stampa(elemento,4));
            c.setLayoutX(110);
            c.setLayoutY(47+(18*i));
            i++;
            this.PaneUltimi.getChildren().add(c);
        }


        int ultimi2[]=C.getSecondi2();
        i=0;
        for (int elemento:ultimi2) {
            Label c= new Label();
            c.setText(U.stampa(elemento,4));
            c.setLayoutX(355);
            c.setLayoutY(47+(18*i));
            i++;
            this.PaneUltimi.getChildren().add(c);
        }


    }

    @FXML private void button3Clicked(ActionEvent event){
        this.chiaveAttacco.setEditable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.chiaveAttacco.setText("100101011");
        this.chiaveAttacco.setEditable(false);
        this.labelOutput.setVisible(false);
        this.outputAttacco.setVisible(false);
        this.labelPane.setVisible(false);
        this.scrollPrimi.setVisible(false);
        this.scrollUltimi.setVisible(false);
    }
}
