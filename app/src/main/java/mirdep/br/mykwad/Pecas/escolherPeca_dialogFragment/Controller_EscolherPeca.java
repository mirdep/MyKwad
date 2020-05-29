package mirdep.br.mykwad.Pecas.escolherPeca_dialogFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.Pecas.Peca;
import mirdep.br.mykwad.Pecas.PecaRepositorio;

public class Controller_EscolherPeca extends ViewModel {
    private static final String NOME_LOG = "[EscolherPeca]";

    private MutableLiveData<List<Peca>> pecas;
    private List<Peca> listaPecas;

    private String tipoPeca;


    public Controller_EscolherPeca(String tipoPeca){
        Log.d(NOME_LOG, "Controller criado!");
        this.tipoPeca = tipoPeca;
    }

    public LiveData<List<Peca>> getPecas(){
        if(pecas == null){
            pecas = new MutableLiveData<>();
            loadPecas();
        }
        return pecas;
    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
    private void loadPecas(){
        listaPecas = new ArrayList<>();
        PecaRepositorio.getInstance().getPecaReference(tipoPeca).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(NOME_LOG, "Carregando lista de "+tipoPeca+"...");

                        //Lê todas as peças do BD e adiciona 1 por 1 na lista
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            listaPecas.add(dsp.getValue(Peca.class)); //add result into array list
                            pecas.postValue(listaPecas);
                        }
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(NOME_LOG, "ERRO! Ao carregar peças do tipo: "+tipoPeca+"...");

                    }
                });
    }
}
