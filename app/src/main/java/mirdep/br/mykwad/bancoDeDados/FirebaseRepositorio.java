package mirdep.br.mykwad.bancoDeDados;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRepositorio {

    private DatabaseReference bancoDeDados;

    public FirebaseRepositorio(){
        bancoDeDados = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getBancoDeDados() {
        return bancoDeDados;
    }
}
