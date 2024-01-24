package com.zavrsni.evidencijastudenata.Kvar;

import java.time.LocalDate;


public class Kvar {
    private long idKvara;
    private LocalDate datumPrijaveKvara;
    private String opisKvara;
    private boolean stanjeKvara;
    private long idSobe;

    public long getIdKvara() {
        return idKvara;
    }

    public void setIdKvara(long idKvara) {
        this.idKvara = idKvara;
    }

    public LocalDate getDatumPrijaveKvara() {
        return datumPrijaveKvara;
    }

    public void setDatumPrijaveKvara(LocalDate datumPrijaveKvara) {
        this.datumPrijaveKvara = datumPrijaveKvara;
    }

    public String getOpisKvara() {
        return opisKvara;
    }

    public void setOpisKvara(String opisKvara) {
        this.opisKvara = opisKvara;
    }

    public boolean isStanjeKvara() {
        return stanjeKvara;
    }

    public void setStanjeKvara(boolean stanjeKvara) {
        this.stanjeKvara = stanjeKvara;
    }

    public long getIdSobe() {
        return idSobe;
    }

    public void setIdSobe(long idSobe) {
        this.idSobe = idSobe;
    }
}
