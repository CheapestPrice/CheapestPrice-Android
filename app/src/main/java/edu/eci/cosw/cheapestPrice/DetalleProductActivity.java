package edu.eci.cosw.cheapestPrice;

import android.content.Intent;
import android.database.DataSetObserver;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import edu.eci.cosw.cheapestPrice.entities.Item;

public class DetalleProductActivity extends AppCompatActivity {

    EditText nombre;
    EditText marca;
    EditText precio;
    Spinner categoria;
    ImageView logo;
    Button save;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_product);
        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("bundle");
        item=(Item)  b.getSerializable("item");
        System.out.println("item: "+item.toString());
        nombre= (EditText) findViewById(R.id.nombreItem);
        nombre.setText(item.getProducto().getNombre());
        marca=(EditText) findViewById(R.id.marcaItem);
        marca.setText(item.getProducto().getMarca());
        categoria=(Spinner) findViewById(R.id.categoria);
        precio=(EditText) findViewById(R.id.precioItem);
       // precio.setText(item.getPrecio());
        logo=(ImageView) findViewById(R.id.logo);
        save=(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem(v);
            }
        });
    }

    private void updateItem(View v) {

    }
}
