package it.edu.iisgubbio.rubrica;

public class Contatto {
    public String nome;
    public String cognome;
    public String telefono;

    public Contatto(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }
    
    public String getTipo() {
        return "BASE";
    }

    public String toString() {
        return nome + " " + cognome + " - " + telefono;
    }

    // SOTTOCLASSE PERSONALE (STATIC!)
    public static class ContattoPersonale extends Contatto {
        public String email;

        public ContattoPersonale(String nome, String cognome, String telefono, String email) {
            super(nome, cognome, telefono);
            this.email = email;
        }

        public String getTipo() {
            return "PERSONALE";
        }

        public String toString() {
            return "[Personale] " + nome + " " + cognome + " - " + telefono + " - " + email;
        }
    }

    // SOTTOCLASSE LAVORO (STATIC!)
    public static class ContattoLavoro extends Contatto {
        public String azienda;

        public ContattoLavoro(String nome, String cognome, String telefono, String azienda) {
            super(nome, cognome, telefono);
            this.azienda = azienda;
        }

        public String getTipo() {
            return "LAVORO";
        }

        public String toString() {
            return "[Lavoro] " + nome + " " + cognome + " - " + telefono + " - " + azienda;
        }
    }
    
}