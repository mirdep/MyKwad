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

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.objetos.Peca;

public final class PecaRepositorio {

    private static final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/pecas");
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("pecas");

    private static PecaRepositorio INSTANCE;
    private static final String LOG_TAG = "[PecaRepositorio]";

    private MutableLiveData<List<Peca>> pecas;
    private List<Peca> listaPecas;

    public static PecaRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PecaRepositorio();
        return INSTANCE;
    }

    private DatabaseReference getPecaReference(String pecaTipo) {
        return FirebaseDatabase.getInstance().getReference("pecas").child(pecaTipo);
    }

    public void povoarBD() {
        //-----------ANTENA------------
        salvarNoBanco(new Peca("1", "Antena", "Foxeer", "Lollipop 3"));
        salvarNoBanco(new Peca("2", "Antena", "Emax", "Pagoda V3"));
        salvarNoBanco(new Peca("3", "Antena", "ImmersionRC", "Patch V2"));

        //-----------BATERIA-----------
        salvarNoBanco(new Peca("1", "Bateria", "CNHL", "Black 4s 1500mah 100c"));
        salvarNoBanco(new Peca("2", "Bateria", "CNHL", "Orange 4s 1500mah 120c"));
        salvarNoBanco(new Peca("3", "Bateria", "Tattu", "R-Line 4s 1300mah 100c"));

        //----------CAMERA-------------
        salvarNoBanco(new Peca("1", "Câmera", "Foxeer", "Monster mini pro"));
        salvarNoBanco(new Peca("2", "Câmera", "Runcam", "Phoenix Oscar"));
        salvarNoBanco(new Peca("3", "Câmera", "Runcam", "Phoenix Oscar 2"));

        //----------Controladora FC-------------
        salvarNoBanco(new Peca("1","Controladora FC","Matek","F405 Ctr"));
        salvarNoBanco(new Peca("2","Controladora FC","Omnibus","F4 Pro V3"));
        salvarNoBanco(new Peca("3","Controladora FC","Omnibus","F405 AIO Std"));

        //----------Esc-------------
        salvarNoBanco(new Peca("1","Esc","Racerstar","4em1 40A"));
        salvarNoBanco(new Peca("2","Esc","Omnibus","BLHeli-s 4em1 30A"));
        salvarNoBanco(new Peca("3","Esc","Littlebee","Spring 30A"));

        //----------Frame-------------
        salvarNoBanco(new Peca("1","Frame","Reptile","Martian 220mm Anniversary Edition"));
        salvarNoBanco(new Peca("2","Frame","Alfa","Monster"));
        salvarNoBanco(new Peca("3","Frame","Tbs","MrSteele V2"));

        //----------Hélice-------------
        salvarNoBanco(new Peca("1","Hélice","Hqprop","5x4.3x3 V1S"));
        salvarNoBanco(new Peca("2","Hélice","Ethix","5x4.3x3"));
        salvarNoBanco(new Peca("3","Hélice","Gemfan","Hurricane 51466"));
        salvarNoBanco(new Peca("4","Hélice","Geprc","5040"));

        //----------Motor-------------
        salvarNoBanco(new Peca("1","Motor","Emax","Eco 2306 2400kv"));
        salvarNoBanco(new Peca("2","Motor","Emax","RS2205 2300kv"));
        salvarNoBanco(new Peca("3","Motor","T-motor","F40 Pro II 1750kv"));

        //----------Vídeo Transmissor VTX-------------
        salvarNoBanco(new Peca("1","Vídeo Transmissor VTX","Eachine","TS5828L 600mw"));
        salvarNoBanco(new Peca("2","Vídeo Transmissor VTX","TBS","Unify Pro 8000mw"));
        salvarNoBanco(new Peca("3","Vídeo Transmissor VTX","AKK","X2-Ultimate 1200mw"));

    }

    //Adiciona uma nova peça no BancoDeDados
    public void salvarNoBanco(final Peca peca) {
        getPecaReference(peca.getTipo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getPecaReference(peca.getTipo()).child(peca.getId()).setValue(peca);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public StorageReference getStorageReference() {
        return REFERENCIA_STORAGE;
    }

    public LiveData<List<Peca>> getPecas(String tipoPeca) {
        pecas = new MutableLiveData<>();
        loadPecas(tipoPeca);
        return pecas;
    }

    //Carrega as peças do BancoDeDados e retorna em um ArrayList
    private void loadPecas(String tipoPeca) {
        listaPecas = new ArrayList<>();
        getPecaReference(tipoPeca).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_TAG, "Carregando lista de " + tipoPeca + "...");

                        //Lê todas as peças do BD e adiciona 1 por 1 na lista
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Peca peca = dsp.getValue(Peca.class);
                            listaPecas.add(peca); //add result into array list
                            Log.d(LOG_TAG, "Peça carregada: " + peca.toString() + "...");
                            pecas.postValue(listaPecas);
                        }
                    }

                    //Se der problema na leitura no BD
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar peças do tipo: " + tipoPeca + "...");

                    }
                });
    }

    public LiveData<Peca> getPecaById(String tipoPeca, String pecaId) {
        MutableLiveData<Peca> pecaTemp = new MutableLiveData<>();
        if(pecaId != null){
            DatabaseReference pecaReference = PecaRepositorio.getInstance().getDatabaseReference().child(tipoPeca).child(pecaId);
            Log.d(LOG_TAG, PecaRepositorio.getInstance().getDatabaseReference().child(tipoPeca).child(pecaId).toString());
            pecaReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "SUCESSO! Ao carregar peca "+pecaId);
                            pecaTemp.postValue(dataSnapshot.getValue(Peca.class));
                        }

                        //Se der problema na leitura no BD
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(LOG_TAG, "ERRO! Ao carregar peca "+pecaId);

                        }
                    });
        } else{
            Log.d(LOG_TAG, "ERRO! PecaId não encontrado.");
        }
        return pecaTemp;
    }

}
