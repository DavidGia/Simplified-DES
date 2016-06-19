package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField input;

    @FXML
    private TextField output;

    @FXML
    private Label label;

    public void prova(ActionEvent event){
        if(input.getText().equals("ciao")){
            label.setText("ciao");
        }
        else{
            label.setText("no");
        }

    }




}


