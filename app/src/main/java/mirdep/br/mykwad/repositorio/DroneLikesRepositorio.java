package mirdep.br.mykwad.repositorio;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DroneLikesRepositorio {
    private static DroneLikesRepositorio INSTANCE;

    private static final String LOG_TAG = "[DroneLikesRepositorio]";
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("droneslikes");

    public static DroneLikesRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneLikesRepositorio();
        return INSTANCE;
    }

    public DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public void inserir(String idDrone, String idUsuario){
        getDatabaseReference().child(idDrone).child(idUsuario).setValue(true);
    }

    public void remover(String idDrone, String idUsuario){
        getDatabaseReference().child(idDrone).child(idUsuario).removeValue();
    }

    public LiveData<Long> getQtdLikes(String idDrone){
        final MutableLiveData<Long> qtdLikes = new MutableLiveData<>();
        getDatabaseReference().child(idDrone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                qtdLikes.postValue(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return qtdLikes;
    }

    public LiveData<Boolean> usuarioCurtiu(String idDrone, String idUsuario){
        final MutableLiveData<Boolean> usuarioCurtiu = new MutableLiveData<>();
        getDatabaseReference().child(idDrone).child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioCurtiu.postValue(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return usuarioCurtiu;
    }
}
