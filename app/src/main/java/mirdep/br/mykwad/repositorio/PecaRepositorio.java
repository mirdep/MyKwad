package mirdep.br.mykwad.repositorio;

import android.util.Log;

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

import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Peca;

public final class PecaRepositorio {

    private static final StorageReference REFERENCIA_STORAGE = FirebaseStorage.getInstance().getReference("midia/imagens/pecas");
    private static final DatabaseReference REFERENCIA_DATABASE = FirebaseDatabase.getInstance().getReference("pecas");

    private static PecaRepositorio INSTANCE;
    private static final String LOG_TAG = "[PecaRepositorio]";

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
        inserir(new Peca("1", "Antena", "Foxeer", "Lollipop 3"));
        inserir(new Peca("2", "Antena", "Emax", "Pagoda V3"));
        inserir(new Peca("3", "Antena", "ImmersionRC", "Patch V2"));

        //-----------BATERIA-----------
        inserir(new Peca("1", "Bateria", "CNHL", "Black 4s 1500mah 100c"));
        inserir(new Peca("2", "Bateria", "CNHL", "Orange 4s 1500mah 120c"));
        inserir(new Peca("3", "Bateria", "Tattu", "R-Line 4s 1300mah 100c"));

        //----------CAMERA-------------
        inserir(new Peca("1", "Câmera", "Foxeer", "Monster mini pro"));
        inserir(new Peca("2", "Câmera", "Runcam", "Phoenix Oscar"));
        inserir(new Peca("3", "Câmera", "Runcam", "Phoenix Oscar 2"));

        //----------Controladora FC-------------
        inserir(new Peca("1","Controladora FC","Matek","F405 Ctr"));
        inserir(new Peca("2","Controladora FC","Omnibus","F4 Pro V3"));
        inserir(new Peca("3","Controladora FC","Omnibus","F405 AIO Std"));

        //----------Esc-------------
        inserir(new Peca("1","Esc","Racerstar","4em1 40A"));
        inserir(new Peca("2","Esc","Omnibus","BLHeli-s 4em1 30A"));
        inserir(new Peca("3","Esc","Littlebee","Spring 30A"));

        //----------Frame-------------
        inserir(new Peca("1","Frame","Reptile","Martian 220mm Anniversary Edition"));
        inserir(new Peca("2","Frame","Alfa","Monster"));
        inserir(new Peca("3","Frame","Tbs","MrSteele V2"));

        //----------Hélice-------------
        inserir(new Peca("1","Hélice","Hqprop","5x4.3x3 V1S"));
        inserir(new Peca("2","Hélice","Ethix","5x4.3x3"));
        inserir(new Peca("3","Hélice","Gemfan","Hurricane 51466"));
        inserir(new Peca("4","Hélice","Geprc","5040"));

        //----------Motor-------------
        inserir(new Peca("1","Motor","Emax","Eco 2306 2400kv"));
        inserir(new Peca("2","Motor","Emax","RS2205 2300kv"));
        inserir(new Peca("3","Motor","T-motor","F40 Pro II 1750kv"));

        //----------Vídeo Transmissor VTX-------------
        inserir(new Peca("1","Vídeo Transmissor VTX","Eachine","TS5828L 600mw"));
        inserir(new Peca("2","Vídeo Transmissor VTX","TBS","Unify Pro 8000mw"));
        inserir(new Peca("3","Vídeo Transmissor VTX","AKK","X2-Ultimate 1200mw"));

    }

    //Adiciona uma nova peça no BancoDeDados
    public void inserir(final Peca peca) {
        if(peca.getTempoCriacao() == null || peca.getTempoCriacao().length() < 5){
            peca.setTempoCriacao(String.valueOf(Timestamp.now().getSeconds()));
        }
        getDatabaseReference().child(peca.getTipo()).child(peca.getId()).setValue(peca);
    }

    public DatabaseReference getDatabaseReference() {
        return REFERENCIA_DATABASE;
    }

    public StorageReference getStorageReference() {
        return REFERENCIA_STORAGE;
    }

    public void getPecas(String tipoPeca, FirebaseCallback<List<Peca>> listener) {
        getPecaReference(tipoPeca).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_TAG, "Carregando lista de " + tipoPeca + "...");
                        List<Peca> pecas = new ArrayList<>();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Peca peca = dsp.getValue(Peca.class);
                            pecas.add(peca); //add result into array list
                            Log.d(LOG_TAG, "Peça carregada: " + peca.toString());
                        }
                        listener.finalizado(pecas);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_TAG, "ERRO! Ao carregar peças do tipo: " + tipoPeca + "...");
                    }
                });
    }

    public void getPecaById(String tipoPeca, String pecaId, FirebaseCallback<Peca> listener) {
        if(pecaId != null){
            DatabaseReference pecaReference = getDatabaseReference().child(tipoPeca).child(pecaId);
            Log.d(LOG_TAG, getDatabaseReference().child(tipoPeca).child(pecaId).toString());
            pecaReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "SUCESSO! Ao carregar peca "+pecaId);
                            listener.finalizado(dataSnapshot.getValue(Peca.class));
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
    }

}
