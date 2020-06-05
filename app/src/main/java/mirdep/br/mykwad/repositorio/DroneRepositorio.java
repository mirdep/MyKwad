package mirdep.br.mykwad.repositorio;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.objetos.Drone;


public class DroneRepositorio {

    private static DroneRepositorio INSTANCE;

    private static final String LOG_TAG = "[DroneRepositorio]";
    private static final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/drones");
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("drones");

    private MutableLiveData<List<Drone>> todosDrones;

    private Context context;

    public static DroneRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneRepositorio();
        return INSTANCE;
    }

    public DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public StorageReference getStorageReference() {
        return REFERENCIA_STORAGE;
    }

    //Adiciona uma nova peça no BancoDeDados
    public void inserir(final Drone drone) {
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(drone.getId() == null){
                    drone.setId(getDatabaseReference().push().getKey());
                }
                getDatabaseReference().child(drone.getId()).setValue(drone);

                for(int i = 0; i < drone.retrieveImagens().length; i++){
                    ImagemRepositorio.getInstance().uploadImagem(getStorageReference().child(drone.getId()), drone.retrieveImagens()[i], i+Configs.EXTENSAO_IMAGEM);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
    public LiveData<List<Drone>> getTodosDrones() {
        todosDrones = new MutableLiveData<>();
        getDatabaseReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_TAG, "Carregando lista de Drones...");
                        final List<Drone> listaDrones = new ArrayList<>();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Drone drone = dsp.getValue(Drone.class);
                            listaDrones.add(0,drone);

                            Log.d(LOG_TAG, "Drone carregado: " + drone.getTitulo());
                            todosDrones.postValue(listaDrones);
                        }
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar todosDrones.");

                    }
                });
        return todosDrones;
    }

    public LiveData<List<Drone>> getDronesPorUsuario(final String idUsuario) {
        todosDrones = new MutableLiveData<>();
        getDatabaseReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_TAG, "Carregando lista de Drones...");
                        final List<Drone> listaDrones = new ArrayList<>();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Drone drone = dsp.getValue(Drone.class);
                            if(drone.getUsuarioDonoId().equals(idUsuario)){
                                listaDrones.add(0,drone);

                                Log.d(LOG_TAG, "Drone carregado: " + drone.getTitulo());
                                todosDrones.postValue(listaDrones);
                            }
                        }
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar todosDrones.");

                    }
                });
        return todosDrones;
    }

    public StorageReference getFotoDroneReference(Drone drone){
        return DroneRepositorio.getInstance().getStorageReference().child(drone.getId()).child(0+Configs.EXTENSAO_IMAGEM);
    }

}
