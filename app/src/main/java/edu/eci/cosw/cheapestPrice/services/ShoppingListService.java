package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 2105403 on 5/2/17.
 */

public interface ShoppingListService {

    @GET("/usuarios/{correo}")
    Call<Usuario> getUsuarioByCorreo(@Path("correo") String correo);

    @DELETE("/usuarios/{correo}/{listaNombre}")
    Call<Void> deleteListaMercado(@Path("correo") String correo, @Path("listaNombre") String listaNombre);

    //Comprar item de la lista de mercado
    @POST("/usuarios/favorito")
    Call<Void> itemSeleccionadoComprado();

    //Marcar item de la lista de mercado como favorito
    @POST("/usuarios/")
    Call<Void> itemSeleccionadoFavorito();

    //Borra un item de la lista de mercado
    @DELETE("/usuarios/{correo}")
    Call<Void> borrarItemSeleccionado();
}
