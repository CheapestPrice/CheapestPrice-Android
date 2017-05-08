package edu.eci.cosw.cheapestPrice.network;

/**
 * Created by 2105684 on 4/24/17.
 */
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

import edu.eci.cosw.cheapestPrice.entities.Account;
import edu.eci.cosw.cheapestPrice.entities.Cuenta;
import edu.eci.cosw.cheapestPrice.entities.CuentaPass;
import edu.eci.cosw.cheapestPrice.entities.Tendero;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.login.User;
import edu.eci.cosw.cheapestPrice.services.UserLoginService;
import edu.eci.cosw.cheapestPrice.services.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitNetwork {
    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private UserLoginService loginService;
    private UserService userService;
    public RetrofitNetwork(){
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        loginService = retrofit.create( UserLoginService.class );
        userService = retrofit.create( UserService.class );
    }

    public void doLogin(RequestCallback<Account> requestCallback, CuentaPass cuenta){
        try{
            Call<Account> call=loginService.doLogin(cuenta);
            Response<Account> execute=call.execute();
            requestCallback.onSuccess( execute.body() );
        }catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }
    public void doCreateUser(Usuario usuario, Callback<Void> callback){
        Call<Void> call=loginService.doCreateUser(usuario);
        call.enqueue(callback);
    }
    public void doCreateTendero(Tendero tendero, Callback<Void> callback){
        Call<Void> call=loginService.doCreateTendero(tendero);
        call.enqueue(callback);
    }
    public void doCreateTienda(Tienda tienda, Callback<Void> callback){
        Call<Void> call=loginService.doCreateTienda(tienda);
        call.enqueue(callback);
    }
    public void doCreateCuenta(Cuenta cuenta, Callback<Void> callback){
        Call<Void> call=loginService.doCreateCuenta(cuenta);
        call.enqueue(callback);
    }


    public void getTendero(RequestCallback<Tendero> requestCallback, int id){
        try{
            Call<Tendero> call=userService.getTendero(id,id);
            Response<Tendero> execute=call.execute();
            requestCallback.onSuccess( execute.body() );
        }catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }
}
