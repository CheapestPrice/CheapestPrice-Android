package edu.eci.cosw.cheapestPrice.services;

import edu.eci.cosw.cheapestPrice.entities.Tendero;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by amoto on 5/8/17.
 */

public interface UserService {
    @GET("/api/usuarios/{id}/tendero/{userId}")
    Call<Tendero> getTendero(@Path("id") int id,@Path("userId") int userId);
    @GET("/api/usuarios/me/{id}")
    Call<Usuario> getUsuario(@Path("id") int userId);
}
