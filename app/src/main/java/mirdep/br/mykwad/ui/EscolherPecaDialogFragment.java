package mirdep.br.mykwad.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.objetos.Peca;
import mirdep.br.mykwad.ui.carrosselFragment.CarrosselPecaFragment;
import mirdep.br.mykwad.ui.escolherPeca_dialogFragment.Controller_EscolherPeca;
import mirdep.br.mykwad.ui.escolherPeca_dialogFragment.ExibirPecasAdapter;

public class EscolherPecaDialogFragment extends DialogFragment {
    private static final String NOME_LOG = "[EscolherPeca]";

    private View root;

    private RecyclerView recyclerView;
    private ExibirPecasAdapter adapter;
    private View button_fechar_dialogfragment;

    private String tipoPeca;
    private Controller_EscolherPeca controller;
    private CarrosselPecaFragment parent;

    public EscolherPecaDialogFragment(String tipoPeca, CarrosselPecaFragment parent) {
        Log.d(NOME_LOG, "View criado!");
        this.parent = parent;
        this.tipoPeca = tipoPeca;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialogfragment_escolherpeca, container, false);
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
        button_fechar_dialogfragment.setOnClickListener(v -> fecharDialog());
        inicializarRecyclerView();
    }

    public void fecharDialog(Peca peca){
        fecharDialog();
        parent.setPecaEscolhida(peca);
    }

    public void fecharDialog(){
        getDialog().dismiss();
        Log.d(NOME_LOG,"MyDialog fechado!");
    }

    //Iniciailiza o recyclerView
    private void inicializarRecyclerView() {
        adapter = new ExibirPecasAdapter(this);
        recyclerView = root.findViewById(R.id.recyclerViewPecas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        recyclerView.setAdapter(adapter);
    }

    //Coloca a lista de peças no adapter do recyclewView
    private void exibirListaPecas(){
        controller.getPecas(pecas -> {
            adapter.definirPecas(pecas);
            root.findViewById(R.id.loadingIcone).setVisibility(View.GONE);
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
