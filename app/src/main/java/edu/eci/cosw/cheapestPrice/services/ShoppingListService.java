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
    Call<ResponseBody> deleteListaMercado(@Path("correo") String correo, @Path("listaNombre") String listaNombre);


    //Marcar item de la lista de mercado como favorito
    @POST("/usuarios/favorito/{correo}/{listaNombre}/{productoId}/{nit}/{x}/{y}/{fav}")
    Call<ResponseBody> itemSeleccionadoFavorito(@Path("correo") String correo,@Path("listaNombre") String listaNombre,
                                            @Path("productoId") long productoId,@Path("nit") String nit,
                                            @Path("x") double x,@Path("y") double y,@Path("fav") boolean fav);

    //Comprar item de la lista de mercado
    @POST("/usuarios/{correo}/{listaNombre}/{productoId}/{nit}/{x}/{y}/{comp}")
    Call<ResponseBody> itemSeleccionadoComprado(@Path("correo") String correo,@Path("listaNombre") String listaNombre,
                                            @Path("productoId") long productoId,@Path("nit") String nit,
                                            @Path("x") double x,@Path("y") double y,@Path("comp") boolean comp);

    //Borra un item de la lista de mercado
    @DELETE("/usuarios/{correo}/{listaNombre}/{productoId}/{nit}/{x}/{y}")
    Call<Void> borrarItemSeleccionado(@Path("correo") String correo,@Path("listaNombre") String listaNombre,
                                      @Path("productoId") long productoId,@Path("nit") String nit,
                                      @Path("x") double x,@Path("y") double y);
}
