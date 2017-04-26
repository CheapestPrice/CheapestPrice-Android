package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by 2105403 on 3/14/17.
 */

public class ListaMercado_Item implements Serializable {

    private String nombre;

    private String usuario;

    public ListaMercado_Item(){}

    public ListaMercado_Item(String nombre,String usuario){
        this.nombre=nombre;
        this.usuario=usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}