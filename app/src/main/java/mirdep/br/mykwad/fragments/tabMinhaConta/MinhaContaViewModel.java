package mirdep.br.mykwad.fragments.tabMinhaConta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.interfaces.LoadUsuarioListener;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class MinhaContaViewModel extends ViewModel {

    public void getUsuarioAtual(LoadUsuarioListener listener) {
        UsuarioRepositorio.getInstance().getUsuarioLogado(usuario -> {
            listener.finalizado(usuario);
        });
    }

    public LiveData<List<Drone>> getDronesDoUsuario() {
        return DroneRepositorio.getInstance().getDronesPorUsuario(UsuarioAuthentication.getInstance().getUsuarioId());
    }
}
