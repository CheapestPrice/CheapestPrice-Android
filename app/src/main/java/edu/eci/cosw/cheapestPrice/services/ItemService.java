package edu.eci.cosw.cheapestPrice.services;

import java.io.InputStream;
import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Producto;
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
 * Created by daniela on 1/05/17.
 */

public interface ItemService {

    @GET("api/items/{id}")
    Call<List<Item>> getItemsByUser(@Path("id") int id);

    @GET("api/items/{id}/products")
    Call<List<Producto>> getProducts (@Path("id") int id);

    @GET("api/items/{id}/items/{idItem}")
    Call<Item> getItemById(@Path("id") int id, @Path("idItem") int idItem );

    @GET("api/items/{id}/shop/{shop}/items")
    Call<List<Item>> getItemsByShop (@Path("id") int id ,@Path("shop") int shop );

    @GET("api/items/{id}/items/category/{category}")
    Call<List<Item>> getItemsByCategory (@Path("id") int id, @Path("category") String category );

    @GET("/api/items/{id}/categories")
    Call<List<String>>getItemsCategories(@Path("id") int id);

    @GET("api/items/{id}/shop/{shop}/item/{idItem}")
    Call<Item> getItemByShop (@Path("id") int id ,@Path("shop") int shop,@Path("idItem") int idItem );

    @POST("api/items/{id}/shop/{shop}/item")
    Call<Void> postItem(@Path("id") int id , @Path("shop") int shop, @Body Item item);

    @DELETE("api/items/{id}/shop/{shop}/item/{idItem}")
    Call<Void> deleteItem(@Path("id") int id,@Path("shop") int shop, @Path("idItem") int idItem);

    @PUT("api/items/{id}/shop/{shop}/item/{idItem}")
    Call<Void> putItem(@Path("id") int id,@Path("shop") int shop, @Path("idItem") int idItem);
}
