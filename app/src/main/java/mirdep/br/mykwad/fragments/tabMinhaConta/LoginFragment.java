package mirdep.br.mykwad.fragments.tabMinhaConta;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.MyDialog;

public class LoginFragment extends Fragment {

    private TextInputLayout editText_login_email;
    private TextInputLayout editText_login_senha;
    private Button button_login_entrar;
    private TextView button_login_criarconta;

    private ProgressDialog loadingDialog;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarInterface();
        inicializarVariaveis();
        adicionarListeners();
    }

    private void inicializarInterface() {
        loadingDialog = MyDialog.criarProgressDialog(root.getContext(),"Fazendo login...");
        editText_login_email = root.findViewById(R.id.editText_login_email);
        editText_login_senha = root.findViewById(R.id.editText_login_senha);
        button_login_entrar = root.findViewById(R.id.button_login_entrar);
        button_login_criarconta = root.findViewById(R.id.button_login_criarconta);
    }

    private void inicializarVariaveis() {

    }

    private void adicionarListeners() {
        button_login_entrar.setOnClickListener(v -> efetuarLogin());

        button_login_criarconta.setOnClickListener(v -> ((BaseApp) getActivity()).abrirTabRegistrar());
    }

    public void efetuarLogin() {
        fecharTeclado();
        if(verificarCampos()) {
            loadingDialog.show();
            String email = editText_login_email.getEditText().getText().toString();
            String senha = editText_login_senha.getEditText().getText().toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ((BaseApp) getActivity()).abrirTabMinhaConta();
                } else {
                    mostrarErrosTela(task);
                }
                loadingDialog.dismiss();
            });
        }

    }

    private boolean verificarCampos(){
        boolean camposOk = true;
        if(!stringValida(editText_login_email.getEditText().getText().toString())){
            editText_login_email.setError("Complete este campo!");
            camposOk = false;
        }
        if(!stringValida(editText_login_senha.getEditText().getText().toString())){
            editText_login_senha.setError("Complete este campo!");
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
                editText_login_email.setError("The email address is badly formatted.");
                editText_login_email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(root.getContext(), "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                editText_login_senha.setError("password is incorrect ");
                editText_login_senha.requestFocus();
                editText_login_senha.getEditText().setText("");
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
                editText_login_email.setError("The email address is already in use by another account.");
                editText_login_email.requestFocus();
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
                editText_login_senha.setError("The password is invalid it must 6 characters at least");
                editText_login_senha.requestFocus();
                break;

        }
    }

    private void fecharTeclado(){
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
    }
}
