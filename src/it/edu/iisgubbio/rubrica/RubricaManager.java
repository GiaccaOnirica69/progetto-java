package it.edu.iisgubbio.rubrica;

import java.io.*;
import java.util.ArrayList;

public class RubricaManager {

    String file = "rubrica.csv";

    public ArrayList<Contatto> carica() {
        ArrayList<Contatto> lista = new ArrayList<>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String riga;
            while ((riga = br.readLine()) != null) {
                String[] c = riga.split(";");

                if (c[0].equals("PERSONALE")) {
                    lista.add(new Contatto.ContattoPersonale(c[1], c[2], c[3], c[4]));
                } else if (c[0].equals("LAVORO")) {
                    lista.add(new Contatto.ContattoLavoro(c[1], c[2], c[3], c[4]));
                }
            }

            br.close();
        } catch (Exception e) {
            // se il file non esiste, nessun errore
        }

        return lista;
    }

    public void salva(Contatto c) {
        try {
            FileWriter fw = new FileWriter(file, true);

            fw.write(c.getTipo() + ";" + c.nome + ";" + c.cognome + ";" + c.telefono);

            if (c instanceof Contatto.ContattoPersonale) {
                fw.write(";" + ((Contatto.ContattoPersonale) c).email);
            }

            if (c instanceof Contatto.ContattoLavoro) {
                fw.write(";" + ((Contatto.ContattoLavoro) c).azienda);
            }

            fw.write("\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
