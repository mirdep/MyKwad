package mirdep.br.mykwad.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.DRONES.Drone;
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
}
