package mirdep.br.mykwad.main_tabs.tabMinhaConta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class MinhaContaViewModel extends ViewModel {

    public LiveData<Usuario> getUsuario() {
        return UsuarioRepositorio.getInstance().getUsuarioLogado();
    }

    public LiveData<List<Drone>> getDronesDoUsuario() {
        return DroneRepositorio.getInstance().getDronesPorUsuario(UsuarioAuthentication.getInstance().getUsuarioId());
    }
}
