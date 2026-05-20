package it.edu.iisgubbio.rubrica;
import java.util.ArrayList;

import it.edu.iisgubbio.rubrica.Contatto.ContattoLavoro;
import it.edu.iisgubbio.rubrica.Contatto.ContattoPersonale;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RubricaApp extends Application{
	 Label lNome = new Label("Nome");
	    Label lCognome = new Label("Cognome");
	    Label lTelefono = new Label("Telefono");
	    Label lExtra = new Label("Email/Azienda");
	    Label lTipo = new Label("Tipo");
	    Label lCerca = new Label("Cerca:");
	    Button bRipristina = new Button("Ripristina");
	    TextField tfNome = new TextField();
	    TextField tfCognome = new TextField();
	    TextField tfTelefono = new TextField();
	    TextField tfExtra = new TextField();
	    Button bPulisci = new Button("Pulisci campi");
	    RadioButton rbPersonale = new RadioButton("Personale");
	    RadioButton rbLavoro = new RadioButton("Lavoro");
	    ToggleGroup gruppo = new ToggleGroup();
	    Button bAggiungi = new Button("Aggiungi");
	    TextField tfRicerca = new TextField();
	    Button bFiltra = new Button("Filtra");
	    ListView<Contatto> lvContatti = new ListView<>();
	    RubricaManager manager = new RubricaManager();
	    ArrayList<Contatto> elenco = new ArrayList<>();

	    public void start(Stage finestra) throws Exception {
	        elenco = manager.carica();
	        lvContatti.getItems().addAll(elenco);
	        rbPersonale.setToggleGroup(gruppo);
	        rbLavoro.setToggleGroup(gruppo);
	        rbPersonale.setSelected(true);
	        GridPane griglia = new GridPane();
	        griglia.setPadding(new Insets(10));
	        griglia.setHgap(10);
	        griglia.setVgap(10);
	        griglia.add(lNome, 0, 0);
	        griglia.add(tfNome, 1, 0);
	        griglia.add(lCognome, 0, 1);
	        griglia.add(tfCognome, 1, 1);
	        griglia.add(lTelefono, 0, 2);
	        griglia.add(tfTelefono, 1, 2);
	        griglia.add(lExtra, 0, 3);
	        griglia.add(tfExtra, 1, 3);
	        griglia.add(lTipo, 0, 4);
	        griglia.add(rbPersonale, 1, 4);
	        griglia.add(rbLavoro, 1, 5);
	        griglia.add(bAggiungi, 0, 6, 2, 1);
	        griglia.add(bRipristina, 0, 10, 2, 1);
	        griglia.add(lCerca, 0, 7);	        
	        griglia.add(tfRicerca, 1, 8);
	        griglia.add(bFiltra, 0, 9, 2, 1);
	        griglia.add(bPulisci, 0, 7, 2, 1);
	        griglia.add(lvContatti, 3, 0, 1, 10);
	        bRipristina.setOnAction(e -> ripristina());
	        bPulisci.setOnAction(e -> pulisciCampi());
	        bAggiungi.setOnAction(e -> aggiungi());
	        bFiltra.setOnAction(e -> filtra());
			bFiltra.setMaxWidth(Integer.MAX_VALUE);	
			bPulisci.setMaxWidth(Integer.MAX_VALUE);	
			bAggiungi.setMaxWidth(Integer.MAX_VALUE);
			bRipristina.setMaxWidth(Integer.MAX_VALUE);
	        Scene scena = new Scene(griglia);
	        scena.getStylesheets().add("it/edu/iisgubbio/rubrica/stile.css");
	        finestra.setScene(scena);
	        finestra.setTitle("Rubrica Telefonica");
	        finestra.show();
	    }
	    void pulisciCampi() {
	        tfNome.clear();
	        tfCognome.clear();
	        tfTelefono.clear();
	        tfExtra.clear();
	        rbPersonale.setSelected(true);
	    }

	    void ripristina() {
	        lvContatti.getItems().clear();
	        lvContatti.getItems().addAll(elenco);
	    }

	    void aggiungi() {
	        String nome = tfNome.getText();
	        String cognome = tfCognome.getText();
	        String telefono = tfTelefono.getText();
	        String extra = tfExtra.getText();

	        Contatto c;

	        if (rbPersonale.isSelected()) {
	            c = new Contatto.ContattoPersonale(nome, cognome, telefono, extra);
	        } else {
	            c = new Contatto.ContattoLavoro(nome, cognome, telefono, extra);
	        }

	        elenco.add(c);
	        lvContatti.getItems().add(c);
	        manager.salva(c);
	    }


	    void filtra() {
	        String testo = tfRicerca.getText().toLowerCase();
	        lvContatti.getItems().clear();        
	        for (int z = 0; z < elenco.size(); z++) {
	            Contatto c = elenco.get(z);
	            if (c.nome.toLowerCase().contains(testo) ||
	                c.cognome.toLowerCase().contains(testo)) {
	                lvContatti.getItems().add(c);
	            }
	        }
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}