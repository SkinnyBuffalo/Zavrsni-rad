package com.zavrsni.evidencijastudenata.Krevet;

public class Krevet {
    private int idKreveta;
    private int brojKreveta;
    private int idSobe;
    private boolean zauzetost;

    public int getIdKreveta() {
        return idKreveta;
    }

    public void setIdKreveta(int idKreveta) {
        this.idKreveta = idKreveta;
    }

    public int getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(int brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public int getIdSobe() {
        return idSobe;
    }

    public void setIdSobe(int idSobe) {
        this.idSobe = idSobe;
    }

    public boolean isZauzetost() {
        return zauzetost;
    }

    public void setZauzetost(boolean zauzetost) {
        this.zauzetost = zauzetost;
    }
}
