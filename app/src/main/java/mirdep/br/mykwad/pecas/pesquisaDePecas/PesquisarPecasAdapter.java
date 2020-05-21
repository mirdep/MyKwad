package mirdep.br.mykwad.pecas.pesquisaDePecas;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;

public class PesquisarPecasAdapter extends RecyclerView.Adapter<PecaViewHolder> {

    private List<Peca> pecas;

    public PesquisarPecasAdapter(ArrayList pecas){
        this.pecas = pecas;
    }

    @NonNull
    @Override
    public PecaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PecaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pesquisar_pecas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PecaViewHolder holder, int position) {
        holder.textView_viewholder_peca_nome.setText(pecas.get(position).getModelo());
        holder.imageView_viewholder_peca_foto.setImageURI(pecas.get(position).getStorage_imagem_uri());
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
}
