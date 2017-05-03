package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.ShoppingListProductActivity;
import edu.eci.cosw.cheapestPrice.entities.ListaDeMercado;

/**
 * Created by 2105403 on 5/2/17.
 */

public class ListasMercadoAdapter extends RecyclerView.Adapter<ListasMercadoAdapter.ViewHolder> {

    private List<ListaDeMercado> listasDeMercado;

    private Context context;


    private final View.OnClickListener clickListener;



    public ListasMercadoAdapter(List<ListaDeMercado> listas,Context mainActivity,View.OnClickListener clickListener){
        listasDeMercado=listas;
        context=mainActivity;
        this.clickListener=clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreLista;
        private TextView fechaCreacion;
        public View view;
        public ViewHolder(View v) {
            super(v);
            setNombreLista((TextView) v.findViewById(R.id.nombreLista));
            setFechaCreacion((TextView) v.findViewById(R.id.fecha));
            view=v;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglistview, parent, false);
        v.findViewById(R.id.ver).
                setOnClickListener(clickListener);

        v.findViewById(R.id.eliminar).setOnClickListener(clickListener);
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
