package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Producto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by daniela on 1/05/17.
 */

public interface ItemService {

    @GET("/items/")
    Call<List<Item>> getItems ();


    @GET("/items/products/")
    Call<List<Producto>> getProducts ();

    @DELETE("/shop/{shop}/id/{id}")
    Call<ResponseBody> deleteItem(@Path("shop") String correo, @Path("id") Long id);


}
