package mirdep.br.mykwad.usuario;

import android.util.Base64;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.time.LocalDate;


public class Usuario {

    private String id;
    private String nickname;
    private String nome_completo;
    private String email;
    private LocalDate data_nascimento;


}
