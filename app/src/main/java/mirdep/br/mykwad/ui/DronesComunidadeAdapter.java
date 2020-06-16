package mirdep.br.mykwad.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.main_tabs.tabComunidade.ViewDroneFragment;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneLikesRepositorio;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.ImagemRepositorio;
import mirdep.br.mykwad.repositorio.NicknameRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;

public class DronesComunidadeAdapter extends RecyclerView.Adapter<DronesComunidadeAdapter.DroneViewHolder> {

    private List<Drone> drones;
    private Fragment parent;

    public DronesComunidadeAdapter(Fragment parent){
        this.parent = parent;
        drones = new ArrayList<>();
    }

    @NonNull
    @Override
    public DronesComunidadeAdapter.DroneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_drone, viewGroup, false);
        DroneViewHolder pvh = new DroneViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DroneViewHolder holder, int position) {
        Drone drone = drones.get(position);
        addQtdLikes(holder, drone);
        addLikeCoracao(holder, drone);
        holder.textView_viewholder_drone_titulo.setText(drone.getTitulo());
        holder.textView_viewholder_drone_descricao.setText(drone.getDescricao());
        if(drone.getTempoCriacao() != null)
            holder.textView_viewholder_drone_horaCriacao.setText(drone.criadoEm());

        LiveData<String> nickname = NicknameRepositorio.getInstance().getNicknameById(drone.getUsuarioDonoId());
        nickname.observe(parent.getViewLifecycleOwner(), exec -> {
            holder.textView_viewholder_drone_nicknameDono.setText(nickname.getValue());
        });

        GlideApp.with(parent.getContext())
                .load(ImagemRepositorio.getInstance().getFotoDroneReference(drones.get(position)))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView_viewholder_drone_foto);

        GlideApp.with(parent.getContext())
                .load(ImagemRepositorio.getInstance().getFotoUsuarioReference(drone.getUsuarioDonoId()))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageView_viewholder_drone_fotoDono);

        holder.imageView_viewholder_drone_foto.setOnClickListener(v -> {
            openFragment(new ViewDroneFragment(drone));
        });
    }

    private void addLikeCoracao(DroneViewHolder holder, Drone drone){
        if(UsuarioAuthentication.getInstance().usuarioEstaLogado()){
            LiveData<Boolean> usuarioCurtiu = DroneLikesRepositorio.getInstance().usuarioCurtiu(drone.getId(), UsuarioAuthentication.getInstance().getUsuarioId());
            usuarioCurtiu.observe(parent.getViewLifecycleOwner(), exec -> {
                if(usuarioCurtiu.getValue() != null && usuarioCurtiu.getValue()){
                    holder.view_viewholder_drone_like.setBackgroundResource(R.drawable.coracao_vermelho);
                    holder.view_viewholder_drone_like.setOnClickListener(v -> {
                        DroneLikesRepositorio.getInstance().remover(drone.getId(), UsuarioAuthentication.getInstance().getUsuarioId());
                        addQtdLikes(holder, drone);
                        addLikeCoracao(holder, drone);
                    });
                } else {
                    holder.view_viewholder_drone_like.setBackgroundResource(R.drawable.coracao_branco);
                    holder.view_viewholder_drone_like.setOnClickListener(v -> {
                        animacaoCoracaoLike(holder.view_viewholder_drone_like);
                        DroneLikesRepositorio.getInstance().inserir(drone.getId(), UsuarioAuthentication.getInstance().getUsuarioId());
                        addQtdLikes(holder, drone);
                        addLikeCoracao(holder, drone);
                    });
                }
            });
        } else {
            //exibirDialogLogin();
        }
    }

    private void exibirDialogLogin(){
        new AlertDialog.Builder(parent.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                .setTitle("Quer interagir com a comunidade?")
                .setMessage("Faça login ou crie uma nova conta!")
                .setPositiveButton("Sim", (dialog, which) -> {
                    ((BaseApp) parent.getActivity()).selecionarTab(3);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void addQtdLikes(DroneViewHolder holder, Drone drone){
        LiveData<Long> qtdLikes = DroneLikesRepositorio.getInstance().getQtdLikes(drone.getId());
        qtdLikes.observe(parent.getViewLifecycleOwner(), exec -> {
            Long qtd = qtdLikes.getValue();
            if(qtd == 0){
                holder.textView_viewholder_drone_qtdlikes.setText("Seja o primeiro a curtir");
            } else if (qtd == 1){
                holder.textView_viewholder_drone_qtdlikes.setText("1 curtida");
            } else {
                holder.textView_viewholder_drone_qtdlikes.setText(qtd+" curtidas");
            }
        });
    }

    private void animacaoCoracaoLike(View view){
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(300);

        scaleDown.setRepeatCount(1);

        scaleDown.start();
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
        private TextView textView_viewholder_drone_descricao;
        private TextView textView_viewholder_drone_horaCriacao;
        private TextView textView_viewholder_drone_qtdlikes;
        private ImageView imageView_viewholder_drone_fotoDono;
        private View view_viewholder_drone_like;

        private DroneViewHolder(View itemView){
            super(itemView);
            imageView_viewholder_drone_foto = itemView.findViewById(R.id.imageView_viewholder_drone_foto);
            textView_viewholder_drone_titulo = itemView.findViewById(R.id.textView_viewholder_drone_titulo);
            textView_viewholder_drone_nicknameDono = itemView.findViewById(R.id.textView_viewholder_drone_nicknameDono);
            textView_viewholder_drone_descricao = itemView.findViewById(R.id.textView_viewholder_drone_descricao);
            textView_viewholder_drone_horaCriacao = itemView.findViewById(R.id.textView_viewholder_drone_horaCriacao);
            textView_viewholder_drone_qtdlikes = itemView.findViewById(R.id.textView_viewholder_drone_qtdlikes);
            imageView_viewholder_drone_fotoDono = itemView.findViewById(R.id.imageView_viewholder_drone_fotoDono);
            view_viewholder_drone_like = itemView.findViewById(R.id.view_viewholder_drone_like);
        }
    }
}
