package edu.eci.cosw.cheapestPrice.network;

/**
 * Created by 2105684 on 4/24/17.
 */

public interface RequestCallback<T> {

    void onSuccess( T response );

    void onFailed( NetworkException e );
}
