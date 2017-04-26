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

    private ListaMercado_Item listaid;

    private Usuario usuario;

    public ListaDeMercado(){}

    public ListaDeMercado(Date fechaCreacion, boolean revisado){
        this.fechaCreacion=fechaCreacion;
        this.revisado=revisado;
        //items=new ArrayList<ItemLista>();
    }

    public ListaDeMercado(ListaMercado_Item lmi){
        this.listaid=lmi;
        this.fechaCreacion=new Date();
        this.revisado=false;
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

    public ListaMercado_Item getListaid() {
        return listaid;
    }

    public void setListaid(ListaMercado_Item listaid) {
        this.listaid = listaid;
    }
}