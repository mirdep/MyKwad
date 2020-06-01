package mirdep.br.mykwad.drones;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DroneRepositorio {

    private static DroneRepositorio INSTANCE;
    private static final String LOG_TAG = "[DroneRepositorio]";

    public static DroneRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DroneRepositorio();
        return INSTANCE;
    }

    private DatabaseReference getDroneReference() {
        return FirebaseDatabase.getInstance().getReference("drones");
    }

    //Adiciona uma nova peça no BancoDeDados
    public void salvarNoBanco(final Drone drone) {
        getDroneReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getDroneReference().push().setValue(drone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public LiveData<List<Peca>> getPecas(String tipoPeca) {
//        pecas = new MutableLiveData<>();
//        loadPecas(tipoPeca);
//        return pecas;
//    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
//    private void loadPecas(String tipoPeca) {
//        listaPecas = new ArrayList<>();
//        getDroneReference(tipoPeca).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Log.d(LOG_TAG, "Carregando lista de " + tipoPeca + "...");
//
//                        //Lê todas as peças do BD e adiciona 1 por 1 na lista
//                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                            Peca peca = dsp.getValue(Peca.class);
//                            listaPecas.add(peca); //add result into array list
//                            Log.d(LOG_TAG, "Peça carregada: " + peca.toString() + "...");
//                            pecas.postValue(listaPecas);
//                        }
//                    }
//
//                    //Se der problema na leitura no BD
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.d(LOG_TAG, "ERRO! Ao carregar peças do tipo: " + tipoPeca + "...");
//
//                    }
//                });
//    }

}
