package edu.eci.cosw.cheapestPrice.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.services.ShoppingListService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
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

    /**
     * Obtener el usuario por el correo
     * @param requestCallback
     * @param id
     */
    public void getUsuarioById(RequestCallback<Usuario> requestCallback, int id){
        try{
            Call<Usuario> call= getUserService().getUsuarioById(id);
            Response<Usuario> response=call.execute();
            requestCallback.onSuccess( response.body() );
        } catch (IOException e) {
            requestCallback.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    /**
     * Eliminar la lista de mercado seleccionada
     * @param id
     * @param listaId
     */
    public void eliminarListaMercado(int id,int listaId){

        Call<Void> deleteShoppingList = getUserService().deleteListaMercado(id,listaId);
        deleteShoppingList.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response);
                System.out.println(call);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    /**
     * Selecciona un item de la lista de mercado como favorito
     * @param requestCall
     * @param id
     * @param itemListaId
     * @param listaId
     * @param fav
     */
    public void itemSeleccionadoFavorito(RequestCallback<ResponseBody> requestCall,int id,int listaId,int itemListaId,boolean fav) {
        Call<ResponseBody> call=getUserService().itemSeleccionadoFavorito(id,listaId,itemListaId,fav);
        try {
            Response<ResponseBody> response=call.execute();
            requestCall.onSuccess(response.body());
        } catch (IOException e) {
            requestCall.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    /**
     * Selecciona un item de la lista de mercado como comprado
     * @param requestCall
     * @param id
     * @param listaId
     * @param itemListaId
     * @param comp
     */
    public void itemSeleccionadoComprado(RequestCallback<ResponseBody> requestCall,int id,int listaId,int itemListaId,boolean comp) {
        Call<ResponseBody> call=getUserService().itemSeleccionadoComprado(id,listaId,itemListaId,comp);
        try {
            Response<ResponseBody> response=call.execute();
            requestCall.onSuccess(response.body());
        } catch (IOException e) {
            requestCall.onFailed( new NetworkException( 0, null, e ) );
        }
    }

    /**
     * Elimina un item seleccionado de la lista de mercado
     * @param requestCall
     * @param id
     * @param listaId
     * @param itemListaId
     */
    public void eliminarItemListaMercado(RequestCallback<Response> requestCall,int id,int listaId,int itemListaId){
        getUserService().borrarItemSeleccionado(id,listaId,itemListaId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Elimino el item de la lista");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    /**
     * Agrega una nueva lista de mercado de un usuario
     * @param id
     * @param listaDeMercado
     */
    public void agregarNuevaListaMercado(int id, ListaDeMercado listaDeMercado){
        System.out.println("1111111111111111111111111111111111111111111111111"+" "+id);
        Call<Void> call=getUserService().agregarListaMercado(id,listaDeMercado);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response);
                System.out.println(call);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    public ShoppingListService getUserService() {
        return userService;
    }

    public void setUserService(ShoppingListService userService) {
        this.userService = userService;
    }
}
