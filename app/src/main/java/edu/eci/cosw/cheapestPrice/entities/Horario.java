package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by masterhugo on 2/19/17.
 */

public class Horario implements Serializable{

    private int horaInicio;
    private int minutosInicio;
    private int horaFin;
    private int minutoFin;
    private int id;
    private String dia;
    private Tienda tienda;

    public Horario(int horaInicio, int minutosInicio, int horaFin, int minutoFin,Tienda tienda,String dia,int id) {
        this.setHoraInicio(horaInicio);
        this.setMinutosInicio(minutosInicio);
        this.setHoraFin(horaFin);
        this.setMinutoFin(minutoFin);
        setTienda(tienda);
        this.id=id;

    }

    public Horario() {
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }


    public int getMinutosInicio() {
        return minutosInicio;
    }

    public void setMinutosInicio(int minutosInicio) {
        this.minutosInicio = minutosInicio;
    }


    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }


    public int getMinutoFin() {
        return minutoFin;
    }

    public void setMinutoFin(int minutoFin) {
        this.minutoFin = minutoFin;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}