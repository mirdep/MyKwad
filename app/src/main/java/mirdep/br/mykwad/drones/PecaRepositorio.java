package mirdep.br.mykwad.drones;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class PecaRepositorio {

    public static DatabaseReference getPecaDatabaseReference(String peca){
        return FirebaseDatabase.getInstance().getReference("pecas").child(peca);
    }

    public static void povoarBD(){
        //-----------ANTENA------------
        salvarNoBanco(new Peca("1","Antena","Foxeer","Lollipop 3"));
        salvarNoBanco(new Peca("2","Antena","Emax","Pagoda V3"));

        //-----------BATERIA-----------
        salvarNoBanco(new Peca("1","Bateria","CNHL","4s 1500mah 100c"));
        salvarNoBanco(new Peca("2","Bateria","CNHL","4s 1500mah 70c"));

        //----------CAMERA-------------
        salvarNoBanco(new Peca("1","Câmera","Foxeer","Monster mini pro"));
    }

    //Adiciona uma nova peça no BancoDeDados
    public static void salvarNoBanco(final Peca peca){
        getPecaDatabaseReference(peca.getTipo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String id = Long.toString(dataSnapshot.getChildrenCount()+1);
                getPecaDatabaseReference(peca.getTipo()).child(peca.getId()).setValue(peca);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void limparBD(){

    }
}