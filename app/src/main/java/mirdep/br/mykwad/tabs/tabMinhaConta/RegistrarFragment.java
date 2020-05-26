package mirdep.br.mykwad.tabs.tabMinhaConta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.usuario.Usuario;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;

public class RegistrarFragment extends Fragment {

    private EditText editText_registrar_email;
    private EditText editText_registrar_nome;
    private EditText editText_registrar_nickname;
    private EditText editText_registrar_senha;
    private Button button_registrar_criarconta;
    private View button_registrar_sair;
    private View button_registrar_senha_visualizar;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_registrar, container, false);
        inicializarInterface();
        adicionarListeners();

        return root;
    }

    private void inicializarInterface(){
        editText_registrar_email = root.findViewById(R.id.editText_registrar_email);
        editText_registrar_nome = root.findViewById(R.id.editText_registrar_nome);
        editText_registrar_nickname = root.findViewById(R.id.editText_registrar_nickname);
        editText_registrar_senha = root.findViewById(R.id.editText_registrar_senha);
        button_registrar_criarconta = root.findViewById(R.id.button_registrar_criarconta);
        button_registrar_sair = root.findViewById(R.id.button_registrar_sair);
        button_registrar_senha_visualizar = root.findViewById(R.id.button_registrar_senha_visualizar);
    }

    private void adicionarListeners(){
        button_registrar_criarconta.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarConta();
            }
        });

        button_registrar_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseApp) getActivity()).abrirTabMinhaConta();
            }
        });

    }

    private void criarConta(){
        UsuarioRepositorio.getUsuariosDatabaseReference().child(editText_registrar_nickname.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    editText_registrar_nickname.setError("Nome de usuário não disponível");
                } else {
                    registrarContaFirebase();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void registrarContaFirebase(){
        if(verificarCamposVazios() && campoNicknameOk()){
            String email = editText_registrar_email.getText().toString();
            String senha = editText_registrar_senha.getText().toString();
            String nome = editText_registrar_nome.getText().toString();
            String nickname = editText_registrar_nickname.getText().toString();
            final Usuario usuario = new Usuario(email, nickname, nome);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(usuario.getNickname()).build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("SALVAR USUARIO DB", "User profile updated.");
                                        } else {
                                            Log.d("DEU RUIM", "User profile updated.");
                                        }
                                    }
                                });

                        UsuarioRepositorio.salvarNoBanco(usuario);
                        ((BaseApp) getActivity()).abrirTabMinhaConta();
                    } else {
                        mostrarErrosTela(task);
                    }
                }
            });
        }
    }

    private boolean campoNicknameOk(){
        boolean campoNicknameOk = false;
        String nickname = editText_registrar_nickname.getText().toString();
        if(nickname.length() < 6){
            editText_registrar_nickname.setError("Seu usuário deve conter pelo menos 6 caractéres");
        } else {
            if(UsuarioRepositorio.nicknameJaExiste(nickname)){
                editText_registrar_nickname.setError("Esse usuário já foi utilizado");
            } else {
                campoNicknameOk = true;
            }
        }
        return campoNicknameOk;
    }

    private boolean verificarCamposVazios(){
        boolean camposOk = true;
        if(!stringValida(editText_registrar_email.getText().toString())){
            editText_registrar_email.setError("Complete este campo!");
            camposOk = false;
        }
        if(!stringValida(editText_registrar_nome.getText().toString())){
            editText_registrar_nome.setError("Complete este campo!");
            camposOk = false;
        }
        if(!stringValida(editText_registrar_nickname.getText().toString())){
            editText_registrar_nickname.setError("Complete este campo!");
            camposOk = false;
        }
        if(!stringValida(editText_registrar_senha.getText().toString())){
            editText_registrar_senha.setError("Complete este campo!");
            camposOk = false;
        }
        return camposOk;
    }

    private boolean stringValida(String string){
        return !(string.equals("") || string == null);
    }
    
    private void mostrarErrosTela(@NonNull Task<AuthResult> task){
        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

        switch (errorCode) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(root.getContext(), "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(root.getContext(), "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(root.getContext(), "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(root.getContext(), "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                editText_registrar_email.setError("The email address is badly formatted.");
                editText_registrar_email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(root.getContext(), "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                editText_registrar_senha.setError("password is incorrect ");
                editText_registrar_senha.requestFocus();
                editText_registrar_senha.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(root.getContext(), "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(root.getContext(), "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(root.getContext(), "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(root.getContext(), "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                editText_registrar_email.setError("The email address is already in use by another account.");
                editText_registrar_email.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(root.getContext(), "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(root.getContext(), "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(root.getContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(root.getContext(), "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(root.getContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(root.getContext(), "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(root.getContext(), "The given password is invalid.", Toast.LENGTH_LONG).show();
                editText_registrar_senha.setError("The password is invalid it must 6 characters at least");
                editText_registrar_senha.requestFocus();
                break;

        }
    }
}
