package edu.eci.cosw.cheapestPrice.network;

import java.io.IOException;
import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.services.ShopService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paula on 05/05/2017.
 */

public class ShopRetrofitNetwork {

    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private ShopService shopService;

    public ShopRetrofitNetwork(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        shopService= retrofit.create(ShopService.class );
    }

    public void getShop(RequestCallback<Tienda> requestCallback, int id, int shopId){
        try
        {
            Call<Tienda> call = shopService.getShop(id,shopId);
            Response<Tienda> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }

    }

    public void modifyShop(Callback<Void> callback, int id, int shopId, Tienda tienda){
        Call<Void> call=shopService.modifyShop(id,shopId,tienda);
        call.enqueue(callback);
    }

    public void getOpinionesTienda(RequestCallback<List<Tienda>> requestCallback, int id, int shop){

        try {
            Call<List<Tienda>> call = shopService.getOpinionesTienda(id,shop);
            Response<List<Tienda>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }

    }
}
