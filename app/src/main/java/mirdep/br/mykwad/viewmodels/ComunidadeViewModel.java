package mirdep.br.mykwad.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;

public class ComunidadeViewModel extends ViewModel {

    public LiveData<List<Drone>> getTodosDrones() {
        return DroneRepositorio.getInstance().getTodosDrones();
    }

}
