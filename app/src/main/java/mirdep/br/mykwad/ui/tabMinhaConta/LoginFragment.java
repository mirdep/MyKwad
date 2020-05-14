package mirdep.br.mykwad.ui.tabMinhaConta;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.FormatarEditText;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;

public class LoginFragment extends Fragment {

    private EditText editText_login_email;
    private EditText editText_login_senha;
    private Button button_login_entrar;
    private TextView button_login_criarconta;
    private View button_login_senha_visualizar;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        inicializarInterface();
        inicializarVariaveis();
        adicionarListeners();
        autoFormatarEditText();
        return root;
    }

    private void inicializarInterface() {
        editText_login_email = root.findViewById(R.id.editText_login_email);
        editText_login_senha = root.findViewById(R.id.editText_login_senha);
        button_login_entrar = root.findViewById(R.id.button_login_entrar);
        button_login_criarconta = root.findViewById(R.id.button_login_criarconta);
        button_login_senha_visualizar = root.findViewById(R.id.button_login_senha_visualizar);
    }

    private void inicializarVariaveis() {

    }

    private void adicionarListeners() {
        button_login_entrar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                efetuarLogin();
            }
        });

        button_login_criarconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseApp) getActivity()).abrirRegistrarConta();
            }
        });

        button_login_senha_visualizar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_login_senha.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    editText_login_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    editText_login_senha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void autoFormatarEditText() {
        editText_login_email = FormatarEditText.editTextEmail(editText_login_email);
    }

    private void efetuarLogin() {
        AutenticacaoRepositorio authRepository = new AutenticacaoRepositorio();
        String email = editText_login_email.getText().toString();
        String senha = editText_login_senha.getText().toString();
        authRepository.loginConta(email, senha);
    }


}
