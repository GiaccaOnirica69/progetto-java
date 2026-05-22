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

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + telefono + ")";
    }

    public static class ContattoPersonale extends Contatto {
        public String email;

        public ContattoPersonale(String nome, String cognome, String telefono, String email) {
            super(nome, cognome, telefono);
            this.email = email;
        }

        @Override
        public String getTipo() {
            return "PERSONALE";
        }

        @Override
        public String toString() {
            return nome + " " + cognome + " - " + telefono + " - " + email;
        }
    }

    public static class ContattoLavoro extends Contatto {
        public String azienda;

        public ContattoLavoro(String nome, String cognome, String telefono, String azienda) {
            super(nome, cognome, telefono);
            this.azienda = azienda;
        }

        @Override
        public String getTipo() {
            return "LAVORO";
        }

        @Override
        public String toString() {
            return nome + " " + cognome + " - " + telefono + " - " + azienda;
        }
    }
}