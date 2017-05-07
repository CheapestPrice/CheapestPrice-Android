package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by Paula on 21/02/2017.
 */

public class Tendero implements Serializable{

    private Tienda tienda;
    private Usuario usuario;
    private int id;

    public Tendero(){}

    public Tendero(Usuario usuario,Tienda tienda,int id){
        this.setUsuario(usuario);
        this.setTienda(tienda);
        this.setId(id);
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}