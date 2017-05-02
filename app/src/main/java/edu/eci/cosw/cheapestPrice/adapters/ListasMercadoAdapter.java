package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ListasMercadoAdapter extends RecyclerView.Adapter<ListasMercadoAdapter.ViewHolder> {

    private List<ListaDeMercado> listasDeMercado;
    private Context context;

    public ListasMercadoAdapter(List<ListaDeMercado> listas,Context mainActivity){
        listasDeMercado=listas;
        context=mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreLista;
        private TextView fechaCreacion;

        public ViewHolder(View v) {
            super(v);
            setNombreLista((TextView) v.findViewById(R.id.nombreLista));
            setFechaCreacion((TextView) v.findViewById(R.id.fecha));
        }

        public TextView getNombreLista() {
            return nombreLista;
        }

        public void setNombreLista(TextView nombreLista) {
            this.nombreLista = nombreLista;
        }

        public TextView getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(TextView fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }
    }

    @Override
    public ListasMercadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shopping_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListasMercadoAdapter.ViewHolder holder, int position) {
        ListaDeMercado lista=listasDeMercado.get(position);
        //Nombre lista
        holder.getNombreLista().setText(lista.getListaid().getNombre());
        //Fecha de creacion
        holder.getFechaCreacion().setText(lista.getFechaCreacion().toString());
    }

    @Override
    public int getItemCount() {
        return listasDeMercado.size();
    }
}
