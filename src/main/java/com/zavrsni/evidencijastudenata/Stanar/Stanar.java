package com.zavrsni.evidencijastudenata.Stanar;

import java.time.LocalDate;


public class Stanar {
    private long oib;
    private long jmbag;
    private String ime;
    private String prezime;
    private LocalDate datumRodenja;
    private String adresaPrebivalista;
    private boolean subvencioniranost;
    private String uciliste;
    private boolean uplataTeretane;
    private String komentar;

    public long getOib() {
        return oib;
    }

    public void setOib(long oib) {
        this.oib = oib;
    }

    public long getJmbag() {
        return jmbag;
    }

    public void setJmbag(long jmbag) {
        this.jmbag = jmbag;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public String getAdresaPrebivalista() {
        return adresaPrebivalista;
    }

    public void setAdresaPrebivalista(String adresaPrebivalista) {
        this.adresaPrebivalista = adresaPrebivalista;
    }

    public boolean isSubvencioniranost() {
        return subvencioniranost;
    }

    public void setSubvencioniranost(boolean subvencioniranost) {
        this.subvencioniranost = subvencioniranost;
    }

    public String getUciliste() {
        return uciliste;
    }

    public void setUciliste(String uciliste) {
        this.uciliste = uciliste;
    }

    public boolean isUplataTeretane() {
        return uplataTeretane;
    }

    public void setUplataTeretane(boolean uplataTeretane) {
        this.uplataTeretane = uplataTeretane;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
