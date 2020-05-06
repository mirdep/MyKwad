package mirdep.br.mykwad.ui.comunidade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import mirdep.br.mykwad.R;

public class ComunidadeFragment extends Fragment {

    private ComunidadeViewModel comunidadeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        comunidadeViewModel = ViewModelProviders.of(this).get(ComunidadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comunidade, container, false);
        return root;
    }
}