package mirdep.br.mykwad.PECAS.escolherPeca_dialogFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.PECAS.Peca;
import mirdep.br.mykwad.PECAS.PecaRepositorio;

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
