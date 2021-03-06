package edu.eci.cosw.cheapestPrice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Account;
import edu.eci.cosw.cheapestPrice.entities.Cuenta;
import edu.eci.cosw.cheapestPrice.entities.Tendero;
import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.entities.Usuario;
import edu.eci.cosw.cheapestPrice.network.RetrofitNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {
    RetrofitNetwork network;

    LinearLayout basic;
    LinearLayout tendero;
    LinearLayout mapa;
    ProgressDialog cargando;

    //Formulario básico
    EditText userName;
    EditText email;
    EditText passwd;
    EditText confirmPasswd;

    //Tendero:
    public static final String tenderostr="¿Desea registrar su tienda?";
    public static final String clientestr="¿Desea registrarse como cliente?";
    Button tiendaR;
    boolean isTendero;
    EditText shopName;
    EditText shopAddress;
    EditText shopPhone;
    EditText shopNit;

    //Mapa
    private static final int ACCESS_LOCATION_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    public String address;
    private Geocoder geocoder;
    private String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargando=new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        network = new RetrofitNetwork();
        userName= (EditText) findViewById(R.id.userName);
        email= (EditText) findViewById(R.id.userMail);
        passwd= (EditText) findViewById(R.id.passwd);
        confirmPasswd= (EditText) findViewById(R.id.passwdConfirm);
        basic= (LinearLayout) findViewById(R.id.basicForm);
        tendero= (LinearLayout) findViewById(R.id.extraForm);
        isTendero=tendero.getVisibility()==View.VISIBLE;
        shopAddress= (EditText) findViewById(R.id.shopAddress);
        shopName= (EditText) findViewById(R.id.shopName);
        shopPhone= (EditText) findViewById(R.id.shopPhone);
        shopNit= (EditText) findViewById(R.id.shopNit);
        mapa=(LinearLayout) findViewById(R.id.mapReg);
        tiendaR=(Button) findViewById(R.id.tiendaReg);

        //mas mapa
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        locationRequest = new LocationRequest();
        address = ((EditText) findViewById(R.id.shopAddress)).getText().toString();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        googleApiClient.connect();
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void reg(View v) {
        System.out.println(validForm());
        if(!validForm()) {
            if (userName.getText() == null || userName.getText().length() == 0) {
                userName.setError("Falta el nombre de usuario");
                System.out.println("Falta el nombre de usuario");
            }
            if (email.getText() == null || email.getText().length() == 0) {
                email.setError("Falta el correo electrónico");
                System.out.println("Falta el correo electrónico");
            }
            if (!isEmailValid(email.getText().toString())) {
                email.setError("Formato incorrecto del correo electrónico");
                System.out.println("Formato incorrecto del correo electrónico");
            }
            if (passwd.getText() == null || passwd.getText().length() == 0) {
                passwd.setError("Falta la contraseña");
                System.out.println("Falta la contraseña");
            }
            if (confirmPasswd.getText() == null || confirmPasswd.getText().length() == 0) {
                confirmPasswd.setError("Falta la confirmación de contraseña");
                System.out.println("Falta la confirmación de contraseña");
            }
            if (!passwd.getText().toString().equals(confirmPasswd.getText().toString())) {
                confirmPasswd.setError("Las contraseñas no coinciden");
                System.out.println("las contraseñas no son iguales");
            }
            if (isTendero) {
                if (shopAddress.getText() == null || shopAddress.getText().length() == 0) {
                    shopAddress.setError("La dirección de la tienda no puede estar vacía");
                    System.out.println("La dirección de la tienda no puede estar vacía");
                }
                if (shopName.getText() == null || shopName.getText().length() == 0) {
                    shopName.setError("El nombre de la tienda no puede estar vacío");
                    System.out.println("El nombre de la tienda no puede estar vacío");
                }
                if (shopNit.getText() == null || shopNit.getText().length() == 0) {
                    shopNit.setError("El NIT de la tienda no puede estar vacío");
                    System.out.println("El NIT de la tienda no puede estar vacío");
                }
                if (shopPhone.getText() == null || shopPhone.getText().length() == 0) {
                    shopPhone.setError("El telefono de la tienda no puede estar vacío");
                    System.out.println("El telefono de la tienda no puede estar vacío");
                }
                try {
                    System.out.println("----------------------->"+address);
                    System.out.println(geocoder.getFromLocationName(address+", Bogota", 1));
                    if(geocoder.getFromLocationName(address+", Bogota", 1).size()==0) {
                        shopAddress.setError("La dirección no es valida");
                        System.out.println("La direccion no es valida");
                    }

                } catch (IOException e) {
                    shopAddress.setError("La dirección no es valida");
                    System.out.println("La direccion no es valida");
                }
            }
        }else{
            System.out.println("validoooooo");
            if(!isTendero){
                registrarUsuario();
            }
            else{
                registrarTendero();
            }
            //alertDialog("Registro exitoso");
        }

    }

    public void alertDialog(String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(e)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validForm(){
        boolean ans=true;
        ans &= !(userName.getText()==null || userName.getText().length()==0) ;
        ans &= !(email.getText()==null || email.getText().length()==0) ;
        ans &= !(email.getText()==null || email.getText().length()==0) && isEmailValid(email.getText().toString());
        ans &= !(passwd.getText()==null || passwd.getText().length()==0);
        ans &= !(confirmPasswd.getText()==null || confirmPasswd.getText().length()==0);
        ans &= passwd.getText().toString().equals(confirmPasswd.getText().toString());
        ans &= ((isTendero == !(shopAddress.getText()==null || shopAddress.getText().length()==0)) || !isTendero);
        ans &= ((isTendero == !(shopName.getText()==null || shopName.getText().length()==0)) || !isTendero);
        ans &= ((isTendero == !(shopNit.getText()==null || shopNit.getText().length()==0)) || !isTendero);
        ans &= ((isTendero == !(shopPhone.getText()==null || shopPhone.getText().length()==0)) || !isTendero);
        try {
            if(geocoder.getFromLocationName(address+", Bogota", 1).size()==0) {
                ans=false;
            }
        } catch (IOException e) {
            ans=false;
        }
        return ans;
    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void tend(View view){
        isTendero=!isTendero;
        tendero.setVisibility(isTendero ? View.VISIBLE:View.GONE);
        tiendaR.setText(!isTendero ? tenderostr : clientestr);
    }
    public void cargar(){
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }
    public void registrarUsuario(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        cargar();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final Usuario u = new Usuario();
                u.setCorreo(email.getText().toString());
                u.setNombre(userName.getText().toString());
                network.doCreateUser(u, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("response: "+response+" call: "+call);
                        Cuenta c = new Cuenta();
                        c.setUsuario(u);
                        c.setHash(Hashing.sha1().hashString(passwd.getText().toString(), Charsets.UTF_8).toString());
                        c.setRol(Account.CLIENTE);
                        network.doCreateCuenta(c, new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("Cuenta: response: "+response+" call: "+call);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                        alertDialog("El cliente ha sido registrado exitosamente");
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Cuenta: failure: "+t+" call: "+call);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                        alertDialog("Ha ocurrido un error inesperado");
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("failure: "+t+" call: "+call);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                                alertDialog("Ha ocurrido un error inesperado");
                            }
                        });
                    }
                });
            }
        });
    }
    public void registrarTendero(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        cargar();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final Usuario u = new Usuario();
                u.setCorreo(email.getText().toString());
                u.setNombre(userName.getText().toString());
                network.doCreateUser(u, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("response: "+response+" call: "+call);
                        Cuenta c = new Cuenta();
                        c.setUsuario(u);
                        c.setHash(Hashing.sha1().hashString(passwd.getText().toString(), Charsets.UTF_8).toString());
                        c.setRol(Account.TENDERO);
                        network.doCreateCuenta(c, new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("Cuenta: response: "+response+" call: "+call);
                                final Tienda t = new Tienda();
                                t.setNit(shopNit.getText().toString());
                                t.setNombre(shopName.getText().toString());
                                t.setDireccion(shopAddress.getText().toString());
                                t.setDisponible(true);
                                t.setTelefono(shopPhone.getText().toString());
                                try {
                                    t.setY(geocoder.getFromLocationName(address + ", Bogota", 1).get(0).getLatitude());
                                    t.setX(geocoder.getFromLocationName(address + ", Bogota", 1).get(0).getLongitude());
                                }catch (IOException e){
                                    System.out.println(e);
                                    t.setY(0);
                                    t.setX(0);
                                }
                                network.doCreateTienda(t, new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        System.out.println("tienda: response: "+response+" call: "+call);
                                        Tendero te = new Tendero();
                                        te.setTienda(t);
                                        te.setUsuario(u);
                                        network.doCreateTendero(te, new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                System.out.println("tendero: response: "+response+" call: "+call);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        cargando.hide();
                                                        alertDialog("El tendero se registró exitosamente");
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                System.out.println("tendero: failure: "+t+" call: "+call);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        cargando.hide();
                                                        alertDialog("Ha ocurrido un error inesperado");
                                                    }
                                                });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        System.out.println("tienda: failure: "+t+" call: "+call);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                cargando.hide();
                                                alertDialog("Ha ocurrido un error inesperado");
                                            }
                                        });

                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Cuenta: failure: "+t+" call: "+call);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cargando.hide();
                                        alertDialog("Ha ocurrido un error inesperado");
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("failure: "+t+" call: "+call);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargando.hide();
                                alertDialog("Ha ocurrido un error inesperado");
                            }
                        });
                    }
                });
            }
        });
    }
    public void verMapa(View view){
        address=((EditText) findViewById(R.id.shopAddress)).getText().toString();
        if((address.trim().length()>0 && mapa.getVisibility()==View.GONE) || mapa.getVisibility()!=View.GONE) {
            mapa.setVisibility(mapa.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            if(mapa.getVisibility() != View.GONE) {
                setMarker();
            }
        }
    }


    //aun mas mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMarker();

    }

    public void setMarker() {
        try {
            System.out.println("----------------------->"+address);
            System.out.println(geocoder.getFromLocationName(address+", Bogota", 1));
            if(geocoder.getFromLocationName(address+", Bogota", 1).size()>0) {
                LatLng shop = new LatLng(geocoder.getFromLocationName(address+", Bogota", 1).get(0).getLatitude(), geocoder.getFromLocationName(address+", Bogota", 1).get(0).getLongitude());
                mMap.addMarker(new MarkerOptions().position(shop).title("Marker in Shop"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop,17));
            }else{
                shopAddress.setError("La dirección no es valida");
                System.out.println("La direccion no es valida");
            }
        } catch (IOException e) {
            e.printStackTrace();
            shopAddress.setError("La dirección no es valida");
            System.out.println("La direccion no es valida");
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!hasPermissions(this,permissions)) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        setMarker();
                        stopLocationUpdates();
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        startLocationUpdates();
    }
    private void startLocationUpdates() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, locationRequest,
                new LocationListener()
                {
                    @Override
                    public void onLocationChanged( Location location )
                    {
                        setMarker();
                        stopLocationUpdates();
                    }
                } );
    }
    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates( googleApiClient, new LocationListener()
        {
            @Override
            public void onLocationChanged( Location location )
            {
                setMarker();
            }
        } );
    }



}
