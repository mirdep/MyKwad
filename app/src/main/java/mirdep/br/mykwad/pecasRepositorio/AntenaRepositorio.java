package mirdep.br.mykwad.pecasRepositorio;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import mirdep.br.mykwad.bancoDeDados.Firebase;
import mirdep.br.mykwad.comum.MetodosGerais;
import mirdep.br.mykwad.pecas.Antena;

public class AntenaRepositorio extends AppCompatActivity {

    private final DatabaseReference referenciaBancoDeDados = Firebase.bancoDeDados.getDatabase().getReference().child("Antenas");

    private final String[] COLUNAS = new String[] {"marca", "modelo", "preco", "tipo"};

    public void salvarAntena(final Antena antena){
        referenciaBancoDeDados.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idAntena = antena.getId();
                boolean antenaJaCadastrado = dataSnapshot.hasChild(idAntena);
                if (!antenaJaCadastrado) {
                    String idAntenaAntigo = MetodosGerais.encodeId(idAntena);
                    referenciaBancoDeDados.child(idAntenaAntigo).removeValue();
                    referenciaBancoDeDados.child(idAntena).setValue(antena);
                    Toast.makeText(getApplicationContext(), "Antena removido com sucesso",
                            Toast.LENGTH_SHORT).show();
                } else {
                    referenciaBancoDeDados.child(idAntena).setValue(antena);
                    Toast.makeText(getApplicationContext(), "Antena atualizado com sucesso",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
