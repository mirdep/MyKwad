package mirdep.br.mykwad.tabs.tabComunidade;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.StorageReference;

import java.util.List;

import mirdep.br.mykwad.DRONES.Drone;
import mirdep.br.mykwad.DRONES.DroneRepositorio;
import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.storage.ImagemRepositorio;

public class ComunidadeViewModel extends ViewModel {

    public LiveData<List<Drone>> getTodosDrones() {
        return DroneRepositorio.getInstance().getTodosDrones();
    }

    public StorageReference getFotoDroneReference(Drone drone){
        return DroneRepositorio.getInstance().getStorageReference().child(drone.getId()).child(0+Configs.EXTENSAO_IMAGEM);
    }
}
