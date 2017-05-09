package edu.eci.cosw.cheapestPrice.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Opinion;
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

    final class UnixEpochDateTypeAdapter
            extends TypeAdapter<Date> {


        private UnixEpochDateTypeAdapter() {
        }



        @Override
        public Date read(final JsonReader in)
                throws IOException {
            // this is where the conversion is performed
            return new Date(in.nextLong());
        }

        @Override
        @SuppressWarnings("resource")
        public void write(final JsonWriter out, final Date value)
                throws IOException {
            // write back if necessary or throw UnsupportedOperationException
            out.value(value.getTime());
        }

    }

    public ShopRetrofitNetwork(){
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new UnixEpochDateTypeAdapter()).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create(gson) ).build();
        shopService= retrofit.create(ShopService.class );
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
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

    public void getOpinionesTienda(RequestCallback<List<Opinion>> requestCallback, int id, int shop){

        try {
            Call<List<Opinion>> call = shopService.getOpinionesTienda(id,shop);
            Response<List<Opinion>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }

    }
}
