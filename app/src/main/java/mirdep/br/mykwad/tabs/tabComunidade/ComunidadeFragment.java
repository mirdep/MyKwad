package mirdep.br.mykwad.tabs.tabComunidade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirdep.br.mykwad.DRONES.Drone;
import mirdep.br.mykwad.PECAS.Peca;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.DRONES.escolherDrone_dialogFragment.Controller_EscolherDrone;
import mirdep.br.mykwad.DRONES.escolherDrone_dialogFragment.ExibirDronesAdapter;

public class ComunidadeFragment extends Fragment {

    private View root;

    private RecyclerView recyclerView;
    private ExibirDronesAdapter adapter;
    private Controller_EscolherDrone controller;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comunidade, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.controller = new Controller_EscolherDrone();
        inicializarInterface();
        exibirListaDrones();
    }

    private void inicializarInterface(){
        inicializarRecyclerView();
    }

    //Iniciailiza o recyclerView
    private void inicializarRecyclerView() {
        adapter = new ExibirDronesAdapter(this);
        recyclerView = root.findViewById(R.id.recyclerViewDrones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);
    }

    //Coloca a lista de peças no adapter do recyclewView
    private void exibirListaDrones(){
        LiveData<List<Drone>> drones = controller.getDrones();
        drones.observe(this.getViewLifecycleOwner(), exec -> {
            adapter.definirDrones(drones.getValue());
            root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        });
    }
}