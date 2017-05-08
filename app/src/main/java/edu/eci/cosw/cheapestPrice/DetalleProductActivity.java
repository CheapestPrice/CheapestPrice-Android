package edu.eci.cosw.cheapestPrice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.entities.Item;
import edu.eci.cosw.cheapestPrice.network.ItemRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nombre;
    EditText marca;
    EditText precio;
    List<String> categories;
    Spinner categoria;
    ImageView logo;
    Button save;
    Button file;
    private int id;
    private int idshop;
    Context context;
    private Item item;

    ItemRetrofitNetwork network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_product);
        context=this;
        network=new ItemRetrofitNetwork();
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        item=(Item)  b.getSerializable("item");
        id= (int) b.getSerializable("id");
        idshop=(int) b.getSerializable("shop");
        System.out.println("item: "+item.toString());
        nombre= (EditText) findViewById(R.id.nombreItem);
        nombre.setText(item.getProducto().getNombre());
        marca=(EditText) findViewById(R.id.marcaItem);
        marca.setText(item.getProducto().getMarca());
        categoria=(Spinner) findViewById(R.id.categoria);
        categoria.setOnItemSelectedListener(this);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                network.getCategories(new RequestCallback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> response) {
                        categories=response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                categoria.setAdapter(dataAdapter);
                                System.out.println("cat:"+ categories);
                            }
                        });
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println("Error: "+e);
                    }
                },id);
            }
        });
        precio=(EditText) findViewById(R.id.precioItem);
        precio.setText(Long.toString(item.getPrecio()));
        logo=(ImageView) findViewById(R.id.logo);
        Picasso.with(this).load("https://cheapestprice.herokuapp.com/api/items/"+id+"/shop/"+idshop+"/item/"+item.getId()+"/imagen").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).fit().into(logo);
        file=(Button) findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLogo(v);
            }
        });
        save=(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        network.putItem(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call1, Response<Void> reponse) {
                                System.out.println("Success : "+call1+" r:"+reponse);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Fail: "+t);
                            }

                        },id,idshop,item.getId());
                    }
                });
            }
        });

    }

    private void updateLogo(View v) {
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
