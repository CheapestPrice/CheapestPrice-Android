package edu.eci.cosw.cheapestPrice.login;

/**
 * Created by 2105684 on 4/24/17.
 */

public class Headers {
    private String authorization;
    public Headers(){

    }

    public Headers(String a){
        setAuthorization(a);
    }
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
