package mirdep.br.mykwad.ui.tabMinhaConta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;
import mirdep.br.mykwad.usuario.LoginFragment;

public class MinhaContaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        if (AutenticacaoRepositorio.getUsuario() == null) {
            root = inflater.inflate(R.layout.fragment_login, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        }
        return root;
    }
}