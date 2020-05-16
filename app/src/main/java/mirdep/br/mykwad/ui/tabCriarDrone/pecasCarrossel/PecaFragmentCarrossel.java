package mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.storage.GlideApp;

public class PecaFragmentCarrossel extends Fragment {

    private Button carrossel_peca_button_escolher;
    private ImageView carrossel_peca_imageView;
    private TextView carrossel_peca_nome;

    private String pecaNome;

    private View root;

    public PecaFragmentCarrossel(String pecaNome) {
        this.pecaNome = pecaNome;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.carrossel_fragment_peca, container, false);
        inicializarInterface();
        downloadImagem();
        return root;
    }

    private void inicializarInterface() {
        carrossel_peca_button_escolher = root.findViewById(R.id.button_peca_escolher);
        carrossel_peca_imageView = root.findViewById(R.id.imageView_peca_imagem);
        carrossel_peca_nome = root.findViewById(R.id.textView_peca_nome);
        carrossel_peca_nome.setText(pecaNome);
    }

    private void downloadImagem() {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mykwad-72d96.appspot.com/midia/imagens/pecas/"+pecaNome.toLowerCase()+".jpg");

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(carrossel_peca_imageView);
    }
}