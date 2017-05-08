package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 2105403 on 2/20/17.
 */

public class ListaDeMercado implements Serializable {

    private Date fechaCreacion;
    private boolean revisado;
    private List<ItemLista> items;
    private int id;
    private String nombre;
    private Usuario usuario;
    private int idUsuario;

    public ListaDeMercado(){}

    public ListaDeMercado(Date fechaCreacion, boolean revisado, int id){
        this.fechaCreacion=fechaCreacion;
        this.revisado=revisado;
        this.id=id;
        //items=new ArrayList<ItemLista>();
    }

    public ListaDeMercado(int lmi){
        this.setId(lmi);
        this.fechaCreacion=new Date();
        this.revisado=false;
    }

    public ListaDeMercado(String nombre){
        this.nombre=nombre;
        fechaCreacion=new Date();
        revisado=false;
    }

    /**
     * Agrega items a la lista de mercado
     * @param ite
     */
    public void agregarProducto(ItemLista ite){
        items.add(ite);
    }

    /**
     * Marca items como comprados
     * @param id
     */
   /* public void marcarProductoComprado(long id){
        for(ItemLista i: items){
            if(i.getId().getItem().getId().getProducto().getId()==id){
                i.setComprado(true);
            }
        }
    }*/

    /**
     * Marca items como favoritos
     */
    /*public void marcarProductoFavorito(long id){
        for(ItemLista i: items){
            if(i.getId().getItem().getId().getProducto().getId()==id){
                i.setFavorito(true);
            }
        }
    }*/


    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }

    public List<ItemLista> getItems() {
        return items;
    }

    public void setItems(List<ItemLista> items) {
        this.items = items;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}