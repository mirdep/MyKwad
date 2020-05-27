package mirdep.br.mykwad.carrosselPecas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.drones.Peca;
import mirdep.br.mykwad.recyclerViewEscolherPeca.EscolherPecaDialogFragment;
import mirdep.br.mykwad.storage.GlideApp;

public class CarrosselPecaFragment extends Fragment {

    private static int REQUEST_CODE_DIALOG = 111;

    private Button button_peca_escolher;
    private ImageView imageView_peca_imagem;
    private TextView textView_peca_nome;

    private String tipoPeca;

    private View root;

    public CarrosselPecaFragment(String tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_carrossel_peca, container, false);
        inicializarInterface();
        adicionarListeners();
        downloadImagem();
        return root;
    }

    private void inicializarInterface() {
        button_peca_escolher = root.findViewById(R.id.button_peca_escolher);
        imageView_peca_imagem = root.findViewById(R.id.imageView_peca_imagem);
        textView_peca_nome = root.findViewById(R.id.textView_peca_nome);
        textView_peca_nome.setText(tipoPeca);
    }

    //Adiciona o listener para abrir o DialogFragment ao clicar no botão
    private void adicionarListeners(){
        button_peca_escolher.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogEscolherPecas();
            }
        });
    }

    //Abre o DialogFragment que irá exibir as peças do BancoDeDados pro usuário escolher
    private void abrirDialogEscolherPecas(){
        EscolherPecaDialogFragment dialog = new EscolherPecaDialogFragment(tipoPeca);
        dialog.setTargetFragment(this, REQUEST_CODE_DIALOG);
        dialog.show(getFragmentManager(), "dialog");
    }

    //Baixa a foto default do Card do Carrossel de acordo o tipoPeca e exibe no Card
    private void downloadImagem() {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mykwad-72d96.appspot.com/midia/imagens/pecas/"+ tipoPeca +".jpg");
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(imageView_peca_imagem);
    }
}