package mirdep.br.mykwad.usuario;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mirdep.br.mykwad.bancoDeDados.Firebase;

public class AutenticacaoRepositorio {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    public AutenticacaoRepositorio() {
        inicializarVariaveis();
    }

    private void inicializarVariaveis() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUsuario(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void criarConta(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                }
            }
        });
    }

    public void loginConta(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                });
    }
}
