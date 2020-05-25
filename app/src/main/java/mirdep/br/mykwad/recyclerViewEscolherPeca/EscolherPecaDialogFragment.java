package mirdep.br.mykwad.recyclerViewEscolherPeca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;
import mirdep.br.mykwad.drones.PecaRepositorio;

public class EscolherPecaDialogFragment extends DialogFragment {

    private View root;

    private RecyclerView recyclerViewPecas;
    private final ArrayList<Peca> listaPecas = new ArrayList<>();

    private String tipoPeca;

    public EscolherPecaDialogFragment(String tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialogfragment_pesquisar_pecas, container, false);
        carregarListaPecas();

        return root;
    }

    private void inicializarRecyclerView() {
        recyclerViewPecas = root.findViewById(R.id.recyclerViewPecas);
        recyclerViewPecas.setHasFixedSize(true);
        recyclerViewPecas.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewPecas.setAdapter(new ExibirPecasAdapter(listaPecas));
    }

    private void carregarListaPecas() {
        PecaRepositorio.getPecaDatabaseReference(tipoPeca).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        coletarPecas((Map<String, Object>) dataSnapshot.getValue());
                        inicializarRecyclerView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }

    private void coletarPecas(Map<String,Object> pecas) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : pecas.entrySet()){
            //Get user map
            Map pecaInfo = (Map) entry.getValue();

            //Get phone field and append to list
            String marca = (String) pecaInfo.get("marca");
            String modelo = (String) pecaInfo.get("modelo");
            Peca peca = new Peca(marca, modelo);
            listaPecas.add(peca);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}
