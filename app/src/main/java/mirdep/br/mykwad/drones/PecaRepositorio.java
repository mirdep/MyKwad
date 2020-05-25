package mirdep.br.mykwad.drones;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class PecaRepositorio {

    public static DatabaseReference getPecaDatabaseReference(String peca){
        return FirebaseDatabase.getInstance().getReference("pecas").child(peca);
    }
}
