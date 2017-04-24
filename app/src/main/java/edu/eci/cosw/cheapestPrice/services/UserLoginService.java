package edu.eci.cosw.cheapestPrice.services;

/**
 * Created by 2105684 on 4/24/17.
 */
import java.security.Principal;

import edu.eci.cosw.cheapestPrice.login.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserLoginService {
    @GET("/app/user/{user}")
    Call<Principal> getPrincipal(@Path("user") User user);
}
