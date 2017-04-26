package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by 2105684 on 3/17/17.
 */

public class ItemListaId implements Serializable {

    private String tiendaNit;

    private double tiendaX;

    private double tiendaY;

    private long productoId;

    private String listaNombre;

    private String listaCorreo;

    public ItemListaId(){}

    public ItemListaId(Item item, ListaDeMercado lista){

    }

    public ItemListaId(String listaCorreo,String listaNombre,String tiendaNit,double tiendaX,double tiendaY, long productoId){
        this.listaCorreo=listaCorreo;
        this.listaNombre=listaNombre;
        this.tiendaNit=tiendaNit;
        this.tiendaX=tiendaX;
        this.tiendaY=tiendaY;
        this.productoId=productoId;
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

    public long getProductoId() {
        return productoId;
    }

    public void setProductoId(long productoId) {
        this.productoId = productoId;
    }

    public String getListaNombre() {
        return listaNombre;
    }

    public void setListaNombre(String listaNombre) {
        this.listaNombre = listaNombre;
    }

    public String getListaCorreo() {
        return listaCorreo;
    }

    public void setListaCorreo(String listaCorreo) {
        this.listaCorreo = listaCorreo;
    }

}