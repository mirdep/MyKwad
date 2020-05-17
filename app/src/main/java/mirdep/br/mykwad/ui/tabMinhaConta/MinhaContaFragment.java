package mirdep.br.mykwad.ui.tabMinhaConta;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.storage.GlideApp;
import mirdep.br.mykwad.usuario.Usuario;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;

public class MinhaContaFragment extends Fragment {

    private Usuario usuario;

    private View root;

    private TextView textView_usuario_nome;
    private TextView textView_usuario_email;
    private TextView textView_usuario_nickname;

    private ImageView imageView_usuario_foto;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        inicializarInterface();
        inicializarVariaveis();
        adicionarListeners();
        atualizarTela();
        return root;
    }

    private void inicializarInterface() {
        textView_usuario_email = root.findViewById(R.id.textView_usuario_email);
        textView_usuario_nome = root.findViewById(R.id.textView_usuario_nome);
        textView_usuario_nickname = root.findViewById(R.id.textView_usuario_nickname);

        imageView_usuario_foto = root.findViewById(R.id.imageView_usuario_foto);
    }

    private void adicionarListeners() {
    }

    private void inicializarVariaveis() {
    }

    private void atualizarTela() {
        carregarUsuario();
        textView_usuario_nickname.setText(usuario.getNickname());
        textView_usuario_nome.setText(usuario.getNome());
        textView_usuario_email.setText(usuario.getEmail());
        carregarFoto();
    }

    private void carregarUsuario() {
        usuario = new Usuario();
        String nickname;
        while ((nickname = UsuarioRepositorio.getUsuarioAuth().getDisplayName()) == null) {

        }
        UsuarioRepositorio.getUsuariosDatabaseReference().child(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario.setEmail(dataSnapshot.child("email").getValue().toString());
                usuario.setNome(dataSnapshot.child("nome").getValue().toString());
                usuario.setNickname(UsuarioRepositorio.getUsuarioAuth().getDisplayName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void carregarFoto() {
        Uri uri;
        if ((uri = UsuarioRepositorio.getUsuarioAuth().getPhotoUrl()) != null) {
            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            GlideApp.with(this /* context */)
                    .load(storageReference)
                    .into(imageView_usuario_foto);
        }
    }

//    private void editarConta(){
//        if(button_minhaconta_editar.getText().toString().toLowerCase().equals("editar")){
//            button_minhaconta_editar.setText("salvar");
//            bloquearCamposDeEdicao(false);
//        } else {
//            Toast.makeText(root.getContext(), "AINDA NAO TEM FUNCIONALIDADE", Toast.LENGTH_LONG).show();
//            button_minhaconta_editar.setText("editar");
//            bloquearCamposDeEdicao(true);
//        }
//    }
//
//    private void bloquearCamposDeEdicao(boolean bloquear){
//        if(bloquear){
//            editText_minhaconta_nickname.setKeyListener(null);
//            editText_minhaconta_email.setKeyListener(null);
//            editText_minhaconta_nome.setKeyListener(null);
//        } else {
//            KeyListener keyListener = new EditText(root.getContext().getApplicationContext()).getKeyListener();
//            editText_minhaconta_nickname.setKeyListener(keyListener);
//            editText_minhaconta_email.setKeyListener(keyListener);
//            editText_minhaconta_nome.setKeyListener(keyListener);
//        }
//    }
}