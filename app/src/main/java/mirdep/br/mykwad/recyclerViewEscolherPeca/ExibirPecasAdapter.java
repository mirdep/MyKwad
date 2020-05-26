package mirdep.br.mykwad.recyclerViewEscolherPeca;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;

public class ExibirPecasAdapter extends RecyclerView.Adapter<ExibirPecasAdapter.PecaViewHolder> {

    private List<Peca> pecas;

    private int selected_position = RecyclerView.NO_POSITION;

    public ExibirPecasAdapter(ArrayList pecas){
        this.pecas = pecas;
    }

    @NonNull
    @Override
    public ExibirPecasAdapter.PecaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_peca_recyclerview, viewGroup, false);
        PecaViewHolder pvh = new PecaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PecaViewHolder holder, int position) {
        holder.textView_viewholder_peca_nome.setText(pecas.get(position).toString());
        holder.imageView_viewholder_peca_foto.setImageURI(pecas.get(position).getStorage_imagem_uri());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return pecas != null ? pecas.size() : 0;
    }

    public void inserirPeca(Peca peca){
        pecas.add(peca);
        notifyItemInserted(getItemCount());
    }

    private void removerPeca(int position){
        pecas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pecas.size());
    }

    private void fecharDialog(){

    }

    //======================================== VIEW HOLDER ===================================================
    class PecaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView_viewholder_peca_foto;
        private TextView textView_viewholder_peca_nome;

        private PecaViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            imageView_viewholder_peca_foto = itemView.findViewById(R.id.imageView_viewholder_peca_foto);
            textView_viewholder_peca_nome = itemView.findViewById(R.id.textView_viewholder_peca_nome);
        }

        @Override
        public void onClick(View v) {
            // Below line is just like a safety check, because sometimes holder could be null,
            // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

            // Do your another stuff for your onClick
            fecharDialog();
        }
    }
}
