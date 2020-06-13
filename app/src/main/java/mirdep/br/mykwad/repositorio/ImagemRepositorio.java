package mirdep.br.mykwad.repositorio;

import android.graphics.Bitmap;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import mirdep.br.mykwad.comum.Configs;

public final class ImagemRepositorio{

    private static ImagemRepositorio INSTANCE;
    private static final int QUALIDADE_FOTO_BAIXA = 40;
    private static final int QUALIDADE_FOTO_ALTA = 100;
    private static final String LOG_TAG = "[ImagemRepositorio]";
    private static final String BD_REFERENCE = "midia/imagens";

    public static ImagemRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ImagemRepositorio();
        return INSTANCE;
    }

    public StorageReference getImagemReference() {
        return FirebaseStorage.getInstance().getReference().child(BD_REFERENCE);
    }

    public StorageReference getChildImagemReference(String child) {
        return FirebaseStorage.getInstance().getReference().child(BD_REFERENCE).child(child);
    }

    public void uploadImagem(StorageReference reference, Bitmap imagem, String nomeArquivo){
        reference = reference.child(nomeArquivo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, QUALIDADE_FOTO_BAIXA, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
        });
    }

    public StorageReference getFotoUsuarioReference(String id){
        return UsuarioRepositorio.getInstance().getStorageReference().child(id+ Configs.EXTENSAO_IMAGEM);
    }
}
