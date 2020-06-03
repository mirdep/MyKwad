package mirdep.br.mykwad.main_tabs.tabCriarDrone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.BaseApp;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.ui.carrosselFragment.CarrosselFragmentAdapter;
import mirdep.br.mykwad.ui.carrosselFragment.CarrosselViewPager;
import mirdep.br.mykwad.ui.carrosselFragment.CarrosselPecaFragment;
import mirdep.br.mykwad.comum.MyDialog;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;

import static android.app.Activity.RESULT_OK;

public class CriarDroneFragment extends Fragment {

    private static int SELECIONAR_FOTO_CAMERA = 1;
    private static int SELECIONAR_FOTO_GALERIA = 2;

    private static int TAMANHO_MINIATURA = 200;

    private CarrosselViewPager viewPager_carrossel_pecas;
    private CarrosselFragmentAdapter adapter;
    private LinePageIndicator indicador;

    private ImageView imageView_abrir_camera;
    private ImageView imageView_abrir_galeria;
    private LinearLayout linearLayout_drone_galeria;
    private HorizontalScrollView scrollView_drone_galeria;
    private Button button_criardrone;
    private TextInputLayout editText_descricao;
    private TextInputLayout editText_titulo;

    private View root;

    private List<Bitmap> fotos;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_criardrone, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarInterface();
        addListeners();
        setCarrosselPecasAdapter();
        fotos = new ArrayList<>();
    }

    private void inicializarInterface() {
        viewPager_carrossel_pecas = root.findViewById(R.id.viewPager_carrossel_pecas);
        imageView_abrir_camera = root.findViewById(R.id.imageView_abrir_camera);
        imageView_abrir_galeria = root.findViewById(R.id.imageView_abrir_galeria);
        linearLayout_drone_galeria = root.findViewById(R.id.linearLayout_drone_galeria);
        scrollView_drone_galeria = root.findViewById(R.id.scrollView_drone_fotos);
        button_criardrone = root.findViewById(R.id.button_criardrone);
        indicador = root.findViewById(R.id.indicator);
        editText_descricao = root.findViewById(R.id.editText_descricao);
        editText_titulo = root.findViewById(R.id.editText_titulo);
    }

    private void addListeners() {
        //Abre a galeria para escolher as fotos do Drone, e pede permissão caso não tenha
        imageView_abrir_camera.setOnClickListener(view -> {
            if(fotos.size() >= Drone.QTD_MAX_FOTOS){
                Toast.makeText(root.getContext(), "Você atingiu o número máximo de fotos permitidas!", Toast.LENGTH_SHORT).show();
            } else {
                abrirCamera();
            }
        });

        imageView_abrir_galeria.setOnClickListener(view -> {
            if(fotos.size() >= Drone.QTD_MAX_FOTOS){
                Toast.makeText(root.getContext(), "Você atingiu o número máximo de fotos permitidas!", Toast.LENGTH_SHORT).show();
            } else {
                abrirGaleria();
            }
        });

        button_criardrone.setOnClickListener(v -> criarDrone());
    }

    //Exibe a galeria do celular, e permite o usuário escolher 1 foto
    //ALTERAR ESSE MÉTODO  E O onActivityResult PARA LIBERAR O USUARIO SELECIONAR MAIS DE 1 FOTO POR VEZ
    private void abrirGaleria() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECIONAR_FOTO_GALERIA);
        }
    }

    private void abrirCamera(){
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},2000);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, SELECIONAR_FOTO_CAMERA);
        }
    }

    private void criarDrone(){
        ProgressDialog progressDialog = MyDialog.criarProgressDialog(root.getContext(),"Cadastrando o drone...");
        progressDialog.show();

        DroneRepositorio.getInstance().inserir(getDronePrevia());

        progressDialog.dismiss();

        ((BaseApp) getActivity()).abrirTabComunidade();
    }

    private Drone getDronePrevia(){
        Drone drone = new Drone();
        drone.setDescricao(editText_descricao.getEditText().getText().toString());
        drone.setTitulo(editText_titulo.getEditText().getText().toString());
        drone.setPecas(adapter.getPecas());
        drone.setFotos(fotos);
        drone.setUsuarioDonoId(UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName());
        return drone;
    }

    //Recebe a foto escolhida pelo usuário da galera e envia para o método de adicionar na tela
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECIONAR_FOTO_CAMERA) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            adicionarFotoDrone(image);
        }
        if (resultCode == RESULT_OK && requestCode == SELECIONAR_FOTO_GALERIA) {
            Uri fotoSelecionadaUri = data.getData();
            Bitmap bitmapImage;
            try{
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fotoSelecionadaUri);
                adicionarFotoDrone(bitmapImage);
            } catch (Exception e){

            }
        }
    }

    //Adiciona a foto escolhida na galeria no LinearLayout de fotos
    private void adicionarFotoDrone(Bitmap foto){
        final Bitmap miniatura = criarMiniatura(foto, TAMANHO_MINIATURA);
        fotos.add(foto);
        ImageView iv = new ImageView(root.getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageBitmap(miniatura);
        int dimensao = linearLayout_drone_galeria.getHeight();
        iv.setLayoutParams(new ViewGroup.LayoutParams(dimensao,dimensao));
        // ARRUMAR ESSA PORRA AQUI EM BAIXO
        iv.setOnClickListener(v -> {
            Toast.makeText(root.getContext(), "Mantenha a foto pressionada para deletar!", Toast.LENGTH_SHORT).show();
        });
        iv.setOnLongClickListener(v -> {
            linearLayout_drone_galeria.removeView(v);
            fotos.remove(foto);
            return true;
        });
        linearLayout_drone_galeria.addView(iv);
        scrollView_drone_galeria.postDelayed(() -> scrollView_drone_galeria.fullScroll(HorizontalScrollView.FOCUS_RIGHT), 100L);
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

    //====================== CARROSSEL DE PEÇAS =============================

    //Cria um carrossel de peças na tela, sendo 1 Card para cada peça que o usuário deve escolher
    private void setCarrosselPecasAdapter() {
        adapter = new CarrosselFragmentAdapter(getChildFragmentManager());
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_1)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_2)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_3)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_4)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_5)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_6)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_7)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_8)));
        adapter.adicionarFragmento(new CarrosselPecaFragment(getString(R.string.peca_9)));
        viewPager_carrossel_pecas.setAdapter(adapter);
        indicador.setViewPager(viewPager_carrossel_pecas);
    }
}