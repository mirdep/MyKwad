package mirdep.br.mykwad.ui.tabMinhaConta;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.BaseApp;
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

    private View viewButton_minhaconta_menu;

    private ImageView imageView_usuario_foto;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_minhaconta, container, false);
        inicializarInterface();
//        inicializarVariaveis();
        adicionarListeners();
        carregarUsuario();
        return root;
    }

    private void inicializarInterface() {
        textView_usuario_email = root.findViewById(R.id.textView_usuario_email);
        textView_usuario_email.setText("");
        textView_usuario_nome = root.findViewById(R.id.textView_usuario_nome);
        textView_usuario_nome.setText("");
        textView_usuario_nickname = root.findViewById(R.id.textView_usuario_nickname);
        textView_usuario_nickname.setText("");

        imageView_usuario_foto = root.findViewById(R.id.imageView_usuario_foto);

        viewButton_minhaconta_menu = root.findViewById(R.id.viewButton_minhaconta_menu);
    }

    private void adicionarListeners() {
        viewButton_minhaconta_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioRepositorio.logoutConta();
                ((BaseApp) getActivity()).abrirTabMinhaConta();
            }
        });
    }

    private void inicializarVariaveis() {
    }

    private void atualizarTela() {
        textView_usuario_nickname.setText(usuario.getNickname());
        textView_usuario_nome.setText(usuario.getNome());
        textView_usuario_email.setText(usuario.getEmail());
        carregarFoto();
    }

    private void carregarUsuario() {
        usuario = new Usuario();
        String nickname;
        while ((nickname = UsuarioRepositorio.getUsuarioAuth().getDisplayName()) == null) ;
        UsuarioRepositorio.getUsuariosDatabaseReference().child(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("email").getValue() != null) {
                    usuario.setEmail(dataSnapshot.child("email").getValue().toString());
                }
                if (dataSnapshot.child("nome").getValue() != null) {
                    usuario.setNome(dataSnapshot.child("nome").getValue().toString());
                }
                usuario.setNickname("@" + UsuarioRepositorio.getUsuarioAuth().getDisplayName());
                atualizarTela();
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
}