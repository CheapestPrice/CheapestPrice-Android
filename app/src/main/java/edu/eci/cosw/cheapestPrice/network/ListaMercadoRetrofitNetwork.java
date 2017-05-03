package edu.eci.cosw.cheapestPrice.network;

import java.io.IOException;

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

    public ListaMercadoRetrofitNetwork(){
        Retrofit retrofit= new Retrofit.Builder().baseUrl(getBaseUrl()).addConverterFactory( GsonConverterFactory.create() ).build();
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
