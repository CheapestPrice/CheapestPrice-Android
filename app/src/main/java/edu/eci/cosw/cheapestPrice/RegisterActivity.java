package edu.eci.cosw.cheapestPrice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;


public class RegisterActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    LinearLayout basic;
    LinearLayout tendero;
    LinearLayout mapa;

    //Formulario básico
    EditText userName;
    EditText email;
    EditText passwd;
    EditText confirmPasswd;

    //Tendero:
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        System.out.println("re chupelo!!!");
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
            alertDialog("Registro exitoso");
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
                mMap.addMarker(new MarkerOptions().position(shop).title("Marker in Sydney"));
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
