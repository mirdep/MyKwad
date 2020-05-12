package mirdep.br.mykwad.usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
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

public class RegistrarFragment extends Fragment {

    private EditText editText_registrar_email;
    private EditText editText_registrar_nome;
    private EditText editText_registrar_usuario;
    private EditText editText_registrar_senha;
    private Button button_registrar_criarconta;
    private View button_registrar_sair;
    private View button_registrar_senha_visualizar;

    private View root;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_registrar, container, false);
        inicializarInterface();
        inicializarVariaveis();
        adicionarListeners();
        return root;
    }

    private void inicializarVariaveis(){
        mAuth = FirebaseAuth.getInstance();
    }

    private void inicializarInterface(){
        editText_registrar_email = root.findViewById(R.id.editText_registrar_email);
        editText_registrar_nome = root.findViewById(R.id.editText_registrar_nome);
        editText_registrar_usuario = root.findViewById(R.id.editText_registrar_usuario);
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
                ((BaseApp) getActivity()).abrirMinhaConta();
            }
        });
    }

    private void criarConta(){
        AutenticacaoRepositorio authRepository = new AutenticacaoRepositorio();
        String email = editText_registrar_email.getText().toString();
        String senha = editText_registrar_senha.getText().toString();
        authRepository.criarConta(email, senha);
    }
}
