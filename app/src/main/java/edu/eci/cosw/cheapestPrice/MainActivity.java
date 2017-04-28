package edu.eci.cosw.cheapestPrice;

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

import java.security.Principal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    SwitchCompat select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        network = new RetrofitNetwork();
        select= (SwitchCompat) findViewById(R.id.selectUser);
        select.setTextOff("Cliente");
        select.setTextOn("Tendero");
        select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(select.isActivated());
                System.out.println(select.isChecked());
                if(isChecked){
                    select.setText(select.getTextOn());
                }else{
                    select.setText(select.getTextOff());
                }
            }
        });
        /*login.setOnClickListener(new View.OnClickListener() {
            código de angular que se debe "traducir"(btoa encripta en base-64):
            var headers = credentials ? {authorization: "Basic "
                            + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('user', {headers: headers}).then(successCallback, errorCallback);

                function successCallback(data){
                    if (data.data.name) {
                        $rootScope.authenticated = true;
                    }else {
                        $rootScope.authenticated = false;
                    }
                        callback && callback();
                }

                function errorCallback(error){
                    $rootScope.authenticated = false;
                     callback && callback();
                }
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //Se necesita una RetrofitNetwork, un RequestCallback, y un UserLoginService (creo) y buscar como convertir a base-64 para copiar el get que se hace en angular
                        byte[] encodedBytes = Base64.encode((email.getText().toString() + ":" + password.getText().toString()).getBytes(), Base64.DEFAULT);
                        String cred = new String(encodedBytes);
                        System.out.println(email.getText().toString() + ":" + password.getText().toString());
                        System.out.println(cred);
                        network.getPrincipal(new RequestCallback<Principal>() {
                            @Override
                            public void onSuccess(Principal response) {
                                System.out.println(response);
                            }

                            @Override
                            public void onFailed(NetworkException e) {
                                System.out.println("La tarea fallo exitosamente");
                                System.out.println(e);
                            }
                        },new User(new Headers("Basic "+cred)));
                    }
                });
            }
        });*/

    }
    public void logIn(View view){
        if(select.getText().equals(select.getTextOn())){
            Intent intent= new Intent(this,ProductActivity.class);
            startActivity(intent);
        }else{
            Intent intent= new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //Se necesita una RetrofitNetwork, un RequestCallback, y un UserLoginService (creo) y buscar como convertir a base-64 para copiar el get que se hace en angular
                byte[] encodedBytes = Base64.encode((email.getText().toString() + ":" + password.getText().toString()).getBytes(), Base64.DEFAULT);
                String cred = new String(encodedBytes);
                System.out.println(email.getText().toString() + ":" + password.getText().toString());
                System.out.println(cred);
                network.getPrincipal(new RequestCallback<Principal>() {
                    @Override
                    public void onSuccess(Principal response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println("La tarea fallo exitosamente");
                        System.out.println(e);
                    }
                },new User(new Headers("Basic "+cred)));
            }
        });
    }

    public void reg(View view){
        Intent intent= new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
