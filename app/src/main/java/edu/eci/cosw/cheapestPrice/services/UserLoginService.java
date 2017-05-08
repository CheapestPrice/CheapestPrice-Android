package edu.eci.cosw.cheapestPrice.services;

/**
 * Created by 2105684 on 4/24/17.
 */
import java.security.Principal;

import edu.eci.cosw.cheapestPrice.entities.Account;
import edu.eci.cosw.cheapestPrice.entities.Cuenta;
import edu.eci.cosw.cheapestPrice.entities.CuentaPass;
import edu.eci.cosw.cheapestPrice.entities.Tendero;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.login.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserLoginService {
    @POST("/api/login")
    Call<Account> doLogin(@Body CuentaPass cuenta);
    @POST("/api/user/reg")
    Call<Void> doCreateUser(@Body Usuario usuario);
    @POST("/api/tendero/reg")
    Call<Void> doCreateTendero(@Body Tendero tendero);
    @POST("/api/tienda/reg")
    Call<Void> doCreateTienda(@Body Tienda tienda);
    @POST("/api/cuenta/reg")
    Call<Void> doCreateCuenta(@Body Cuenta cuenta);
}
