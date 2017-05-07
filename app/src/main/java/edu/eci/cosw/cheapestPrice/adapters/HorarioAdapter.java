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
        holder.getDiaHorario().setText(h.getHorarioId().getDia());
        //Hora abrir
        holder.getHoraInicioHorario().setText(h.getHoraInicio()+":"+h.getMinutosInicio());
        //Hora de cierre
        holder.getHoraFinHorario().setText(h.getHoraFin()+":"+h.getMinutoFin());
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }
}
