package mirdep.br.mykwad.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Usuario;

public class NicknameRepositorio {
    private static final String LOG_TAG = "[NicknameRepositorio]";
    private static NicknameRepositorio INSTANCE;
    public static NicknameRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new NicknameRepositorio();
        return INSTANCE;
    }

    public NicknameRepositorio(){

    }

    private final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("nicknames");

    private DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public void inserir(String nickname, String idUsuario){
        Log.d(LOG_TAG, "O nickname \""+nickname+"\" será adicionado para o usuário: \""+idUsuario+"\"");
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    if(dsp.getValue(String.class).equals(idUsuario)){
                        remover(dsp.getKey());
                        getDatabaseReference().child(nickname).setValue(idUsuario);
                        break;
                    }
                }
                getDatabaseReference().child(nickname).setValue(idUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void remover(String nickname){
        Log.d(LOG_TAG, "O nickname \""+nickname+"\" será removido.");
        getDatabaseReference().child(nickname).removeValue();
    }

    public void getNicknameById(String id, FirebaseCallback<String> listener){
        UsuarioRepositorio.getInstance().getDatabaseReference().child(id).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.finalizado(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void nicknameDisponivel(String nickname, FirebaseCallback<Boolean> listener){
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean disponivel = true;
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    if(dsp.getKey().equals(nickname)){
                        disponivel = false;
                        break;
                    }
                }
                listener.finalizado(disponivel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.finalizado(true);
            }
        });
    }

    public void atualizarLista(){
        UsuarioRepositorio.getInstance().getTodosUsuarios(usuarios -> {
            for(Usuario usuario : usuarios){
                getDatabaseReference().child(usuario.getNickname()).setValue(usuario.getId());
            }
        });
    }
}
