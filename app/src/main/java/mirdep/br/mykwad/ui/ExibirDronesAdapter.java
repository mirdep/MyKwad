package mirdep.br.mykwad.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class ExibirDronesAdapter extends RecyclerView.Adapter<ExibirDronesAdapter.DroneViewHolder> {

    private List<Drone> drones;
    private Fragment parent;

    public ExibirDronesAdapter(Fragment parent){
        this.parent = parent;
        drones = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExibirDronesAdapter.DroneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_drone, viewGroup, false);
        DroneViewHolder pvh = new DroneViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DroneViewHolder holder, int position) {
        holder.textView_viewholder_drone_titulo.setText(drones.get(position).getTitulo());
        holder.textView_viewholder_drone_nicknameDono.setText(UsuarioRepositorio.getInstance().getNicknameById(drones.get(position).getUsuarioDonoId()));
        GlideApp.with(parent.getContext())
                .load(DroneRepositorio.getInstance().getFotoDroneReference(drones.get(position)))
                .into(holder.imageView_viewholder_drone_foto);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return drones != null ? drones.size() : 0;
    }

    public void definirDrones(List<Drone> drones){
        this.drones = drones;
        notifyDataSetChanged();
    }

    private void removerDrone(int position){
        drones.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, drones.size());
    }

    //======================================== VIEW HOLDER ===================================================
    class DroneViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView_viewholder_drone_foto;
        private TextView textView_viewholder_drone_titulo;
        private TextView textView_viewholder_drone_nicknameDono;

        private DroneViewHolder(View itemView){
            super(itemView);
            imageView_viewholder_drone_foto = itemView.findViewById(R.id.imageView_viewholder_drone_foto);
            textView_viewholder_drone_titulo = itemView.findViewById(R.id.textView_viewholder_drone_titulo);
            textView_viewholder_drone_nicknameDono = itemView.findViewById(R.id.textView_viewholder_drone_nicknameDono);
        }
    }
}
