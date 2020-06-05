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

import mirdep.br.mykwad.objetos.Usuario;

public class UsuarioRepositorio {

    private static UsuarioRepositorio INSTANCE;
    private static final String LOG_TAG = "[UsuarioRepositorio]";
    private static MutableLiveData<Usuario> usuario;

    public static UsuarioRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UsuarioRepositorio();
        return INSTANCE;
    }
    
    public DatabaseReference getUsuariosReference(){
        return FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public void salvar(Usuario usuario) {
        if(usuario.getId() == null){
            usuario.setId(getUsuariosReference().push().getKey());
        }
        getUsuariosReference().child(usuario.getId()).setValue(usuario);
        UsuarioAuthentication.getInstance().atualizarAuth(usuario);
    }

    public LiveData<Usuario> getUsuario() {
        usuario = new MutableLiveData<>();
        carregarDoBanco();
        return usuario;
    }

    private void carregarDoBanco() {
        String usuarioId = UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName();
        if(usuarioId != null){
            DatabaseReference usuarioReference = UsuarioRepositorio.getInstance().getUsuariosReference().child(usuarioId);
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
            carregarDoBanco();
        }
    }

    public LiveData<String> getNicknameById(String id){
        MutableLiveData<String> nickname = new MutableLiveData<>();
        getUsuariosReference().child(id).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
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
