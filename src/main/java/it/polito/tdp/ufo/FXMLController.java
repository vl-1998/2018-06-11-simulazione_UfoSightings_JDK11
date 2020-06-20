package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Avvistamenti;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Avvistamenti> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	String stato = boxStato.getValue();
    	if (stato==null) {
    		txtResult.appendText("Selezionare uno stato.");
    		return;
    	}
    
    	txtResult.appendText("Stati precedenti.\n");
    	if (this.model.precedenti(stato).isEmpty()) {
    		txtResult.appendText("Vertce isolato.");
    	} else {
    		txtResult.appendText(this.model.precedenti(stato)+"\n");
    	}
    	if (this.model.successori(stato).isEmpty()) {
    		txtResult.appendText("Vertce isolato.");
    	} else {
    		txtResult.appendText("\n\nStati successori. \n");
        	txtResult.appendText(this.model.successori(stato)+"\n");
    	}
    	if (this.model.raggiungibili(stato).isEmpty()) {
    		txtResult.appendText("Vertce isolato.");
    	} else {
    		txtResult.appendText("\n\nStati raggiungibili. \n");
        	txtResult.appendText(this.model.raggiungibili(stato)+"\n");
        	txtResult.appendText("\n\n# stati raggiungibili: "+this.model.visitaSize());
    	}
    	
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();
    	Avvistamenti a = boxAnno.getValue();
    	if (a==null) {
    		txtResult.appendText("Selezionare un anno.");
    		return;
    	}
    	this.model.creaGrafo(a);
    	this.boxStato.getItems().addAll(this.model.getVertex());
    	
    	txtResult.appendText("# vertici: "+this.model.getVertexNumber()+". # archi: "+this.model.getEdgeNumber());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	String stato = boxStato.getValue();
    	if (stato==null) {
    		txtResult.appendText("Selezionare uno stato.");
    		return;
    	}
    	
    	txtResult.appendText("BEST PERCORSO: \n");
    	txtResult.appendText(this.model.trovaAvvistamenti(stato)+"\n");
    	
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAnno());
	}
}
