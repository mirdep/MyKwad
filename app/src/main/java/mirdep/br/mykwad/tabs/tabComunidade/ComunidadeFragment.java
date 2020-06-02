package mirdep.br.mykwad.tabs.tabComunidade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mirdep.br.mykwad.DRONES.Drone;
import mirdep.br.mykwad.DRONES.DroneRepositorio;
import mirdep.br.mykwad.R;

public class ComunidadeFragment extends Fragment {

    private View root;

    private RecyclerView recyclerView;
    private ExibirDronesAdapter adapter;

    public ComunidadeViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comunidade, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ComunidadeViewModel.class);
        inicializarRecyclerView();
    }

    //Iniciailiza o recyclerView
    private void inicializarRecyclerView() {
        adapter = new ExibirDronesAdapter(this);
        povoarAdapter();
        recyclerView = root.findViewById(R.id.recyclerViewDrones);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);

    }

    //Coloca a lista de peças no adapter do recyclewView
    private void povoarAdapter() {
        mViewModel.getTodosDrones().observe(this.getViewLifecycleOwner(), drones -> {
            adapter.definirDrones(drones);
            root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
        });
    }
}