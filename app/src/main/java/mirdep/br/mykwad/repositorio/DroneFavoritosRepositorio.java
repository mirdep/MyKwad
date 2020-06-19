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

public class DroneFavoritosRepositorio {
    private static final String LOG_TAG = "[DroneFavoritosRepos]";
    private static DroneFavoritosRepositorio INSTANCE;
    public static DroneFavoritosRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneFavoritosRepositorio();
        return INSTANCE;
    }


    private final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("dronesfavoritos");

    private DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    //Adiciona o Favorito do usuário idUsuario e insere o horário
    public void inserir(String idDrone, String idUsuario){
        Log.d(LOG_TAG, "O usuário "+idUsuario+" favoritou o drone: "+idDrone);
        getDatabaseReference().child(idDrone).child(idUsuario).setValue(Timestamp.now().getSeconds());
    }

    public void remover(String idDrone, String idUsuario){
        Log.d(LOG_TAG, "O usuário "+idUsuario+" desfavoritou o drone: "+idDrone);
        getDatabaseReference().child(idDrone).child(idUsuario).removeValue();
    }

    public void usuarioFavoritou(String idDrone, String idUsuario, FirebaseCallback<Boolean> listener){
        Log.d(LOG_TAG, "Verificando se o usuário "+idUsuario+" favoritou o drone: "+idDrone);
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
