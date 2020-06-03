package mirdep.br.mykwad.ui.escolherPeca_dialogFragment;

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
import mirdep.br.mykwad.objetos.Peca;

public class ExibirPecasAdapter extends RecyclerView.Adapter<ExibirPecasAdapter.PecaViewHolder> {

    private List<Peca> pecas;
    private View_EscolherPeca parent;

    public ExibirPecasAdapter(View_EscolherPeca parent){
        this.parent = parent;
        pecas = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExibirPecasAdapter.PecaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_peca, viewGroup, false);
        PecaViewHolder pvh = new PecaViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PecaViewHolder holder, int position) {
        holder.textView_viewholder_peca_nome.setText(pecas.get(position).toString());
        holder.imageView_viewholder_peca_foto.setImageURI(pecas.get(position).getStorage_imagem_uri());
        holder.itemView.setOnClickListener(v -> {
            parent.fecharDialog(pecas.get(position));
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return pecas != null ? pecas.size() : 0;
    }

    public void definirPecas(List<Peca> pecas){
        this.pecas = pecas;
        notifyDataSetChanged();
    }

    private void removerPeca(int position){
        pecas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pecas.size());
    }

    private void fecharDialog(){

    }

    //======================================== VIEW HOLDER ===================================================
    class PecaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView_viewholder_peca_foto;
        private TextView textView_viewholder_peca_nome;

        private PecaViewHolder(View itemView){
            super(itemView);
            imageView_viewholder_peca_foto = itemView.findViewById(R.id.imageView_viewholder_peca_foto);
            textView_viewholder_peca_nome = itemView.findViewById(R.id.textView_viewholder_peca_nome);
        }
    }
}
