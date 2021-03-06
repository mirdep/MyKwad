package mirdep.br.mykwad.fragments.tabMinhaConta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.MyDialog;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.NicknameRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class RegistrarFragment extends Fragment {

    private TextInputLayout editText_registrar_email;
    private TextInputLayout editText_registrar_nome;
    private TextInputLayout editText_registrar_nickname;
    private TextInputLayout editText_registrar_senha;
    private Button button_registrar_criarconta;
    private View button_registrar_sair;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_registrar, container, false);
        inicializarInterface();
        adicionarListeners();

        return root;
    }

    private void inicializarInterface() {
        editText_registrar_email = root.findViewById(R.id.editText_registrar_email);
        editText_registrar_nome = root.findViewById(R.id.editText_registrar_nome);
        editText_registrar_nickname = root.findViewById(R.id.editText_registrar_nickname);
        editText_registrar_senha = root.findViewById(R.id.editText_registrar_senha);
        button_registrar_criarconta = root.findViewById(R.id.button_registrar_criarconta);
        button_registrar_sair = root.findViewById(R.id.button_registrar_sair);
    }

    private void adicionarListeners() {
        button_registrar_criarconta.setOnClickListener(v -> criarConta());

        button_registrar_sair.setOnClickListener(v -> ((BaseApp) getActivity()).abrirTabMinhaConta());

        editText_registrar_nickname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_registrar_nickname.setError(null);
            }
        });
    }

    private void criarConta() {
        String novoNickname = editText_registrar_nickname.getEditText().getText().toString();
        NicknameRepositorio.getInstance().nicknameDisponivel(novoNickname, disponivel -> {
            if (disponivel) {
                salvarUsuario();
            } else {
                editText_registrar_nickname.setError("Usuário indisponível!");
            }
        });
    }

    private void salvarUsuario(){
        if (verificarCamposVazios() && campoNicknameOk()) {
            registrarConta();
        }
    }

    private void registrarConta() {
        final ProgressDialog dialog = MyDialog.criarProgressDialog(root.getContext(), "Criando nova conta...");
        dialog.show();
        final Usuario usuario = gerarUsuario();
        String senha = editText_registrar_senha.getEditText().getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UsuarioRepositorio.getInstance().inserir(usuario, objeto -> {
                    dialog.dismiss();
                    ((BaseApp) getActivity()).abrirTabMinhaConta();
                });
            } else {
                dialog.dismiss();
                mostrarErrosTela(task);
            }
        });
    }

    private Usuario gerarUsuario(){
        final Usuario usuario = new Usuario();
        usuario.setEmail(editText_registrar_email.getEditText().getText().toString());
        usuario.setNome(editText_registrar_nome.getEditText().getText().toString());
        usuario.setNickname(editText_registrar_nickname.getEditText().getText().toString());
        return usuario;
    }

    private boolean campoNicknameOk() {
        boolean campoNicknameOk = false;
        String nickname = editText_registrar_nickname.getEditText().getText().toString();
        if (nickname.length() < 6) {
            editText_registrar_nickname.setError("Seu usuário deve conter pelo menos 6 caractéres");
        } else {
            if (1 == 0) { //UsuarioRepositorio.nicknameJaExiste(nickname)
                editText_registrar_nickname.setError("Esse usuário já foi utilizado");
            } else {
                campoNicknameOk = true;
            }
        }
        return campoNicknameOk;
    }

    private boolean verificarCamposVazios() {
        boolean camposOk = true;
        if (!stringValida(editText_registrar_email.getEditText().getText().toString())) {
            editText_registrar_email.setError("Complete este campo!");
            camposOk = false;
        }
        if (!stringValida(editText_registrar_nome.getEditText().getText().toString())) {
            editText_registrar_nome.setError("Complete este campo!");
            camposOk = false;
        }
        if (!stringValida(editText_registrar_nickname.getEditText().getText().toString())) {
            editText_registrar_nickname.setError("Complete este campo!");
            camposOk = false;
        }
        if (!stringValida(editText_registrar_senha.getEditText().getText().toString())) {
            editText_registrar_senha.setError("Complete este campo!");
            camposOk = false;
        }
        return camposOk;
    }

    private boolean stringValida(String string) {
        return !(string.equals("") || string == null);
    }

    private void mostrarErrosTela(@NonNull Task<AuthResult> task) {
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
                editText_registrar_senha.getEditText().setText("");
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
