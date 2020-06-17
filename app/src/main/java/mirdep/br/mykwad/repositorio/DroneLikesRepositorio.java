package mirdep.br.mykwad.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mirdep.br.mykwad.interfaces.FirebaseCallback;

public class DroneLikesRepositorio {
    private static final String LOG_TAG = "[DroneLikesRepositorio]";
    private static DroneLikesRepositorio INSTANCE;
    public static DroneLikesRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneLikesRepositorio();
        return INSTANCE;
    }


    private final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("droneslikes");

    private DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    //Adiciona o Like do usuário idUsuario e insere o horário
    public void inserir(String idDrone, String idUsuario){
        Log.d(LOG_TAG, "O usuário "+idUsuario+" deu like no drone: "+idDrone);
        getDatabaseReference().child(idDrone).child(idUsuario).setValue(Timestamp.now().getSeconds());
    }

    public void remover(String idDrone, String idUsuario){
        Log.d(LOG_TAG, "O usuário "+idUsuario+" removeu o like no drone: "+idDrone);
        getDatabaseReference().child(idDrone).child(idUsuario).removeValue();
    }

    public void getQtdLikes(String idDrone, FirebaseCallback<Long> listener){
        Log.d(LOG_TAG, "Carregando quantidade de likes do drone: "+idDrone);
        getDatabaseReference().child(idDrone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.finalizado(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void usuarioCurtiu(String idDrone, String idUsuario, FirebaseCallback<Boolean> listener){
        Log.d(LOG_TAG, "Verificando se o usuário "+idUsuario+" já curtiu o drone: "+idDrone);
        getDatabaseReference().child(idDrone).child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.finalizado(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
