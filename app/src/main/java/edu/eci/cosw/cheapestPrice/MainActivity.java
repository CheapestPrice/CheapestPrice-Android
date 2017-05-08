package edu.eci.cosw.cheapestPrice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.security.Principal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Account;
import edu.eci.cosw.cheapestPrice.entities.CuentaPass;
import edu.eci.cosw.cheapestPrice.login.Headers;
import edu.eci.cosw.cheapestPrice.login.User;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import edu.eci.cosw.cheapestPrice.network.RetrofitNetwork;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    Button register;
    RetrofitNetwork network;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        network = new RetrofitNetwork();
    }
    public void logIn(View view){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            Context cont;
            @Override
            public void run() {
                //Se necesita una RetrofitNetwork, un RequestCallback, y un UserLoginService (creo) y buscar como convertir a base-64 para copiar el get que se hace en angular
                String pass=Hashing.sha1().hashString(password.getText().toString(), Charsets.UTF_8).toString();
                String correo=email.getText().toString();
                System.out.println(correo+" | "+pass+" | "+password.getText().toString());
                CuentaPass acc= new CuentaPass(correo,pass);
                network.doLogin(new RequestCallback<Account>() {
                    @Override
                    public void onSuccess(Account response) {
                        System.out.println("success: "+response);
                        if(response.getRol().equals(Account.TENDERO)){
                            System.out.println("tendero "+response.getId());
                            Intent intent= new Intent(cont,ProductActivity.class);
                            Bundle b = new Bundle();
                            b.putSerializable("id",response.getId());
                            Intent start=intent.putExtra("bundle",b);
                            cont.startActivity(start);
                        }else{
                            System.out.println("cliente "+response.getId());
                            Intent intent= new Intent(cont,ShoppingListActivity.class);
                            Bundle b = new Bundle();
                            b.putSerializable("id",response.getId());
                            Intent start=intent.putExtra("bundle",b);
                            cont.startActivity(start);
                        }
                    }

                    @Override
                    public void onFailed(NetworkException e) {

                    }
                },acc);
            }
            public Runnable init(Context c){
                cont=c;
                return this;
            }
        }.init(this));

    }

    public void reg(View view){
        Intent intent= new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
