package mirdep.br.mykwad.ui.tabMinhaConta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;

public class MinhaContaFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        root.findViewById(R.id.button_minhaconta_logout).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return root;
    }

    private void logout(){
        AutenticacaoRepositorio auth = new AutenticacaoRepositorio();
        auth.logoutConta();
    }
}