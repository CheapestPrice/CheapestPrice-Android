package edu.eci.cosw.cheapestPrice.services;

import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Producto;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by daniela on 1/05/17.
 */

public interface ItemService {

    @GET("/item/")
    Call<List<Item>> getItems ();


    @GET("/products/")
    Call<List<Producto>> getProducts ();


}
