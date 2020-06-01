package mirdep.br.mykwad.usuario;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import mirdep.br.mykwad.BaseApp;

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

    public void atualizarAuth(Usuario usuario){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(usuario.getId()).build();
        UsuarioAuthentication.getInstance().getUsuarioAuth().updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "Atualizado no FirebaseAuth.");
                        UsuarioRepositorio.getInstance().atualizarBanco(usuario);
                    } else {
                        Log.d(LOG_TAG, "Erro ao atualizar no FirebaseAuth.");
                    }
                });
    }
}
