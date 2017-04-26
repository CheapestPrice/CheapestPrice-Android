package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by 2105684 on 3/17/17.
 */

public class ItemId implements Serializable{

    private String tiendaNit;
    private double tiendaX;
    private double tiendaY;
    private long productoId;

    public ItemId(){}

    public ItemId( long producto){
        //this.tiendaId=tienda;
        this.productoId=producto;
    }

    public ItemId(long productoId,double tiendaX,double tiendaY, String tiendaNit){
        this.productoId=productoId;
        this.tiendaX=tiendaX;
        this.tiendaY=tiendaY;
        this.tiendaNit=tiendaNit;
    }

    /*public TiendaId getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(TiendaId tienda) {
        this.tiendaId = tienda;
    }*/
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

    public void setProductoId(long producto) {
        this.productoId = producto;
    }

    /*@Override
    public boolean equals(Object o){
        ItemId oi=(ItemId) o;
        return tiendaId.equals(oi.getTiendaId()) && productoId.equals(oi.getProductoId());
    }

    @Override
    public String toString(){
        return "->Tienda: "+tienda+"\nProducto: "+producto+"\n";
    }*/
}
