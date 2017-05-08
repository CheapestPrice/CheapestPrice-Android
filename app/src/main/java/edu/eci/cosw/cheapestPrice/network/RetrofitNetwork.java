package edu.eci.cosw.cheapestPrice.network;

/**
 * Created by 2105684 on 4/24/17.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
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


    public RetrofitNetwork(){
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new RetrofitNetwork.UnixEpochDateTypeAdapter()).create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
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
    public void getUsuario(RequestCallback<Usuario> requestCallback, int id){
        try {
            Call<Usuario> call = userService.getUsuario(id);
            Response<Usuario> execute = call.execute();
            requestCallback.onSuccess(execute.body());
        }catch (IOException e){
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
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
