package mirdep.br.mykwad.interfaces;

import java.util.List;

import mirdep.br.mykwad.objetos.Usuario;

public interface LoadUsuariosListener {
    void finalizado(List<Usuario> usuarios);
}
