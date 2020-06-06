package mirdep.br.mykwad.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.objetos.Usuario;

public class UsuarioRepositorio {

    private static UsuarioRepositorio INSTANCE;
    private static final String LOG_TAG = "[UsuarioRepositorio]";
    private static MutableLiveData<Usuario> usuario;

    private static final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/usuarios");
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("usuarios");

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

    public void salvar(Usuario usuario) {
        if(usuario.getId() == null){
            usuario.setId(getDatabaseReference().push().getKey());
        }
        getDatabaseReference().child(usuario.getId()).setValue(usuario);
        UsuarioAuthentication.getInstance().atualizarAuth(usuario);
        ImagemRepositorio.getInstance().uploadImagem(getStorageReference(), usuario.retrieveFoto(), usuario.getId()+ Configs.EXTENSAO_IMAGEM);
    }

    public LiveData<Usuario> getUsuario() {
        usuario = new MutableLiveData<>();
        String usuarioId = UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName();
        if(usuarioId != null){
            DatabaseReference usuarioReference = UsuarioRepositorio.getInstance().getDatabaseReference().child(usuarioId);
            usuarioReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "SUCESSO! Ao carregar usuario "+usuarioId);
                            usuario.postValue(dataSnapshot.getValue(Usuario.class));
                        }

                        //Se der problema na leitura no BD
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(LOG_TAG, "ERRO! Ao carregar usuario "+usuarioId);

                        }
                    });
        } else{
            Log.d(LOG_TAG, "ERRO! UsuarioID não encontrado.");
            getUsuario();
        }
        return usuario;
    }

    public LiveData<String> getNicknameById(String id){
        MutableLiveData<String> nickname = new MutableLiveData<>();
        getDatabaseReference().child(id).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nickname.postValue(((String) dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return nickname;
    }

}
