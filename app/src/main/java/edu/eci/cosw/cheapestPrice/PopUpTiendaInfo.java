package edu.eci.cosw.cheapestPrice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import edu.eci.cosw.cheapestPrice.adapters.HorarioAdapter;
import edu.eci.cosw.cheapestPrice.entities.Tienda;

/**
 * Created by User on 06/05/17.
 */

public class PopUpTiendaInfo extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    private Tienda tienda;
    private TextView nombreTienda;
    private TextView telefonoTienda;
    private TextView direcionTienda;
    private RecyclerView recyclerView;

    //Mapa
    LinearLayout mapa;
    private static final int ACCESS_LOCATION_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    public String address;
    private Geocoder geocoder;
    private String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION};
    ProgressDialog cargando;

    public void cargar() {
        cargando.setMessage("Cargando...");
        cargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        cargando.setIndeterminate(true);
        cargando.setCanceledOnTouchOutside(false);
        cargando.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuptienda);
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundleTienda");
        tienda=(Tienda) b.getSerializable("postTienda");
        nombreTienda=(TextView) findViewById(R.id.nombreTienda);
        telefonoTienda=(TextView) findViewById(R.id.telefonoTienda);
        direcionTienda=(TextView) findViewById(R.id.direccionTienda);
        cargando=new ProgressDialog(this);
        configureRecyclerView();
        refresh();

        mapa=(LinearLayout) findViewById(R.id.mapLin);

        //mas mapa
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        locationRequest = new LocationRequest();
        address = ((TextView)findViewById(R.id.direccionTienda)).getText().toString();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        googleApiClient.connect();
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapShop);
        mapFragment.getMapAsync(this);

    }

    private void configureRecyclerView() {
        nombreTienda.setText(tienda.getNombre());
        telefonoTienda.setText(tienda.getTelefono());
        direcionTienda.setText(tienda.getDireccion());
        address=direcionTienda.getText().toString();
        setRecyclerView((RecyclerView) findViewById( R.id.recyclerViewHorariosTienda));
        getRecyclerView().setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        getRecyclerView().setLayoutManager( layoutManager );
    }

    public void refresh(){
        recyclerView.setAdapter(new HorarioAdapter(tienda.getHorarios(),this));
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    //aun mas mapa
    public void verMapa(View view){
        address=direcionTienda.getText().toString();
        System.out.println(address);
        if((address.trim().length()>0 && mapa.getVisibility()==View.GONE) || mapa.getVisibility()!=View.GONE) {
            mapa.setVisibility(mapa.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            if(mapa.getVisibility() != View.GONE) {
                setMarker();
            }
        }
    }

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

                System.out.println("La direccion no es valida");
            }
        } catch (IOException e) {
            e.printStackTrace();

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
