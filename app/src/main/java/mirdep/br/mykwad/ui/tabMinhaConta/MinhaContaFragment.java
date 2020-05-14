package mirdep.br.mykwad.ui.tabMinhaConta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;
import mirdep.br.mykwad.usuario.Usuario;

public class MinhaContaFragment extends Fragment {

    private View root;

    private Usuario usuario;

    private EditText editText_minhaconta_usuario;
    private EditText editText_minhaconta_email;
    private EditText editText_minhaconta_nome;
    private EditText editText_minhaconta_nascimento;

    private Button button_minhaconta_logout;
    private Button button_minhaconta_editar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);

        inicializarInterface();
        adicionarListeners();
        root.findViewById(R.id.button_minhaconta_logout).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return root;
    }

    private void inicializarInterface(){
        editText_minhaconta_usuario = root.findViewById(R.id.editText_minhaconta_usuario);
        editText_minhaconta_email = root.findViewById(R.id.editText_minhaconta_email);
        editText_minhaconta_nome = root.findViewById(R.id.editText_minhaconta_nome);
        editText_minhaconta_nascimento = root.findViewById(R.id.editText_minhaconta_nascimento);

        button_minhaconta_logout = root.findViewById(R.id.button_minhaconta_logout);
        button_minhaconta_editar = root.findViewById(R.id.button_minhaconta_editar);
    }

    private void adicionarListeners(){
        button_minhaconta_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        button_minhaconta_editar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarConta();
            }
        });

    }

    private void atualizarTela(){
        String nome = AutenticacaoRepositorio.getUsuario().getDisplayName();
        editText_minhaconta_nome.setText(nome);
    }

    private void editarConta(){
        if(button_minhaconta_editar.getText().toString().toLowerCase().equals("editar")){
            button_minhaconta_editar.setText("salvar");
            bloquearCamposDeEdicao(true);
        } else {
            salvarUsuario();
            button_minhaconta_editar.setText("editar");
            bloquearCamposDeEdicao(false);
        }
    }

    private void bloquearCamposDeEdicao(boolean bloquear){
        editText_minhaconta_usuario.setFocusable(bloquear);
        editText_minhaconta_email.setFocusable(bloquear);
        editText_minhaconta_nome.setFocusable(bloquear);
        editText_minhaconta_nascimento.setFocusable(bloquear);
    }

    private void salvarUsuario(){
        String nome = editText_minhaconta_nome.getText().toString();
        String nascimento = editText_minhaconta_nascimento.getText().toString();
        UserProfileChangeRequest novosDadosUsuario = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();
        AutenticacaoRepositorio.getUsuario().updateProfile(novosDadosUsuario);
    }

    private void logout(){
        AutenticacaoRepositorio.logoutConta();
    }
}