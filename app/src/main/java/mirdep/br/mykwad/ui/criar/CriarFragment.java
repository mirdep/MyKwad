package mirdep.br.mykwad.ui.criar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import mirdep.br.mykwad.R;

public class CriarFragment extends Fragment {

    private CriarViewModel criarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        criarViewModel = ViewModelProviders.of(this).get(CriarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        return root;
    }
}