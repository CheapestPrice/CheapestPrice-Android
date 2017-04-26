package edu.eci.cosw.cheapestPrice.entities;
import java.io.Serializable;

/**
 * Created by 2105403 on 2/20/17.
 */
public class ItemLista implements Serializable{

    private boolean comprado;

    private boolean favorito;

    private ItemListaId id;

    private Item item;

    private ListaDeMercado lista;

    public ItemLista(){

    }

    public ItemLista(ItemListaId id, boolean comprado, boolean favorito){
        this.comprado=comprado;
        this.favorito=favorito;
    }

    public ListaDeMercado getLista() {
        return lista;
    }

    public void setLista(ListaDeMercado lista) {
        this.lista = lista;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isComprado() {
        return comprado;
    }

    public boolean getComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public ItemListaId getId() {
        return id;
    }

    public void setId(ItemListaId id) {
        this.id = id;
    }
}
