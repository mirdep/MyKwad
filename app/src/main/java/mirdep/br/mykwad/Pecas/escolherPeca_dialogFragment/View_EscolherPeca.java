package mirdep.br.mykwad.Pecas.escolherPeca_dialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mirdep.br.mykwad.R;

public class View_EscolherPeca extends DialogFragment {
    private static final String NOME_LOG = "[EscolherPeca]";

    private View root;

    private RecyclerView recyclerView;
    private ExibirPecasAdapter adapter;
    private View button_fechar_dialogfragment;

    private String tipoPeca;
    private Controller_EscolherPeca controller;

    public View_EscolherPeca(String tipoPeca) {
        Log.d(NOME_LOG, "View criado!");
        this.tipoPeca = tipoPeca;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialogfragment_pesquisar_pecas, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.controller = new Controller_EscolherPeca(tipoPeca);
        inicializarInterface();
        exibirListaPecas();
    }

    //Inicializar os objetos da interface e adiciona o comando de fechar o dialogo no botão X
    private void inicializarInterface(){
        button_fechar_dialogfragment = root.findViewById(R.id.button_fechar_dialogfragment);
        button_fechar_dialogfragment.setOnClickListener(v -> getDialog().dismiss());
        inicializarRecyclerView();
    }


    //Iniciailiza o recyclerView
    private void inicializarRecyclerView() {
        adapter = new ExibirPecasAdapter();
        recyclerView = root.findViewById(R.id.recyclerViewPecas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);
    }

    //Coloca a lista de peças no adapter do recyclewView
    private void exibirListaPecas(){
        controller.getPecas().observe(this, users -> {
            adapter.definirPecas(controller.getPecas().getValue());
        });
    }

    //Define o tamanho do DialogFragment na tela
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}
