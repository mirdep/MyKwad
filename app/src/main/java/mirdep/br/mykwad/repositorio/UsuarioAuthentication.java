package mirdep.br.mykwad.repositorio;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Usuario;

public class UsuarioAuthentication {
    private static UsuarioAuthentication INSTANCE;
    private static final String LOG_TAG = "[UsuarioAuthentication]";

    public static UsuarioAuthentication getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UsuarioAuthentication();
        return INSTANCE;
    }

    public FirebaseUser getUsuarioAuth() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void atualizarAuth(Usuario usuario, FirebaseCallback<String> listener){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(usuario.getId()).build();
        UsuarioAuthentication.getInstance().getUsuarioAuth().updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "Atualizado no FirebaseAuth.");
                        listener.finalizado(usuario.getId());
                    } else {
                        Log.d(LOG_TAG, "Erro ao atualizar no FirebaseAuth.");
                    }
                });
    }

    public boolean usuarioEstaLogado() {
        return getUsuarioAuth() != null;
    }

    public void logoutConta() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getUsuarioId(){
        return getUsuarioAuth().getDisplayName();
    }
}
