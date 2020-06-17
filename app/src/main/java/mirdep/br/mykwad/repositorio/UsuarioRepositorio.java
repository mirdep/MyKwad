package mirdep.br.mykwad.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Usuario;

public class UsuarioRepositorio {

    private static final String LOG_TAG = "[UsuarioRepositorio]";

    private static UsuarioRepositorio INSTANCE;

    private Usuario usuario;

    private final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/usuarios");
    private final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("usuarios");

    public static UsuarioRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UsuarioRepositorio();
        return INSTANCE;
    }

    public DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public StorageReference getStorageReference() {
        return REFERENCIA_STORAGE;
    }

    public void inserir(Usuario usuario) {
        inserir(usuario, objeto -> {
        });
    }

    public void inserir(Usuario usuario, FirebaseCallback<Boolean> listener) {
        if (usuario.getTempoCriacao() == null || usuario.getTempoCriacao().length() < 5) {
            usuario.setTempoCriacao(String.valueOf(Timestamp.now().getSeconds()));
        }
        if (usuario.getId() == null || usuario.getId().trim().length() == 0) {
            usuario.setId(getDatabaseReference().push().getKey());
        }
        Log.d(LOG_TAG, "Inserindo usuário \"" + usuario.getId());
        UsuarioAuthentication.getInstance().atualizarAuth(usuario, objeto ->
                getDatabaseReference().child(usuario.getId()).setValue(usuario).addOnSuccessListener(aVoid -> {
                    NicknameRepositorio.getInstance().inserir(usuario.getNickname(), usuario.getId());
                    if (usuario.retrieveFoto() != null) {
                        ImagemRepositorio.getInstance().uploadImagem(getStorageReference(), usuario.retrieveFoto(), usuario.getId() + Configs.EXTENSAO_IMAGEM);
                    }
                    listener.finalizado(true);
                }));
    }

    public void getUsuario(String idUsuario, FirebaseCallback<Usuario> listener) {
        Log.d(LOG_TAG, "Carregando usuario \"" + idUsuario + "\"");
        if (idUsuario != null) {
            DatabaseReference usuarioReference = UsuarioRepositorio.getInstance().getDatabaseReference().child(idUsuario);
            usuarioReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "SUCESSO! Ao carregar usuario " + idUsuario);
                            listener.finalizado(dataSnapshot.getValue(Usuario.class));

                        }

                        //Se der problema na leitura no BD
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(LOG_TAG, "ERRO! Ao carregar usuario " + idUsuario);
                        }
                    });
        } else {
            Log.d(LOG_TAG, "ERRO! UsuarioID não encontrado.");
        }
    }

    public void getUsuarioLogado(FirebaseCallback<Usuario> listener) {
        if (this.usuario != null) {
            listener.finalizado(this.usuario);
        } else {
            String idUsuario = UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName();
            getUsuario(idUsuario, usuario -> {
                this.usuario = usuario;
                listener.finalizado(usuario);
            });
        }
    }

    public void getTodosUsuarios(FirebaseCallback<List<Usuario>> listener) {
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Usuario> usuarios = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    usuarios.add(dsp.getValue(Usuario.class));
                }
                listener.finalizado(usuarios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
