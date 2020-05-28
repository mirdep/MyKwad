package mirdep.br.mykwad.Pecas.escolherPeca_dialogFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.Pecas.Peca;

final class Controller_EscolherPeca {
    private static final String NOME_COLECAO_BD = "pecas";
    private static final String NOME_LOG = "[EscolherPeca]";
    private String tipoPeca;

    private Model_EscolherPeca model;

    public Controller_EscolherPeca(String tipoPeca){
        Log.d(NOME_LOG, "Controller criado!");
        this.tipoPeca = tipoPeca;
        this.model = new Model_EscolherPeca(tipoPeca);
    }

    public LiveData<List<Peca>> getPecas(){
        return model.getPecas();
    }



}
