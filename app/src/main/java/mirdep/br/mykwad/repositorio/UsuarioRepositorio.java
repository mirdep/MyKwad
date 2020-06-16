package mirdep.br.mykwad.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
        if (usuario.getTempoCriacao() == null || usuario.getTempoCriacao().length() < 5) {
            usuario.setTempoCriacao(String.valueOf(Timestamp.now().getSeconds()));
        }
        if (usuario.getId() == null || usuario.getId().length() < 2) {
            usuario.setId(getDatabaseReference().push().getKey());
        }
        Log.d(LOG_TAG, "Inserindo usuário \""+usuario.getId());
        getDatabaseReference().child(usuario.getId()).setValue(usuario);
        UsuarioAuthentication.getInstance().atualizarAuth(usuario);
        NicknameRepositorio.getInstance().inserir(usuario.getNickname(), usuario.getId());
        if (usuario.retrieveFoto() != null) {
            ImagemRepositorio.getInstance().uploadImagem(getStorageReference(), usuario.retrieveFoto(), usuario.getId() + Configs.EXTENSAO_IMAGEM);
        }
    }

    public void registrarNovo(Usuario usuario){
        this.usuario = usuario;
        inserir(usuario);
    }

    public MutableLiveData<Usuario> getUsuarioLogado() {
        String idUsuario = UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName();
        return getUsuario(idUsuario);
    }

    public MutableLiveData<Usuario> getUsuario(String idUsuario) {
        Log.d(LOG_TAG,"getUsuario :"+idUsuario);
        MutableLiveData<Usuario> usuario = new MutableLiveData<>();
        if (idUsuario != null) {
            DatabaseReference usuarioReference = UsuarioRepositorio.getInstance().getDatabaseReference().child(idUsuario);
            usuarioReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "SUCESSO! Ao carregar usuario " + idUsuario);
                            usuario.postValue(dataSnapshot.getValue(Usuario.class));
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
        return usuario;
    }

    public MutableLiveData<List<Usuario>> getTodosUsuarios() {
        MutableLiveData<List<Usuario>> todosUsuarios = new MutableLiveData<>();
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Usuario> usuarios = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    usuarios.add(dsp.getValue(Usuario.class));
                    todosUsuarios.postValue(usuarios);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return todosUsuarios;
    }

}
