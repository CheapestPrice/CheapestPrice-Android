package edu.eci.cosw.cheapestPrice.entities;


import java.io.Serializable;

/**
 * Created by amoto on 5/4/17.
 */


public class Cuenta implements Serializable{

    private int id;
    private Usuario usuario;
    private String rol;
    private String hash;

    public Cuenta(int id,String hash, String rol,Usuario user){
        this.id=id;
        this.rol=rol;
        this.hash=hash;
        this.usuario=user;
    }

    public Cuenta(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRol() { return rol;}

    public void setRol(String rol) { this.rol = rol;}

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
