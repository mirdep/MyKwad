package mirdep.br.mykwad.ui.tabMinhaConta;

import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;

public class MinhaContaFragment extends Fragment {

    private View root;

    private EditText editText_minhaconta_nickname;
    private EditText editText_minhaconta_email;
    private EditText editText_minhaconta_nome;
    private Button button_minhaconta_logout;
    private Button button_minhaconta_editar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        inicializarInterface();
        bloquearCamposDeEdicao(true);
        inicializarVariaveis();
        adicionarListeners();
        atualizarTela();
        return root;
    }

    private void inicializarInterface(){
        editText_minhaconta_nickname = root.findViewById(R.id.editText_minhaconta_nickname);
        editText_minhaconta_email = root.findViewById(R.id.editText_minhaconta_email);
        editText_minhaconta_nome = root.findViewById(R.id.editText_minhaconta_nome);

        button_minhaconta_logout = root.findViewById(R.id.button_minhaconta_logout);
        button_minhaconta_editar = root.findViewById(R.id.button_minhaconta_editar);
    }

    private void adicionarListeners(){
        button_minhaconta_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioRepositorio.logoutConta();
                ((BaseApp) getActivity()).abrirTabMinhaConta();
            }
        });

        button_minhaconta_editar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarConta();
            }
        });
    }

    private void inicializarVariaveis(){

    }

    private void atualizarTela(){
        String nickname;
        while((nickname = UsuarioRepositorio.getUsuarioAuth().getDisplayName()) == null){

        }
        UsuarioRepositorio.getUsuariosDatabaseReference().child(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editText_minhaconta_email.setText(dataSnapshot.child("email").getValue().toString());
                editText_minhaconta_nome.setText(dataSnapshot.child("nome").getValue().toString());
                editText_minhaconta_nickname.setText(dataSnapshot.child("nickname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void editarConta(){
        if(button_minhaconta_editar.getText().toString().toLowerCase().equals("editar")){
            button_minhaconta_editar.setText("salvar");
            bloquearCamposDeEdicao(false);
        } else {
            Toast.makeText(root.getContext(), "AINDA NAO TEM FUNCIONALIDADE", Toast.LENGTH_LONG).show();
            button_minhaconta_editar.setText("editar");
            bloquearCamposDeEdicao(true);
        }
    }

    private void bloquearCamposDeEdicao(boolean bloquear){
        if(bloquear){
            editText_minhaconta_nickname.setKeyListener(null);
            editText_minhaconta_email.setKeyListener(null);
            editText_minhaconta_nome.setKeyListener(null);
        } else {
            KeyListener keyListener = new EditText(root.getContext().getApplicationContext()).getKeyListener();
            editText_minhaconta_nickname.setKeyListener(keyListener);
            editText_minhaconta_email.setKeyListener(keyListener);
            editText_minhaconta_nome.setKeyListener(keyListener);
        }
    }
}