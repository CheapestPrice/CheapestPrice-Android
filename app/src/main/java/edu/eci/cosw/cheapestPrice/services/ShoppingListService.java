package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 2105403 on 5/2/17.
 */

public interface ShoppingListService {

    @GET("/{correo:.+}")
    Call<Usuario> getUsuarioByCorreo(@Path("correo") String correo);
}
