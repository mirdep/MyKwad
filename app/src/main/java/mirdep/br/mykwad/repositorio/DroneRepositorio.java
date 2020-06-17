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
import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Drone;


public class DroneRepositorio {

    private static DroneRepositorio INSTANCE;

    private static final String LOG_TAG = "[DroneRepositorio]";
    private static final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/drones");
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("drones");

    private MutableLiveData<List<Drone>> todosDrones;

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
        drone.setUsuarioDonoId(UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName());
        drone.setTempoCriacao(String.valueOf(Timestamp.now().getSeconds()));
        if(drone.getId() == null){
            drone.setId(getDatabaseReference().push().getKey());
        }
        getDatabaseReference().child(drone.getId()).setValue(drone);

        for(int i = 0; i < drone.retrieveImagens().length; i++){
            ImagemRepositorio.getInstance().uploadImagem(getStorageReference().child(drone.getId()), drone.retrieveImagens()[i], i+Configs.EXTENSAO_IMAGEM);
        }
    }

    public void getById(String idDrone, FirebaseCallback<Drone> listener){
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    if(dsp.getKey().equals(idDrone)){
                        listener.finalizado(dsp.getValue(Drone.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
    public void getTodosDrones(FirebaseCallback<List<Drone>> listener) {
        Log.d(LOG_TAG, "Carregando lista de Drones...");
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<Drone> drones = new ArrayList<>();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Drone drone = dsp.getValue(Drone.class);
                            drones.add(0,drone);
                            Log.d(LOG_TAG, "Drone carregado: " + drone.getTitulo());
                        }
                        listener.finalizado(drones);
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar todos Drones.");

                    }
                });
    }

    public void getDronesPorUsuario(final String idUsuario, FirebaseCallback<List<Drone>> listener) {
        Log.d(LOG_TAG, "Carregando lista de Drones...");
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Drone> drones = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Drone drone = dsp.getValue(Drone.class);
                    if(drone.getUsuarioDonoId().equals(idUsuario)){
                        drones.add(0,drone);
                        Log.d(LOG_TAG, "Drone carregado: " + drone.getTitulo());
                    }
                }
                listener.finalizado(drones);
            }

            //Se der problema na leitura no BD
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LOG_TAG, "ERRO! Ao carregar todos Drones.");

            }
        });
    }

}
