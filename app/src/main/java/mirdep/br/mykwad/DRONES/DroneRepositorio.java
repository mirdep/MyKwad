package mirdep.br.mykwad.DRONES;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import mirdep.br.mykwad.storage.ImagemRepositorio;

public class DroneRepositorio {

    private static DroneRepositorio INSTANCE;
    private static final String LOG_TAG = "[DroneRepositorio]";

    private MutableLiveData<List<Drone>> drones;
    private List<Drone> listaDrones;

    public static DroneRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneRepositorio();
        return INSTANCE;
    }

    private DatabaseReference getDronesReference() {
        return FirebaseDatabase.getInstance().getReference("drones");
    }

    //Adiciona uma nova peça no BancoDeDados
    public void salvarNoBanco(final Drone drone) {
        getDronesReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(drone.getId() == null){
                    drone.setId(getDronesReference().push().getKey());
                }
                getDronesReference().child(drone.getId()).setValue(drone);

                for(int i = 0; i < drone.retrieveImagens().length; i++){
                    ImagemRepositorio.getInstance().uploadImagem("drones/"+drone.getId(), drone.retrieveImagens()[i], Integer.toString(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public LiveData<List<Drone>> getDrones() {
        drones = new MutableLiveData<>();
        loadDrones();
        return drones;
    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
    private void loadDrones() {
        listaDrones = new ArrayList<>();
        getDronesReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_TAG, "Carregando lista de drones...");

                        //Lê todas as peças do BD e adiciona 1 por 1 na lista
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Drone drone = dsp.getValue(Drone.class);
                            listaDrones.add(0,drone); //add result into array list
                            Log.d(LOG_TAG, "Drone carregado: " + drone.getTitulo());
                            drones.postValue(listaDrones);
                        }
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar drones.");

                    }
                });
    }

}
