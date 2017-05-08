package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import okhttp3.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by 2105403 on 5/2/17.
 */

public interface ShoppingListService {

    @GET("/api/usuarios/me/{id}")
    Call<Usuario> getUsuarioById(@Path("id") int id);


    @DELETE("/api/usuarios/{id}/lista/{listaId}/")
    Call<Void> deleteListaMercado(@Path("id") int id, @Path("listaId") int listaId);

    //Agregar lista de mercado nueva
    @POST("/api/usuarios/{id}/lista/")
    Call<Void> agregarListaMercado(@Path("id") int id,@Body ListaDeMercado listaDeMercado);

    //Marcar item de la lista de mercado como favorito
    @PUT("/api/usuarios/{id}/lista/{listaId}/item/{itemListaId}/favorite/{fav}")
    Call<ResponseBody> itemSeleccionadoFavorito(@Path("id") int id,@Path("listaId") int listaId,
                                            @Path("itemListaId") int itemListaId,@Path("fav") boolean fav);

    //Comprar item de la lista de mercado
    @PUT("api/usuarios/{id}/lista/{lId}/item/{ILId}/comprado/{comp}")
    Call<ResponseBody> itemSeleccionadoComprado(@Path("id") int id,@Path("lId") int lId,
                                            @Path("ILId") int ILId,@Path("comp") boolean comp);

    //Borra un item de la lista de mercado
    @DELETE("/api/usuarios/{id}/lista/{listaId}/item/{idItem}")
    Call<Void> borrarItemSeleccionado(@Path("id") int id,@Path("listaId") int listaId,
                                      @Path("idItem") int idItem);
}
