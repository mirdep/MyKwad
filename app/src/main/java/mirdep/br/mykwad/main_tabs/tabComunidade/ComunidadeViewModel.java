package mirdep.br.mykwad.main_tabs.tabComunidade;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.StorageReference;

import java.util.List;

import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.comum.Configs;

public class ComunidadeViewModel extends ViewModel {

    public LiveData<List<Drone>> getTodosDrones() {
        return DroneRepositorio.getInstance().getTodosDrones();
    }

    public StorageReference getFotoDroneReference(Drone drone){
        return DroneRepositorio.getInstance().getStorageReference().child(drone.getId()).child(0+Configs.EXTENSAO_IMAGEM);
    }
}
