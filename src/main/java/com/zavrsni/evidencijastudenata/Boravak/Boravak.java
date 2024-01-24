package com.zavrsni.evidencijastudenata.Boravak;


import java.time.LocalDate;

public class Boravak {
    private int idBoravka;
    private int idKreveta;
    private long oib;
    private LocalDate datumUseljenja;
    private LocalDate datumIseljenja;

    public int getIdBoravka() {
        return idBoravka;
    }

    public void setIdBoravka(int idBoravka) {
        this.idBoravka = idBoravka;
    }

    public int getIdKreveta() {
        return idKreveta;
    }

    public void setIdKreveta(int idKreveta) {
        this.idKreveta = idKreveta;
    }

    public long getOib() {
        return oib;
    }

    public void setOib(long oib) {
        this.oib = oib;
    }

    public LocalDate getDatumUseljenja() {
        return datumUseljenja;
    }

    public void setDatumUseljenja(LocalDate datumUseljenja) {
        this.datumUseljenja = datumUseljenja;
    }

    public LocalDate getDatumIseljenja() {
        return datumIseljenja;
    }

    public void setDatumIseljenja(LocalDate datumIseljenja) {
        this.datumIseljenja = datumIseljenja;
    }
}
