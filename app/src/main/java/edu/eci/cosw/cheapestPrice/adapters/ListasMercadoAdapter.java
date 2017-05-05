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

    public ListasMercadoAdapter(List<ListaDeMercado> listas,Context mainActivity){
        listasDeMercado=listas;
        context=mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreLista;
        private TextView fechaCreacion;
        private Button ver;
        public ViewHolder(View v) {
            super(v);
            setNombreLista((TextView) v.findViewById(R.id.nombreLista));
            setFechaCreacion((TextView) v.findViewById(R.id.fecha));
            setVer((Button) v.findViewById(R.id.ver));
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

        public Button getVer() {return ver;}

        public void setVer(Button ver) {this.ver = ver;}
    }

    @Override
    public ListasMercadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglistview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListasMercadoAdapter.ViewHolder holder, final int position) {
        ListaDeMercado lista=listasDeMercado.get(position);
        //Nombre lista
        holder.getNombreLista().setText(lista.getListaid().getNombre());
        //Fecha de creacion
        holder.getFechaCreacion().setText(lista.getFechaCreacion().toString());
        //Ver
        holder.getVer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=position;
                Intent intent=new Intent(v.getContext(),ShoppingListProductActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("post",listasDeMercado.get(position));
                Intent start=intent.putExtra("bundle",b);
                context.startActivity(start);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listasDeMercado.size();
    }
}
