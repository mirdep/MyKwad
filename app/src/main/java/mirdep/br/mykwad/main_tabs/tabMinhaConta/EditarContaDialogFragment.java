package mirdep.br.mykwad.main_tabs.tabMinhaConta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

import static android.app.Activity.RESULT_OK;

public class EditarContaDialogFragment extends DialogFragment {
    private static final String NOME_LOG = "[EditarConta]";
    private static int SELECIONAR_FOTO_GALERIA = 2;

    private static int TAMANHO_MINIATURA = 200;

    private View root;

    private Usuario usuario;

    private Button button_editar_salvar;
    private View button_editar_sair;
    private View view_editar_foto;
    private TextInputLayout editText_editar_email;
    private TextInputLayout editText_editar_nome;
    private TextInputLayout editText_editar_nickname;
    private ImageView imageView_editar_foto;

    private Bitmap foto;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialogfragment_editarconta, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView_editar_foto = root.findViewById(R.id.imageView_editar_foto);
        button_editar_salvar = root.findViewById(R.id.button_editar_salvar);
        button_editar_sair = root.findViewById(R.id.button_editar_sair);
        view_editar_foto = root.findViewById(R.id.view_editar_foto);
        editText_editar_email = root.findViewById(R.id.editText_editar_email);
        editText_editar_nome = root.findViewById(R.id.editText_editar_nome);
        editText_editar_nickname = root.findViewById(R.id.editText_editar_nickname);

        atualizarUI();

        button_editar_sair.setOnClickListener(v -> {
            fecharDialog();
        });

        button_editar_salvar.setOnClickListener(v -> {
            salvarUsuario();
        });

        view_editar_foto.setOnClickListener(v -> {
            abrirGaleria();
        });
    }

    private void atualizarUI(){
        UsuarioRepositorio.getInstance().getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            this.usuario = usuario;
            editText_editar_email.getEditText().setText(usuario.getEmail());
            editText_editar_nickname.getEditText().setText(usuario.getNickname());
            editText_editar_nome.getEditText().setText(usuario.getNome());
            GlideApp.with(getContext())
                    .load(UsuarioRepositorio.getInstance().getStorageReference().child(usuario.getId()+ Configs.EXTENSAO_IMAGEM))
                    .into(imageView_editar_foto);
        });
    }

    private void salvarUsuario(){
        if(this.usuario != null){
            String novoNickname = editText_editar_nickname.getEditText().getText().toString();
            String novoNome = editText_editar_nome.getEditText().getText().toString();
            usuario.setNickname(novoNickname);
            usuario.setNome(novoNome);
            usuario.setFoto(this.foto);
            UsuarioRepositorio.getInstance().salvar(this.usuario);
            fecharDialog();
            ((BaseApp) getActivity()).abrirTabMinhaConta();
        }
    }

    //Define o tamanho do DialogFragment na tela
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void fecharDialog(){
        getDialog().dismiss();
        Log.d(NOME_LOG,"MyDialog fechado!");
    }

    private void abrirGaleria() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECIONAR_FOTO_GALERIA);
        }
    }

    //Recebe a foto escolhida pelo usuário da galera e envia para o método de adicionar na tela
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECIONAR_FOTO_GALERIA) {
            Uri fotoSelecionadaUri = data.getData();
            Bitmap bitmapImage;
            try{
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fotoSelecionadaUri);
                adicionarFotoUsuario(bitmapImage);
            } catch (Exception e){

            }
        }
    }

    //Adiciona a foto escolhida na galeria no LinearLayout de fotos
    private void adicionarFotoUsuario(Bitmap foto){
        this.foto = foto;
        imageView_editar_foto.setImageBitmap(criarMiniatura(foto, TAMANHO_MINIATURA));
    }

    private Bitmap criarMiniatura(Bitmap foto, int size){
        double height = foto.getHeight();
        double width = foto.getWidth();
        double aspectRatio = width/height;
        if(aspectRatio > 1){
            height = size;
            width = height*aspectRatio;
        } else {
            width = size;
            height = width/aspectRatio;
        }
        return Bitmap.createScaledBitmap(foto, (int)width, (int)height, true);
    }
}
