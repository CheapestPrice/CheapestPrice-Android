package edu.eci.cosw.cheapestPrice.entities;

import java.io.Serializable;


public class Cuenta implements Serializable{

    private String hash;
    private String email;
    private String rol;
    private boolean habilitado;

    public Cuenta(String email, String hash, String rol){
        this.email=email;
        this.hash=hash;
        this.rol=rol;
        this.habilitado=true;
    }

    public Cuenta(){ this.habilitado=true; }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getRol() { return rol;}

    public void setRol(String rol) { this.rol = rol;}

}
