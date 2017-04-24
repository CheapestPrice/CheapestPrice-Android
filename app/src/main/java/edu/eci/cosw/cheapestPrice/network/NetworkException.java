package edu.eci.cosw.cheapestPrice.network;

import java.io.IOException;

/**
 * Created by 2105684 on 4/24/17.
 */

public class NetworkException extends Exception {

    public NetworkException(){
        super();
    }

    public NetworkException(int i, Object o, IOException e) {
        super(e);
    }
}
