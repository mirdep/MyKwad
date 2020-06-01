package mirdep.br.mykwad.DRONES.escolherDrone_dialogFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.DRONES.Drone;
import mirdep.br.mykwad.DRONES.DroneRepositorio;

public class Controller_EscolherDrone extends ViewModel {
    private static final String NOME_LOG = "[EscolherDrone]";

    public Controller_EscolherDrone(){
        Log.d(NOME_LOG, "Controller criado!");
    }

    public LiveData<List<Drone>> getDrones(){
        return DroneRepositorio.getInstance().getDrones();
    }

}
