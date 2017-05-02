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

public class InformacionTiendaAdapter extends RecyclerView.Adapter<InformacionTiendaAdapter.ViewHolder> {

    private List<ItemLista> tiendas;
    private Context context;

    public InformacionTiendaAdapter(List<ItemLista> tiendas,Context context){
        this.tiendas=tiendas;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreTienda;
        private TextView telefonoTienda;
        private TextView direccionTienda;

        public ViewHolder(View v) {
            super(v);
            setNombreTienda((TextView) v.findViewById(R.id.nombreTienda));
            setTelefonoTienda((TextView) v.findViewById(R.id.telefonoTienda));
            setDireccionTienda((TextView) v.findViewById(R.id.direccionTienda));
        }

        public TextView getNombreTienda() {
            return nombreTienda;
        }

        public void setNombreTienda(TextView nombreTienda) {
            this.nombreTienda = nombreTienda;
        }

        public TextView getTelefonoTienda() {
            return telefonoTienda;
        }

        public void setTelefonoTienda(TextView telefonoTienda) {
            this.telefonoTienda = telefonoTienda;
        }

        public TextView getDireccionTienda() {
            return direccionTienda;
        }

        public void setDireccionTienda(TextView direccionTienda) {
            this.direccionTienda = direccionTienda;
        }
    }

    @Override
    public InformacionTiendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popuptienda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InformacionTiendaAdapter.ViewHolder holder, int position) {
        ItemLista item=tiendas.get(position);
        //Nombre tienda
        holder.nombreTienda.setText(item.getItem().getTienda().getNombre());
        //Telefono de la tienda
        holder.telefonoTienda.setText(item.getItem().getTienda().getTelefono());
        //Direccion de la tienda
        holder.direccionTienda.setText(item.getItem().getTienda().getDireccion());
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }
}
