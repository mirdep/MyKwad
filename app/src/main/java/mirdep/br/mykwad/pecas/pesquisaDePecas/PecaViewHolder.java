package mirdep.br.mykwad.pecas.pesquisaDePecas;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mirdep.br.mykwad.R;


public class PecaViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView_viewholder_peca_foto;
    public TextView textView_viewholder_peca_nome;

    public PecaViewHolder(View itemView){
        super(itemView);
        imageView_viewholder_peca_foto = itemView.findViewById(R.id.imageView_viewholder_peca_foto);
        textView_viewholder_peca_nome = itemView.findViewById(R.id.textView_viewholder_peca_nome);
    }
}
