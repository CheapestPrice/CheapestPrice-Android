package edu.eci.cosw.cheapestPrice.login;

/**
 * Created by 2105684 on 4/24/17.
 */

public class User {
    private Headers headers;
    public User(){}
    public User(Headers h){
        setHeaders(h);
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
}
