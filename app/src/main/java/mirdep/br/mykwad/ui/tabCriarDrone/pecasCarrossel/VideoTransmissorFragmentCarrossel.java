package mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import mirdep.br.mykwad.R;

public class VideoTransmissorFragmentCarrossel extends Fragment {

    private Button carrossel_esc_button_escolher;
    private ImageView carrossel_esc_imagem;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.carrossel_fragment_esc, container, false);
        inicializarInterface();
        return root;
    }

    private void inicializarInterface(){
        carrossel_esc_button_escolher = root.findViewById(R.id.carrossel_esc_button_escolher);
        carrossel_esc_imagem = root.findViewById(R.id.carrossel_esc_imagem);
    }
}
