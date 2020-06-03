package mirdep.br.mykwad.ui.carrosselFragment;

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

import mirdep.br.mykwad.objetos.Peca;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.ui.escolherPeca_dialogFragment.View_EscolherPeca;
import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.repositorio.GlideApp;

public class CarrosselPecaFragment extends Fragment {

    private static final int REQUEST_CODE_DIALOG = 111;

    private Button button_peca_escolher;
    private ImageView imageView_peca_imagem;
    private TextView textView_peca_nome;

    private String tipoPeca;

    private View root;

    private Peca pecaEscolhida;

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

    public void setPecaEscolhida(Peca peca){
        this.pecaEscolhida = peca;
        atualizarUI();
    }

    public Peca getPecaEscolhida(){
        return pecaEscolhida;
    }

    private void atualizarUI(){
        textView_peca_nome.setText(pecaEscolhida.toString());
        imageView_peca_imagem.setImageURI(pecaEscolhida.getStorage_imagem_uri());
    }

    //Adiciona o listener para abrir o DialogFragment ao clicar no botão
    private void adicionarListeners(){
        button_peca_escolher.setOnClickListener(v -> abrirDialogEscolherPecas());
    }

    //Abre o DialogFragment que irá exibir as peças do BancoDeDados pro usuário escolher
    private void abrirDialogEscolherPecas(){
        View_EscolherPeca dialog = new View_EscolherPeca(tipoPeca, this);
        dialog.setTargetFragment(this, REQUEST_CODE_DIALOG);
        dialog.show(getFragmentManager(), "");
    }

    //Baixa a foto default do Card do Carrossel de acordo o tipoPeca e exibe no Card
    private void downloadImagem() {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mykwad-72d96.appspot.com/midia/imagens/pecas/"+ tipoPeca + Configs.EXTENSAO_IMAGEM);
        // Download directly from StorageReference using Glide
        // (See GlideRepositorio for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(imageView_peca_imagem);
    }
}