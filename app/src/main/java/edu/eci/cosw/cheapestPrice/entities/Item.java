package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;

/**
 * Created by Julian David Devia Serna on 2/19/17.
 */

public class Item implements Serializable {

    private ItemId id;

    private long precio;

    private Tienda tienda;

    private Producto producto;

    public Item(){};

    public Item(ItemId id){
        this.id=id;
    }

    public Item(Producto producto,Tienda tienda,long precio){
        this.producto=producto;
        this.tienda=tienda;
        this.precio=precio;
        this.id=new ItemId(producto.getId(),tienda.getId().getX(),tienda.getId().getY(),tienda.getId().getNit());
    }

    public Item(Producto producto,Tienda tienda){
        this.producto=producto;
        this.tienda=tienda;
        this.id=new ItemId(producto.getId(),tienda.getId().getX(),tienda.getId().getY(),tienda.getId().getNit());
    }

    public ItemId getId() {
        return id;
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Item: "+id.getTiendaNit()+ " "+ id.getTiendaX() + " " + id.getTiendaY() + " "+id.getProductoId() +" "+precio;
    }
}