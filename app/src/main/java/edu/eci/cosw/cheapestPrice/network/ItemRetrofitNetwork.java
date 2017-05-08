package edu.eci.cosw.cheapestPrice.network;

import java.io.IOException;
import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.services.ItemService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniela on 1/05/17.
 */

public class ItemRetrofitNetwork {

    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private ItemService itemService;


    public ItemRetrofitNetwork(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        itemService= retrofit.create(ItemService.class );
    }

    public void getItemsByShop( RequestCallback<List<Item>> requestCallback, int id, int shop)
    {
        try {
            Call<List<Item>> call = itemService.getItemsByShop(id,shop);
            Response<List<Item>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

   /* public void deleteItem( RequestCallback<Item> requestCallback)
    {
        try {
            Call<Response> call=itemService.deleteItem(id,shop,idItem);
            Response execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }*/

}
