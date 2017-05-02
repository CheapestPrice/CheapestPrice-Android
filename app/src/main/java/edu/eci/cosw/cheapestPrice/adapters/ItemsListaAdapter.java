package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.ItemLista;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ItemsListaAdapter extends RecyclerView.Adapter<ItemsListaAdapter.ViewHolder> {

    private List<ItemLista> productos;
    private Context context;

    public ItemsListaAdapter(List<ItemLista> productos,Context mainActivity){
        this.productos=productos;
        context=mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreProducto;
        private TextView precio;
        //Información de la tienda

        public ViewHolder(View v) {
            super(v);
            nombreProducto=(TextView) v.findViewById(R.id.nombreProducto);
            precio=(TextView) v.findViewById(R.id.precio);
        }
    }

    @Override
    public ItemsListaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_products, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsListaAdapter.ViewHolder holder, int position) {
        ItemLista item=productos.get(position);
        //Nombre del producto
        holder.nombreProducto.setText(item.getItem().getProducto().getNombre());
        //Precio del producto
        holder.precio.setText("$"+item.getItem().getPrecio());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
