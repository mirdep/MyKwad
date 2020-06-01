package mirdep.br.mykwad.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@GlideModule
public final class ImagemRepositorio extends AppGlideModule {

    private static ImagemRepositorio INSTANCE;
    private static final int QUALIDADE_FOTO_BAIXA = 40;
    private static final int QUALIDADE_FOTO_ALTA = 100;
    private static final String LOG_TAG = "[ImagemRepositorio]";

    public static ImagemRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ImagemRepositorio();
        return INSTANCE;
    }

    private StorageReference getImagemReference() {
        return FirebaseStorage.getInstance().getReference().child("midia/imagens");
    }

    public void uploadImagem(String path, Bitmap imagem, String nomeArquivo){
        StorageReference reference = getImagemReference().child(path).child(nomeArquivo);
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

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}
