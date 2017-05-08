package edu.eci.cosw.cheapestPrice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.eci.cosw.cheapestPrice.R;
import edu.eci.cosw.cheapestPrice.entities.Horario;

/**
 * Created by User on 06/05/17.
 */

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder> {

    private List<Horario> horarios;
    private Context context;
    private String horaInicio;
    private String minutoInicio;
    private String horaFin;
    private String minutoFin;

    public HorarioAdapter(List<Horario> horarios,Context context){
        this.horarios=horarios;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView diaHorario;
        private TextView horaInicioHorario;
        private TextView horaFinHorario;

        public ViewHolder(View v) {
            super(v);
            setDiaHorario((TextView) v.findViewById(R.id.diaHorario));
            setHoraInicioHorario((TextView) v.findViewById(R.id.horaInicioHorario));
            setHoraFinHorario((TextView) v.findViewById(R.id.horaFinHorario));
        }

        public TextView getDiaHorario() {return diaHorario;}

        public void setDiaHorario(TextView diaHorario) {this.diaHorario = diaHorario;}

        public TextView getHoraInicioHorario() {return horaInicioHorario;}

        public void setHoraInicioHorario(TextView horaInicioHorario) {this.horaInicioHorario = horaInicioHorario;}

        public TextView getHoraFinHorario() {return horaFinHorario;}

        public void setHoraFinHorario(TextView horaFinHorario) {this.horaFinHorario = horaFinHorario;}
    }
    @Override
    public HorarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_schedule_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HorarioAdapter.ViewHolder holder, int position) {
        Horario h=horarios.get(position);
        //Dia del horario
        holder.getDiaHorario().setText(h.getDia());
        //Hora abrir
        horaInicio=String.valueOf(h.getHoraInicio());minutoInicio=String.valueOf(h.getMinutosInicio());
        if(h.getHoraInicio()<10){
            horaInicio="0"+h.getHoraInicio();
        }
        if(h.getMinutosInicio()<10){
            minutoInicio="0"+h.getMinutosInicio();
        }

        holder.getHoraInicioHorario().setText(horaInicio+":"+minutoInicio);
        //Hora de cierre

        horaFin=String.valueOf(h.getHoraFin());minutoFin=String.valueOf(h.getMinutoFin());
        if(h.getHoraFin()<10){
            horaFin="0"+h.getHoraFin();
        }
        if(h.getMinutoFin()<10){
            minutoFin="0"+h.getMinutoFin();
        }
        holder.getHoraFinHorario().setText(horaFin+":"+minutoFin);
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }
}
