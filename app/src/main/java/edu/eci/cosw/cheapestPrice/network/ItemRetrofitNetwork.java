package edu.eci.cosw.cheapestPrice.network;

import edu.eci.cosw.cheapestPrice.services.ItemService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daniela on 1/05/17.
 */

public class ItemRetrofitNetwork {

    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private ItemService itemService;

    public ItemRetrofitNetwork(){
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        itemService= retrofit.create(ItemService.class );
    }



}
