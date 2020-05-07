package mirdep.br.mykwad.bancoDeDados;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {
    public static final DatabaseReference bancoDeDados = FirebaseDatabase.getInstance().getReference();
}
