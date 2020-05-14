package mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import mirdep.br.mykwad.R;

public class PecaFragmentCarrossel extends Fragment {

    private Button carrossel_peca_button_escolher;
    private ImageView carrossel_peca_imagem;
    private TextView carrossel_peca_nome;

    private String pecaNome;

    private View root;

    public PecaFragmentCarrossel(String pecaNome){
        this.pecaNome = pecaNome;
    }

    public PecaFragmentCarrossel(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.carrossel_fragment_peca, container, false);
        inicializarInterface();
        return root;
    }

    private void inicializarInterface(){
        carrossel_peca_button_escolher = root.findViewById(R.id.button_peca_escolher);
        carrossel_peca_imagem = root.findViewById(R.id.imageView_peca_imagem);
        carrossel_peca_nome = root.findViewById(R.id.textView_peca_nome);
        carrossel_peca_nome.setText(pecaNome);
    }
}
