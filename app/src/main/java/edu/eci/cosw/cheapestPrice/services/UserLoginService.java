package edu.eci.cosw.cheapestPrice.services;

/**
 * Created by 2105684 on 4/24/17.
 */
import java.security.Principal;

import edu.eci.cosw.cheapestPrice.entities.Account;
import edu.eci.cosw.cheapestPrice.entities.CuentaPass;
import edu.eci.cosw.cheapestPrice.login.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserLoginService {
    @POST("/api/login")
    Call<Account> doLogin(@Body CuentaPass cuenta);
}
