package mirdep.br.mykwad.pecas.pesquisaDePecas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;

public class PesquisarPecasFragment extends Fragment {

    private View root;

    private RecyclerView recyclerViewPecas;

    private String tipoPeca;

    public PesquisarPecasFragment(String tipoPeca){
        this.tipoPeca = tipoPeca;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pesquisar_pecas, container, false);
        inicializarRecyclerView();
        return root;
    }

    private void inicializarRecyclerView(){
        recyclerViewPecas = root.findViewById(R.id.recyclerViewPecas);
        recyclerViewPecas.setHasFixedSize(true);
        recyclerViewPecas.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));

        recyclerViewPecas.setLayoutManager(new LinearLayoutManager(root.getContext()));

        recyclerViewPecas.setAdapter(new PesquisarPecasAdapter(buscarListaDePecas()));
    }

    private ArrayList<Peca> buscarListaDePecas(){
        return new ArrayList<>(0);
    }
}
