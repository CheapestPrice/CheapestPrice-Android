package edu.eci.cosw.cheapestPrice;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Tienda;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import edu.eci.cosw.cheapestPrice.network.ShopRetrofitNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static edu.eci.cosw.cheapestPrice.R.id.shopAddress;
import static edu.eci.cosw.cheapestPrice.R.id.shopName;
import static edu.eci.cosw.cheapestPrice.R.id.shopNit;
import static edu.eci.cosw.cheapestPrice.R.id.shopPhone;

public class MenuTenderoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    Bundle bundle;

    Tienda tienda;
    private int id = 3;
    private int shop = 1;
    private ShopRetrofitNetwork shopNetwork;

    LinearLayout mapa;

    EditText nombreTienda;
    EditText nitTienda;
    EditText dirTienda;
    EditText telTienda;

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
        setContentView(R.layout.activity_menu_tendero);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        setId(((int) bundle.getSerializable("id")));
        setShop((int) bundle.getSerializable("shopId"));

        mapa = (LinearLayout) findViewById(R.id.mapTienda);

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

        shopNetwork = new ShopRetrofitNetwork();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            int shop;
            int id;

            public Runnable init(int iduser, int idshop) {
                shop = idshop;
                id = iduser;
                return this;
            }

            @Override
            public void run() {
                shopNetwork.getShop(new RequestCallback<Tienda>() {
                    @Override
                    public void onSuccess(Tienda response) {
                        System.out.println("response: " + response);
                        tienda = response;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nombreTienda = (EditText) findViewById(shopName);
                                nombreTienda.setText(tienda.getNombre());

                                nitTienda = (EditText) findViewById(shopNit);
                                nitTienda.setText(tienda.getNit());

                                dirTienda = (EditText) findViewById(shopAddress);
                                dirTienda.setText(tienda.getDireccion());

                                telTienda = (EditText) findViewById(shopPhone);
                                telTienda.setText(tienda.getTelefono());
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e);
                    }
                }, id, shop);

            }
        }.init(getId(), getShop()));
    }

    public void productos(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void opiniones(View view) {
        Intent intent = new Intent(this, OpinionActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void editShop(final View view) {
        if(validForm()){

            ExecutorService executorService = Executors.newFixedThreadPool(1);

            executorService.execute(new Runnable() {
                int shop;
                int id;

                public Runnable init(int iduser, int idshop) {
                    shop = idshop;
                    id = iduser;
                    return this;
                }

                @Override
                public void run() {

                    Tienda tiendaEdit = new Tienda();
                    tiendaEdit.setNombre(nombreTienda.getText().toString());
                    tiendaEdit.setNit(nitTienda.getText().toString());
                    tiendaEdit.setTelefono(telTienda.getText().toString());
                    tiendaEdit.setDisponible(true);
                    tiendaEdit.setDireccion(dirTienda.getText().toString());
                    try {
                        tiendaEdit.setY(geocoder.getFromLocationName(address + ", Bogota", 1).get(0).getLatitude());
                        tiendaEdit.setX(geocoder.getFromLocationName(address + ", Bogota", 1).get(0).getLongitude());
                    }catch (IOException e){
                        System.out.println(e);
                        tiendaEdit.setY(0);
                        tiendaEdit.setX(0);
                    }

                    shopNetwork.modifyShop(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call1, Response<Void> reponse) {
                            System.out.println("Success : "+call1+" r:"+reponse);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setMessage("Actualizacion completa..")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("Fail: "+t);
                        }

                    },id,shop,tiendaEdit);
                }
            });

        }

    }

    private boolean validForm() {
        boolean ans = true;

        ans &= !(dirTienda.getText() == null || dirTienda.getText().length() == 0);
        ans &= !(nombreTienda.getText() == null || nombreTienda.getText().length() == 0);
        ans &= !(nitTienda.getText() == null || nitTienda.getText().length() == 0);
        ans &= !(telTienda.getText() == null || telTienda.getText().length() == 0);
        try {
            if (geocoder.getFromLocationName(address + ", Bogota", 1).size() == 0) {
                ans = false;
            }
        } catch (IOException e) {
            ans = false;
        }
        return ans;
    }

    public void verMapa(View view) {
        address = ((EditText) findViewById(R.id.shopAddress)).getText().toString();
        if ((address.trim().length() > 0 && mapa.getVisibility() == View.GONE) || mapa.getVisibility() != View.GONE) {
            mapa.setVisibility(mapa.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            if (mapa.getVisibility() != View.GONE) {
                setMarker();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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

    public void setMarker() {
        try {
            System.out.println("----------------------->"+address);
            System.out.println(geocoder.getFromLocationName(address+", Bogota", 1));
            if(geocoder.getFromLocationName(address+", Bogota", 1).size()>0) {
                LatLng shop = new LatLng(geocoder.getFromLocationName(address+", Bogota", 1).get(0).getLatitude(), geocoder.getFromLocationName(address+", Bogota", 1).get(0).getLongitude());
                mMap.addMarker(new MarkerOptions().position(shop).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop,17));
            }else{
                dirTienda.setError("La dirección no es valida");
                System.out.println("La direccion no es valida");
            }
        } catch (IOException e) {
            e.printStackTrace();
            dirTienda.setError("La dirección no es valida");
            System.out.println("La direccion no es valida");
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMarker();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        startLocationUpdates();
    }
}
