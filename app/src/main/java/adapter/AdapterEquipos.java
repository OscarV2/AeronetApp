package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oscar.aeronet.R;

import java.util.List;

import modelo.Equipo;

public class
AdapterEquipos extends BaseAdapter {

    List<Equipo> equipos;
    private Context context;
    public AdapterEquipos(Context context ,List<Equipo> equipos) {
        this.context = context;
        this.equipos = equipos;

    }

    @Override
    public int getCount() {
        return equipos.size();
    }

    @Override
    public Object getItem(int position) {
        return equipos.get(position);
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
            row = inflater.inflate(R.layout.plantilla_equipos, parent, false);
        }

        TextView tvNombre = row.findViewById(R.id.tv_nombre_equipo);
        TextView tvTipo = row.findViewById(R.id.tv_tipo_equipo);
        TextView tvFiltro = row.findViewById(R.id.tv_filtro);

        String filtro = "";
        try {
          filtro =  this.equipos.get(position).getFiltro().getNombre();

        }catch (NullPointerException e){
            Log.e("ocurrio", "una excepcion");
        }

        tvTipo.setText(this.equipos.get(position).getTipo()); // mostrar fecha
        tvNombre.setText(this.equipos.get(position).getNombre()); // mostrar nombre de lote

        if (filtro.equals("")){
            tvFiltro.setText("Sin filtro asignado.");
        }else{
            tvFiltro.setText(filtro);
        }
        return row;
    }
}
