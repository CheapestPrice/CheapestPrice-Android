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

    private HorarioId horarioId;

    private Tienda tienda;

    public Horario(int horaInicio, int minutosInicio, int horaFin, int minutoFin,Tienda tienda,String dia) {
        this.setHoraInicio(horaInicio);
        this.setMinutosInicio(minutosInicio);
        this.setHoraFin(horaFin);
        this.setMinutoFin(minutoFin);
        setTienda(tienda);
        setHorarioId(new HorarioId(dia,tienda.getId().getNit(),tienda.getId().getX(),tienda.getId().getY()));

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

    public HorarioId getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(HorarioId horarioId) {
        this.horarioId = horarioId;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}