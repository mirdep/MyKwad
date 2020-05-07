package mirdep.br.mykwad.ui.conta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import mirdep.br.mykwad.R;

public class ContaFragment extends Fragment {

    private ContaViewModel contaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contaViewModel = ViewModelProviders.of(this).get(ContaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conta, container, false);
        return root;
    }
}