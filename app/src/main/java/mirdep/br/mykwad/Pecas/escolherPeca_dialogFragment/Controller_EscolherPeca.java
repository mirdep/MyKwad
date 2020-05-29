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

    private String tipoPeca;


    public Controller_EscolherPeca(String tipoPeca){
        Log.d(NOME_LOG, "Controller criado!");
        this.tipoPeca = tipoPeca;
    }

    public LiveData<List<Peca>> getPecas(){
        return PecaRepositorio.getInstance().getPecas(tipoPeca);
    }

}
