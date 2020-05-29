package mirdep.br.mykwad.Pecas;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PecaRepositorio {

    private static PecaRepositorio INSTANCE;
    private static final String LOG_TAG = "[PecaRepositorio]";

    public static PecaRepositorio getInstance(){
        if(INSTANCE == null)
            INSTANCE = new PecaRepositorio();
        return INSTANCE;
    }

    public DatabaseReference getPecaReference(String pecaTipo){
        return FirebaseDatabase.getInstance().getReference("pecas").child(pecaTipo);
    }

    public void povoarBD(){
        //-----------ANTENA------------
        salvarNoBanco(new Peca("1","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("2","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("3","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("4","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("5","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("6","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("7","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("8","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("9","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("10","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("11","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("12","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("13","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("14","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("15","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("16","Antena","Emax","Pagoda V3"));
        salvarNoBanco(new Peca("17","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("18","Antena","Emax","Pagoda V3"));

        //-----------BATERIA-----------
        salvarNoBanco(new Peca("1","Bateria","CNHL","4s 1500mah 100c"));
        salvarNoBanco(new Peca("2","Bateria","CNHL","4s 1500mah 70c"));

        //----------CAMERA-------------
        salvarNoBanco(new Peca("1","Câmera","Foxeer","Monster mini pro"));
    }

    //Adiciona uma nova peça no BancoDeDados
    public void salvarNoBanco(final Peca peca){
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



}
