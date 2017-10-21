package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oscar.aeronet.R;

import java.util.List;

import modelo.Filtro;

public class AdapterFiltros extends BaseAdapter {

    private List<Filtro> filtros;
    private Context context;

    public AdapterFiltros(List<Filtro> filtros, Context context) {
        this.filtros = filtros;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filtros.size();
    }

    @Override
    public Object getItem(int position) {
        return filtros.get(position);
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

        TextView tvNombre = row.findViewById(R.id.tv_id_filtro);
        TextView tvPeso = row.findViewById(R.id.tv_peso_filtro);

        tvPeso.setText(String.valueOf(this.filtros.get(position).getPeso())); // mostrar fecha
        tvNombre.setText(this.filtros.get(position).getNombre()); // mostrar nombre de lote

        return row;
    }
}
