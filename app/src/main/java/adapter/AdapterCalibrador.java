package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oscar.aeronet.R;

import java.util.List;

import modelo.Calibrador;
import modelo.Filtro;

public class AdapterCalibrador extends BaseAdapter {

    private List<Calibrador> calibradores;
    private Context context;

    public AdapterCalibrador(List<Calibrador> calibradores, Context context) {
        this.calibradores = calibradores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return calibradores.size();
    }

    @Override
    public Object getItem(int position) {
        return calibradores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.plantilla_calibradores, parent, false);
        }

        TextView tvNombre = row.findViewById(R.id.tv_id_filtro);
        TextView tvPeso = row.findViewById(R.id.tv_peso_filtro);

        tvPeso.setText(String.valueOf(this.calibradores.get(position).getModelo())); // mostrar fecha
        tvNombre.setText(this.calibradores.get(position).getMarca()); // mostrar nombre de lote

        return row;
    }
}
