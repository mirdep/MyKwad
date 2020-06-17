package mirdep.br.mykwad.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.fragments.tabComunidade.ViewDroneFragment;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.ImagemRepositorio;

public class MeusDronesAdapter extends RecyclerView.Adapter<MeusDronesAdapter.MeuDroneViewHolder> {

    private List<Drone> drones;
    private Fragment parent;

    public MeusDronesAdapter(Fragment parent){
        this.parent = parent;
        drones = new ArrayList<>();
    }

    @NonNull
    @Override
    public MeuDroneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_meusdrones, viewGroup, false);
        MeuDroneViewHolder pvh = new MeuDroneViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MeuDroneViewHolder holder, int position) {
        Drone drone = drones.get(position);
        holder.textView_viewholder_drone_titulo.setText(drone.getTitulo());
        holder.textView_viewholder_drone_descricao.setText(drone.getDescricao());
        if(drone.getTempoCriacao() != null)
            holder.textView_viewholder_drone_horaCriacao.setText(drone.criadoEm());

        GlideApp.with(parent.getContext())
                .load(ImagemRepositorio.getInstance().getFotoDroneReference(drones.get(position)))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView_viewholder_drone_foto);

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
    class MeuDroneViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView_viewholder_drone_foto;
        private TextView textView_viewholder_drone_titulo;
        private TextView textView_viewholder_drone_descricao;
        private TextView textView_viewholder_drone_horaCriacao;

        private MeuDroneViewHolder(View itemView){
            super(itemView);
            imageView_viewholder_drone_foto = itemView.findViewById(R.id.imageView_viewholder_drone_foto);
            textView_viewholder_drone_titulo = itemView.findViewById(R.id.textView_viewholder_drone_titulo);
            textView_viewholder_drone_descricao = itemView.findViewById(R.id.textView_viewholder_drone_descricao);
            textView_viewholder_drone_horaCriacao = itemView.findViewById(R.id.textView_viewholder_drone_horaCriacao);
        }
    }
}
