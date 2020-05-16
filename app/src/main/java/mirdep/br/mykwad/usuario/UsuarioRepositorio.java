package mirdep.br.mykwad.usuario;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class UsuarioRepositorio {

    private static boolean nicknameJaExiste;

    public static FirebaseUser getUsuarioAuth() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    
    public static DatabaseReference getUsuariosDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public static void salvarNoBanco(Usuario usuario) {
        getUsuariosDatabaseReference().child(usuario.getNickname()).setValue(usuario);
    }

    public static boolean usuarioEstaLogado() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static void logoutConta() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean nicknameJaExiste(String nickname) {
        nicknameJaExiste = false;
        getUsuariosDatabaseReference().child(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nicknameJaExiste = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return nicknameJaExiste;
    }
}
