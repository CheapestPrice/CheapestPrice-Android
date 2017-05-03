package edu.eci.cosw.cheapestPrice.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.services.ShoppingListService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ListaMercadoRetrofitNetwork {

    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private ShoppingListService userService;

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

    public ListaMercadoRetrofitNetwork(){
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new UnixEpochDateTypeAdapter()).create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(getBaseUrl()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        setUserService(retrofit.create(ShoppingListService.class ));
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    //En construcción....
    public void getUsuarioByCorreo(RequestCallback<Usuario> requestCallback, String correo){
        try{
            Call<Usuario> call= getUserService().getUsuarioByCorreo(correo);
            Response<Usuario> response=call.execute();
            requestCallback.onSuccess( response.body() );
        } catch (IOException e) {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    public ShoppingListService getUserService() {
        return userService;
    }

    public void setUserService(ShoppingListService userService) {
        this.userService = userService;
    }
}
