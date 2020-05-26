package mirdep.br.mykwad.recyclerViewEscolherPeca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;
import mirdep.br.mykwad.drones.PecaRepositorio;

public class EscolherPecaDialogFragment extends DialogFragment {

    private View root;

    private RecyclerView recyclerViewPecas;
    private View button_fechar_dialogfragment;

    private final ArrayList<Peca> listaPecas = new ArrayList<>();
    private String tipoPeca;

    public EscolherPecaDialogFragment(String tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialogfragment_pesquisar_pecas, container, false);
        carregarListaPecas();
        inicializarInterface();
        return root;
    }

    //Inicializar os objetos da interface e adiciona o comando de fechar o dialogo no botão X
    private void inicializarInterface(){
        button_fechar_dialogfragment = root.findViewById(R.id.button_fechar_dialogfragment);
        button_fechar_dialogfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }


    //Iniciailiza o recyclerView e adicionar o Adapter para exibir as peças
    private void inicializarRecyclerView() {
        recyclerViewPecas = root.findViewById(R.id.recyclerViewPecas);
        recyclerViewPecas.setHasFixedSize(true);
        recyclerViewPecas.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewPecas.setAdapter(new ExibirPecasAdapter(listaPecas));
    }

    //Carrega as peças do BancoDeDados na listaPecas e depois inicializa o RecyclerView com essa lista
    private void carregarListaPecas() {
        PecaRepositorio.getPecaDatabaseReference(tipoPeca).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Puxa cada peça do BancoDeDados para a lista
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            listaPecas.add(dsp.getValue(Peca.class)); //add result into array list
                        }
                        inicializarRecyclerView();
                    }

                    //Se der errado ao ler do BancoDeDados ele exibe mensagem de erro na tela
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(root.getContext(), "Não foi possível carregar a lista de peças", Toast.LENGTH_LONG);
                    }
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
