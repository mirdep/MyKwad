package mirdep.br.mykwad.viewmodels;

import androidx.lifecycle.ViewModel;

import java.util.List;

import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class MinhaContaViewModel extends ViewModel {

    public void getUsuarioAtual(FirebaseCallback<Usuario> listener) {
        UsuarioRepositorio.getInstance().getUsuarioLogado(usuario -> {
            listener.finalizado(usuario);
        });
    }

    public void getDronesDoUsuario(FirebaseCallback<List<Drone>> listener) {
        DroneRepositorio.getInstance().getDronesPorUsuario(UsuarioAuthentication.getInstance().getUsuarioId(), dronesDoUsuario -> {
            listener.finalizado(dronesDoUsuario);
        });
    }
}
