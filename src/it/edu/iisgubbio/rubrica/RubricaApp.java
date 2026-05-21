package it.edu.iisgubbio.rubrica;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	    TextField tfNomePersona = new TextField();
	    TextField tfCognome = new TextField();
	    TextField tfTelefono = new TextField();
	    TextField tfExtra = new TextField();
	    TextField tfRicerca = new TextField();

	    Button bAggiungi = new Button("Aggiungi");
	    Button bPulisci = new Button("Pulisci campi");
	    Button bFiltra = new Button("Filtra");
	    Button bRipristina = new Button("Ripristina ricerca");

	    RadioButton rbPersonale = new RadioButton("Personale");
	    RadioButton rbLavoro = new RadioButton("Lavoro");
	    ToggleGroup gruppo = new ToggleGroup();

	    ListView<Contatto> lvContatti = new ListView<>();

	    // -----------------------------
	    // DATI
	    // -----------------------------
	    ArrayList<Contatto> elenco = new ArrayList<>();
	    String percorsoFile = "rubrica.csv";

	    // -----------------------------
	    // AVVIO APPLICAZIONE
	    // -----------------------------
	    public void start(Stage finestra) {

	        // Carica i contatti dal file
	        elenco = caricaContatti();
	        lvContatti.getItems().addAll(elenco);

	        // RadioButton
	        rbPersonale.setToggleGroup(gruppo);
	        rbLavoro.setToggleGroup(gruppo);
	        rbPersonale.setSelected(true);

	        // Layout
	        GridPane griglia = new GridPane();
	        griglia.setPadding(new Insets(10));
	        griglia.setHgap(10);
	        griglia.setVgap(10);

	        griglia.add(lNome, 0, 0);
	        griglia.add(tfNomePersona, 1, 0);

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

	        griglia.add(lCerca, 0, 7);
	        griglia.add(tfRicerca, 1, 7);

	        griglia.add(bFiltra, 0, 8, 2, 1);
	        griglia.add(bPulisci, 0, 9, 2, 1);
	        griglia.add(bRipristina, 0, 10, 2, 1);

	        griglia.add(lvContatti, 3, 0, 1, 11);

	        // Bottoni
	        bAggiungi.setOnAction(e -> aggiungi());
	        bPulisci.setOnAction(e -> pulisciCampi());
	        bFiltra.setOnAction(e -> filtra());
	        bRipristina.setOnAction(e -> ripristina());

	        // Scene
	        Scene scena = new Scene(griglia);
	        scena.getStylesheets().add("it/edu/iisgubbio/rubrica/stile.css");

	        finestra.setScene(scena);
	        finestra.setTitle("Rubrica Telefonica");
	        finestra.show();
	    }

	    // -----------------------------
	    // CARICAMENTO FILE
	    // -----------------------------
	    ArrayList<Contatto> caricaContatti() {

	        ArrayList<Contatto> lista = new ArrayList<>();

	        try (BufferedReader lettore = new BufferedReader(new FileReader(percorsoFile))) {

	            String riga;

	            while ((riga = lettore.readLine()) != null) {

	                if (riga.trim().isEmpty()) continue;

	                String[] campi = riga.split(";");

	                String tipo = campi[0];
	                String nome = campi[1];
	                String cognome = campi[2];
	                String telefono = campi[3];
	                String extra = campi[4];

	                Contatto contatto;

	                if (tipo.equals("PERSONALE")) {
	                    contatto = new ContattoPersonale(nome, cognome, telefono, extra);
	                } else {
	                    contatto = new ContattoLavoro(nome, cognome, telefono, extra);
	                }

	                lista.add(contatto);
	            }

	        } catch (IOException e) {
	            // file non trovato → nessun errore
	        }

	        return lista;
	    }

	    // -----------------------------
	    // SALVATAGGIO FILE
	    // -----------------------------
	    void salvaContatto(Contatto c) {

	        try (FileWriter fw = new FileWriter("/home/capannelli/Scrivania/progetto javafx/rubrica.csv")) {

	            fw.write(c.getTipo() + ";" +
	                     c.nome + ";" +
	                     c.cognome + ";" +
	                     c.telefono + ";");

	            if (c instanceof ContattoPersonale) {
	                fw.write(((ContattoPersonale) c).email);
	            } else {
	                fw.write(((ContattoLavoro) c).azienda);
	            }

	            fw.write("\n");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    void pulisciCampi() {
	        tfNomePersona.clear();
	        tfCognome.clear();
	        tfTelefono.clear();
	        tfExtra.clear();
	        rbPersonale.setSelected(true);
	    }

	    void ripristina() {
	        tfRicerca.clear();
	        lvContatti.getItems().setAll(elenco);
	    }

	    void aggiungi() {

	        String nome = tfNomePersona.getText();
	        String cognome = tfCognome.getText();
	        String telefono = tfTelefono.getText();
	        String extra = tfExtra.getText();

	        Contatto nuovo;

	        if (rbPersonale.isSelected()) {
	            nuovo = new ContattoPersonale(nome, cognome, telefono, extra);
	        } else {
	            nuovo = new ContattoLavoro(nome, cognome, telefono, extra);
	        }

	        elenco.add(nuovo);
	        lvContatti.getItems().add(nuovo);
	        salvaContatto(nuovo);
	    }

	    void filtra() {

	        String testo = tfRicerca.getText().toLowerCase();

	        lvContatti.getItems().clear();

	        for (Contatto c : elenco) {
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