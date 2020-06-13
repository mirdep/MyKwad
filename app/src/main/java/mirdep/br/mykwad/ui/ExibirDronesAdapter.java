package mirdep.br.mykwad.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.main_tabs.tabComunidade.ViewDroneFragment;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.ImagemRepositorio;
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
        Drone drone = drones.get(position);
        holder.textView_viewholder_drone_titulo.setText(drone.getTitulo());

        LiveData<String> nickname = UsuarioRepositorio.getInstance().getNicknameById(drone.getUsuarioDonoId());
        nickname.observe(parent.getViewLifecycleOwner(), exec -> {
            holder.textView_viewholder_drone_nicknameDono.setText(nickname.getValue());
        });
        GlideApp.with(parent.getContext())
                .load(DroneRepositorio.getInstance().getFotoDroneReference(drones.get(position)))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView_viewholder_drone_foto);

        GlideApp.with(parent.getContext())
                .load(ImagemRepositorio.getInstance().getFotoUsuarioReference(drone.getUsuarioDonoId()))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView_viewholder_drone_fotoDono);

        holder.itemView.setOnClickListener(v -> {
            openFragment(new ViewDroneFragment(drone));
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = parent.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        private ImageView imageView_viewholder_drone_fotoDono;

        private DroneViewHolder(View itemView){
            super(itemView);
            imageView_viewholder_drone_foto = itemView.findViewById(R.id.imageView_viewholder_drone_foto);
            textView_viewholder_drone_titulo = itemView.findViewById(R.id.textView_viewholder_drone_titulo);
            textView_viewholder_drone_nicknameDono = itemView.findViewById(R.id.textView_viewholder_drone_nicknameDono);
            imageView_viewholder_drone_fotoDono = itemView.findViewById(R.id.imageView_viewholder_drone_fotoDono);
        }
    }
}
