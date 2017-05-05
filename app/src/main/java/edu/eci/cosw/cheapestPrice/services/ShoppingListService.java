package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 2105403 on 5/2/17.
 */

public interface ShoppingListService {

    @GET("/usuarios/{correo}")
    Call<Usuario> getUsuarioByCorreo(@Path("correo") String correo);

    @DELETE("/usuarios/{correo}/{listaNombre}")
    Call<ResponseBody> deleteListaMercado(@Path("correo") String correo, @Path("listaNombre") String listaNombre);
}
