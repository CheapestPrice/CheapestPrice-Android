package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by amoto on 3/20/17.
 */

public class HorarioId implements Serializable {

    private String dia;

    private String tiendaNit;

    private double tiendaX;

    private double tiendaY;

    public HorarioId(){}

    public HorarioId(String dia, String nit, double x, double y){
        this.setDia(dia);
        this.setTiendaNit(nit);
        this.setTiendaX(x);
        this.setTiendaY(y);
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTiendaNit() {
        return tiendaNit;
    }

    public void setTiendaNit(String tiendaNit) {
        this.tiendaNit = tiendaNit;
    }

    public double getTiendaX() {
        return tiendaX;
    }

    public void setTiendaX(double tiendaX) {
        this.tiendaX = tiendaX;
    }

    public double getTiendaY() {
        return tiendaY;
    }

    public void setTiendaY(double tiendaY) {
        this.tiendaY = tiendaY;
    }
}
