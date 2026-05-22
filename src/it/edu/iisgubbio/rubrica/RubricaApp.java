package it.edu.iisgubbio.rubrica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import it.edu.iisgubbio.rubrica.Contatto.ContattoPersonale;
import it.edu.iisgubbio.rubrica.Contatto.ContattoLavoro;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RubricaApp extends Application {

    Label lNome = new Label("Nome");
    Label lCognome = new Label("Cognome");
    Label lTelefono = new Label("Telefono");
    Label lExtra = new Label("Email/Azienda");
    Label lTipo = new Label("Tipo");
    Label lCerca = new Label("Cerca");

    TextField tfNome = new TextField();
    TextField tfCognome = new TextField();
    TextField tfTelefono = new TextField();
    TextField tfExtra = new TextField();
    TextField tfRicerca = new TextField();

    RadioButton rbPersonale = new RadioButton("Personale");
    RadioButton rbLavoro = new RadioButton("Lavoro");
    ToggleGroup gruppo = new ToggleGroup();

    Button bAggiungi = new Button("Aggiungi");
    Button bFiltra = new Button("Filtra");
    Button bPulisci = new Button("Pulisci");
    Button bRipristina = new Button("Ripristina");

    ListView<Contatto> lvContatti = new ListView<>();

    ArrayList<Contatto> elenco = new ArrayList<>();

    String percorsoFile = "/home/capannelli/Scrivania/progetto javafx/rubrica.csv";

    @Override
    public void start(Stage finestra) throws Exception {

        // ---------------------------
        // LETTURA FILE CSV
        // ---------------------------
        try {
            FileReader fr = new FileReader(percorsoFile);
            BufferedReader br = new BufferedReader(fr);

            String riga;

            while ((riga = br.readLine()) != null) {

                if (riga.trim().isEmpty()) continue;

                String[] campi = riga.split(";");

                if (campi.length < 4) continue;

                String tipo = campi[0];
                String nome = campi[1];
                String cognome = campi[2];
                String telefono = campi[3];

                String extra = "";
                if (campi.length >= 5) {
                    extra = campi[4];
                }

                Contatto c;

                if (tipo.equals("PERSONALE")) {
                    c = new ContattoPersonale(nome, cognome, telefono, extra);
                } else {
                    c = new ContattoLavoro(nome, cognome, telefono, extra);
                }

                elenco.add(c);
                lvContatti.getItems().add(c);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("File non trovato, ne verrà creato uno nuovo.");
        }

        // ---------------------------
        // IMPOSTAZIONE GUI
        // ---------------------------
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
        griglia.add(lCerca, 0, 7);
        griglia.add(tfRicerca, 1, 7);
        griglia.add(bFiltra, 0, 8, 2, 1);
        griglia.add(bPulisci, 0, 9, 2, 1);
        griglia.add(bRipristina, 0, 10, 2, 1);
        griglia.add(lvContatti, 3, 0, 5, 11);
        // ---------------------------
        // EVENTI BOTTONI
        // ---------------------------
        bAggiungi.setOnAction(e -> aggiungi());
        bFiltra.setOnAction(e -> filtra());
        bPulisci.setOnAction(e -> pulisci());
        bRipristina.setOnAction(e -> ripristina());
        Scene scena = new Scene(griglia,800,600);
        // COLLEGAMENTO CSS stile iPhone
        scena.getStylesheets().add("it/edu/iisgubbio/rubrica/stile.css");
        finestra.setScene(scena);
        finestra.setTitle("Rubrica Semplice");
        finestra.show();
    }
    // ---------------------------
    // AGGIUNTA CONTATTO + SALVATAGGIO
    // ---------------------------
    void aggiungi() {
        String nome = tfNome.getText();
        String cognome = tfCognome.getText();
        String telefono = tfTelefono.getText();
        String extra = tfExtra.getText();
        if (nome.isBlank() || cognome.isBlank() || telefono.isBlank() || extra.isBlank()) return;
        Contatto nuovo;
        if (rbPersonale.isSelected()) {
            nuovo = new ContattoPersonale(nome, cognome, telefono, extra);
        } else {
            nuovo = new ContattoLavoro(nome, cognome, telefono, extra);
        }
        elenco.add(nuovo);
        lvContatti.getItems().add(nuovo);

        try {
            FileWriter fw = new FileWriter(percorsoFile, true);
            fw.write(nuovo.getTipo() + ";" + nome + ";" + cognome + ";" + telefono + ";" + extra + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio.");
        }

        pulisci();
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

    void pulisci() {
        tfNome.clear();
        tfCognome.clear();
        tfTelefono.clear();
        tfExtra.clear();
        rbPersonale.setSelected(true);
    }

    void ripristina() {
        tfRicerca.clear();
        lvContatti.getItems().setAll(elenco);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
