package mirdep.br.mykwad.pecasRepositorio;

import android.util.Base64;
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

    public void salvarAntena(final Antena antena){
        salvarAntena(antena, true);
    }

    public void salvarAntena(final Antena antena, final boolean substituir){
        referenciaBancoDeDados.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idAntena = antena.getId();
                if (substituir) {
                    referenciaBancoDeDados.child(idAntena).setValue(antena);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public Antena buscarAntena(final String idAntena){
        referenciaBancoDeDados.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Antena antena = new Antena();

                boolean antenaCadastrado = dataSnapshot.hasChild(idAntena);

                if (antenaCadastrado) {
                    antena.setNome(dataSnapshot.child(idAntena).child("nome").getValue().toString());
                    antena.setEmail(dataSnapshot.child(idAntena).child("email").getValue().toString());
                    antena.setId(dataSnapshot.child(idAntena).child("id").getValue().toString());
                    antena.setCep(dataSnapshot.child(idAntena).child("cep").getValue().toString());
                    antena.setEndereco(dataSnapshot.child(idAntena).child("endereco").getValue().toString());
                    antena.setTelefone(dataSnapshot.child(idAntena).child("telefone").getValue().toString());
                    antena.setNascimento(dataSnapshot.child(idAntena).child("nascimento").getValue().toString());
                } else {
                    antena = null;
                }

                if (antena != null) {
                    editTextNome.setText(antena.getNome());
                    editTextEmail.setText(antena.getEmail());
                    editTextCEP.setText(antena.getCep());
                    editTextEnd.setText(antena.getEndereco());
                    editTextTelefone.setText(antena.getTelefone());
                    editTextNascimento.setText(antena.getNascimento());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
