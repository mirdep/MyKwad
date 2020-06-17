package mirdep.br.mykwad.fragments.tabMinhaConta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.objetos.Usuario;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.ImagemRepositorio;
import mirdep.br.mykwad.repositorio.NicknameRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

import static android.app.Activity.RESULT_OK;

public class EditarContaFragment extends Fragment {
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
        root = inflater.inflate(R.layout.fragment_editarconta, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView_editar_foto = root.findViewById(R.id.imageView_viewholder_drone_fotoDono);
        button_editar_salvar = root.findViewById(R.id.button_editar_salvar);
        button_editar_sair = root.findViewById(R.id.button_editar_sair);
        view_editar_foto = root.findViewById(R.id.view_editar_foto);
        editText_editar_email = root.findViewById(R.id.editText_editar_email);
        editText_editar_nome = root.findViewById(R.id.editText_editar_nome);
        editText_editar_nickname = root.findViewById(R.id.editText_editar_nickname);

        atualizarUI();

        editText_editar_nickname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText_editar_nickname.setError(null);
            }
        });

        button_editar_sair.setOnClickListener(v -> {
            fecharDialog();
        });

        button_editar_salvar.setOnClickListener(v -> {
            verificarCampos();
        });

        view_editar_foto.setOnClickListener(v -> {
            abrirGaleria();
        });
    }

    private void atualizarUI() {
        UsuarioRepositorio.getInstance().getUsuarioLogado(usuario -> {
            this.usuario = usuario;
            editText_editar_email.getEditText().setText(usuario.getEmail());
            editText_editar_nickname.getEditText().setText(usuario.getNickname());
            editText_editar_nome.getEditText().setText(usuario.getNome());
            GlideApp.with(root.getContext())
                    .load(ImagemRepositorio.getInstance().getFotoUsuarioReference(usuario.getId()))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView_editar_foto);
        });
    }

    private void salvarUsuario() {
        if (this.usuario != null) {
            String novoNickname = editText_editar_nickname.getEditText().getText().toString();
            String novoNome = editText_editar_nome.getEditText().getText().toString();
            usuario.setNickname(novoNickname);
            usuario.setNome(novoNome);
            if (foto != null) {
                usuario.setFoto(this.foto);
            } else {
                usuario.setFoto(BitmapFactory.decodeResource(getResources(), R.drawable.profile));
            }
            UsuarioRepositorio.getInstance().inserir(usuario);
            fecharDialog();
        }
    }

    private void verificarCampos() {
        String novoNickname = editText_editar_nickname.getEditText().getText().toString();
        if (novoNickname.equals(usuario.getNickname())) {
            salvarUsuario();
        } else {
            NicknameRepositorio.getInstance().nicknameDisponivel(novoNickname, disponivel -> {
                if (disponivel) {
                    salvarUsuario();
                } else {
                    editText_editar_nickname.setError("Usuário indisponível!");
                }
            });
        }
    }

    public void fecharDialog() {
        getFragmentManager().popBackStackImmediate();
        Log.d(NOME_LOG, "MyDialog fechado!");
    }

    private void abrirGaleria() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2000);
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
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fotoSelecionadaUri);
                adicionarFotoUsuario(bitmapImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Adiciona a foto escolhida na galeria no LinearLayout de fotos
    private void adicionarFotoUsuario(Bitmap foto) {
        this.foto = foto;
        imageView_editar_foto.setImageBitmap(criarMiniatura(foto, TAMANHO_MINIATURA));
    }

    private Bitmap criarMiniatura(Bitmap foto, int size) {
        double height = foto.getHeight();
        double width = foto.getWidth();
        double aspectRatio = width / height;
        if (aspectRatio > 1) {
            height = size;
            width = height * aspectRatio;
        } else {
            width = size;
            height = width / aspectRatio;
        }
        return Bitmap.createScaledBitmap(foto, (int) width, (int) height, true);
    }
}
