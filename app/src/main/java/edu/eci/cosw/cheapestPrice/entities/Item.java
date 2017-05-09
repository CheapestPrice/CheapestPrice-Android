package edu.eci.cosw.cheapestPrice.entities;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Julian David Devia Serna on 2/19/17.
 */
public class Item implements Serializable,Comparable<Item> {

    private int id;
    private long precio;
    private Tienda tienda;
    private Producto producto;

    public Item(){};

    public Item(int id){
        this.id=id;
    }

    public Item(Producto producto,Tienda tienda,long precio,int id){
        this.producto=producto;
        this.tienda=tienda;
        this.precio=precio;
        this.id=id;
    }

    public Item(Producto producto,Tienda tienda,int id){
        this.producto=producto;
        this.tienda=tienda;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "["+tienda.getNit()+ " "+ tienda.getX() + " " + tienda.getY() + " "+producto.getId() +" "+precio+"]";
    }

    @Override
    public int compareTo(@NonNull Item item) {
        if(getPrecio()<item.getPrecio()){
            return -1;
        }else{
            return 1;
        }
    }
}