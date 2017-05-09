package edu.eci.cosw.cheapestPrice.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.entities.Producto;
import edu.eci.cosw.cheapestPrice.services.ItemService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniela on 1/05/17.
 */

public class ItemRetrofitNetwork {

    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private ItemService itemService;

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


    public ItemRetrofitNetwork(){
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new ItemRetrofitNetwork.UnixEpochDateTypeAdapter()).create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        itemService= retrofit.create(ItemService.class );
    }

    public void getItemsByUser( RequestCallback<List<Item>> requestCallback, int id)
    {
        try {
            Call<List<Item>> call = itemService.getItemsByUser(id);
            Response<List<Item>> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    public void getProducts( RequestCallback<List<Producto>> requestCallback, int id)
    {
        try {
            Call<List<Producto>> call = itemService.getProducts(id);
            Response<List<Producto>> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    public void  getItemById( RequestCallback<Item> requestCallback, int id,int idItem)
    {
        try {
            Call<Item> call = itemService.getItemById(id,idItem);
            Response<Item> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
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

    public void  getItemsByCategory( RequestCallback<List<Item>> requestCallback, int id,String category)
    {
        try {
            Call<List<Item>> call = itemService.getItemsByCategory(id,category);
            Response<List<Item>> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    public void  getCategories( RequestCallback<List<String>> requestCallback, int id)
    {
        try {
            Call<List<String>> call = itemService.getItemsCategories(id);
            Response<List<String>> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    public void getItemByShop( RequestCallback<Item> requestCallback, int id, int shop, int idItem)
    {
        try {
            Call<Item> call = itemService.getItemByShop(id,shop,idItem);
            Response<Item> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }
        catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

   public void deleteItem(Callback<Void> callback, int id, int shop,int idItem)
    {
            Call<Void> call=itemService.deleteItem(id,shop,idItem);
            call.enqueue(callback);
    }

    public void postItem(Callback<Void> callback,int id, int shop,int idItem)
    {
        Call<Void> call=itemService.postItem(id,shop,idItem);
        call.enqueue(callback);
    }

    public void putItem(Callback<Void> callback,int id, int shop, int idItem)
    {
        Call<Void> call=itemService.putItem(id,shop,idItem);
        call.enqueue(callback);
    }
}
