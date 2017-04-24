package edu.eci.cosw.cheapestPrice.network;

/**
 * Created by 2105684 on 4/24/17.
 */
import java.io.IOException;
import java.security.Principal;

import edu.eci.cosw.cheapestPrice.login.User;
import edu.eci.cosw.cheapestPrice.services.UserLoginService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetwork {
    private static final String BASE_URL = "https://cheapestprice.herokuapp.com/";
    private UserLoginService loginService;
    public RetrofitNetwork(){
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        loginService = retrofit.create( UserLoginService.class );
    }

    public void getPrincipal(RequestCallback<Principal> requestCallback, User user){
        try{
            Call<Principal> call=loginService.getPrincipal(user);
            Response<Principal> execute=call.execute();
            requestCallback.onSuccess( execute.body() );
        }catch ( IOException e )
        {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }
}
