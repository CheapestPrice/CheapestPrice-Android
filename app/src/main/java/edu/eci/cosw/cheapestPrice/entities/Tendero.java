package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

public class Tendero implements Serializable, Persona{


    protected String correo;

    protected String nombre;

    private Tienda tienda;

    public Tendero(){}

    public Tendero(String nombre,String correo, Tienda tienda){
        //super(nombre,correo);
        this.nombre = nombre;
        this.correo = correo;
        //this.tienda=tienda;
    }

    @Override
    public String getCorreo(){
        return correo;
    }

    @Override
    public String getNombre(){
        return nombre;
    }

    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

}