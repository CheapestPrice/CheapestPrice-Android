package edu.eci.cosw.cheapestPrice.services;

import edu.eci.cosw.cheapestPrice.entities.Tienda;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Paula on 05/05/2017.
 */

public interface ShopService {

    @GET("/api/tiendas/{id}/shop/{shopId}")
    Call<Tienda> getShop(@Path("id") int id, @Path("shopId") int shopId);

    @PUT("/api/tiendas/{id}/shop/{shopId}")
    Call<Void> modifyShop(@Path("id") int id, @Path("shopId") int shopId, @Body Tienda tienda);
}
