package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.cosw.cheapestPrice.PopUpTiendaInfo;
import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.ItemLista;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;
import edu.eci.cosw.cheapestPrice.network.ListaMercadoRetrofitNetwork;
import edu.eci.cosw.cheapestPrice.network.NetworkException;
import edu.eci.cosw.cheapestPrice.network.RequestCallback;
import okhttp3.ResponseBody;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ItemsListaAdapter extends RecyclerView.Adapter<ItemsListaAdapter.ViewHolder> {

    private ListaDeMercado productos;
    private Context context;
    private ListaMercadoRetrofitNetwork network;
    ExecutorService executorService;

    public ItemsListaAdapter(ListaDeMercado productos,Context mainActivity){
        this.productos=productos;
        context=mainActivity;
        network=new ListaMercadoRetrofitNetwork();
        executorService= Executors.newFixedThreadPool(1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreProducto;
        private TextView precio;
        private CheckBox favorito;
        private CheckBox comprado;
        private Button masInformacion;
        //Información de la tienda

        public ViewHolder(View v) {
            super(v);
            setNombreProducto((TextView) v.findViewById(R.id.nombreProducto));
            setPrecio((TextView) v.findViewById(R.id.precio));
            setFavorito((CheckBox) v.findViewById(R.id.favorito));
            setComprado((CheckBox) v.findViewById(R.id.comprado));
            setMasInformacion((Button) v.findViewById(R.id.infoTienda));
        }

        public TextView getNombreProducto() {
            return nombreProducto;
        }

        public void setNombreProducto(TextView nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public CheckBox getFavorito() {
            return favorito;
        }

        public void setFavorito(CheckBox favorito) {
            this.favorito = favorito;
        }

        public CheckBox getComprado() {
            return comprado;
        }

        public void setComprado(CheckBox comprado) {
            this.comprado = comprado;
        }

        public Button getMasInformacion() {return masInformacion;}

        public void setMasInformacion(Button masInformacion) {this.masInformacion = masInformacion;}
    }

    @Override
    public ItemsListaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_product_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsListaAdapter.ViewHolder holder, final int position) {
        ItemLista item=productos.getItems().get(position);
        //Nombre del producto
        holder.getNombreProducto().setText(item.getItem().getProducto().getNombre());
        //Precio del producto
        holder.getPrecio().setText("$"+item.getItem().getPrecio());
        //Item favorito
        holder.getFavorito().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        network.itemSeleccionadoFavorito(new RequestCallback<ResponseBody>() {
                            @Override
                            public void onSuccess(ResponseBody response) {
                                System.out.println("Cambio el item favorito");
                            }
                            @Override
                            public void onFailed(NetworkException e) {
                                System.out.println(e);
                            }
                        }, productos.getListaid().getUsuario(),
                                productos.getListaid().getNombre(),
                                productos.getItems().get(position).getId().getProductoId(),
                                productos.getItems().get(position).getId().getTiendaNit(),
                                productos.getItems().get(position).getId().getTiendaX(),
                                productos.getItems().get(position).getId().getTiendaY(),
                                !productos.getItems().get(position).getFavorito());
                    }

                });
            }
        });
        //Mas informacion sobre la tienda
        holder.getMasInformacion().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), PopUpTiendaInfo.class);
                Bundle b=new Bundle();
                b.putSerializable("postTienda",productos.getItems().get(position).getItem().getTienda());
                Intent start=intent.putExtra("bundleTienda",b);
                context.startActivity(start);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.getItems().size();
    }
}
