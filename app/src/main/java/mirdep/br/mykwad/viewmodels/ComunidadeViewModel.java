package mirdep.br.mykwad.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;

public class ComunidadeViewModel extends ViewModel {

    public void getTodosDrones(FirebaseCallback<List<Drone>> listener) {
        DroneRepositorio.getInstance().getTodosDrones(drones -> listener.finalizado(drones));
    }

}
