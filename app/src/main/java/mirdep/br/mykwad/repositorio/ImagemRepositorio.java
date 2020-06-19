package mirdep.br.mykwad.repositorio;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.interfaces.FirebaseCallback;
import mirdep.br.mykwad.objetos.Drone;

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

    public void uploadImagem(StorageReference reference, Bitmap imagem, String nomeArquivo){
        uploadImagem(reference, imagem, nomeArquivo, objeto -> {});
    }

    public void uploadImagem(StorageReference reference, Bitmap imagem, String nomeArquivo, FirebaseCallback<Boolean> listener){
        reference = reference.child(nomeArquivo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, QUALIDADE_FOTO_BAIXA, baos);
        byte[] data = baos.toByteArray();
        reference.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            listener.finalizado(true);
        });
    }

    public StorageReference getFotoUsuarioReference(String id){
        return UsuarioRepositorio.getInstance().getStorageReference().child(id+ Configs.EXTENSAO_IMAGEM);
    }

    public StorageReference getFotoDroneReference(Drone drone){
        return DroneRepositorio.getInstance().getStorageReference().child(drone.getId()).child(0+Configs.EXTENSAO_IMAGEM);
    }
}
