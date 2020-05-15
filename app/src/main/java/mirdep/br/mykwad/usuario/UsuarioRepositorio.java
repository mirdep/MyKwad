package mirdep.br.mykwad.usuario;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.bancoDeDados.FirebaseRepositorio;

public class UsuarioRepositorio {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    public UsuarioRepositorio() {
        inicializarVariaveis();
    }

    private void inicializarVariaveis() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void atualizarNoBanco(Usuario usuario){
        FirebaseRepositorio repositorio = new FirebaseRepositorio();
        String usuarioID = getUsuario().getUid();
        repositorio.getBancoDeDados().child("usuarios").child(usuarioID).setValue(usuario);
    }

    public void loginConta(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                }
            }
        });

    }

    //============================ MÉTODOS ESTÁTICOS =================================

    public static FirebaseUser getUsuario() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static boolean usuarioLogado(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static void logoutConta(){
        FirebaseAuth.getInstance().signOut();
    }
}
