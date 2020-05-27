package mirdep.br.mykwad.tabs.tabCriarDrone;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.viewpagerindicator.LinePageIndicator;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.carrosselPecas.CarrosselFragmentAdapter;
import mirdep.br.mykwad.carrosselPecas.CarrosselViewPager;
import mirdep.br.mykwad.carrosselPecas.CarrosselPecaFragment;

import static android.app.Activity.RESULT_OK;

public class CriarDroneFragment extends Fragment {

    private static int SELECIONAR_GALERIA = 1;

    private CarrosselViewPager viewPager_carrossel_pecas;
    private LinePageIndicator indicador;

    private ImageView imageView_escolher_fotos;
    private LinearLayout linearLayout_drone_galeria;
    private Button button_criardrone;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_criardrone, container, false);
        inicializarInterface();
        addListeners();
        setCarrosselPecasAdapter();
        return root;
    }

    private void inicializarInterface() {
        viewPager_carrossel_pecas = root.findViewById(R.id.viewPager_carrossel_pecas);
        imageView_escolher_fotos = root.findViewById(R.id.imageView_escolher_fotos);
        linearLayout_drone_galeria = root.findViewById(R.id.linearLayout_drone_galeria);
        button_criardrone = root.findViewById(R.id.button_criardrone);
        indicador = root.findViewById(R.id.indicator);
    }

    private void addListeners() {
        //Abre a galeria para escolher as fotos do Drone, e pede permissão caso não tenha
        imageView_escolher_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
                } else {
                    abrirGaleria();
                }
            }
        });

        button_criardrone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarDrone();
            }
        });
    }

    private void criarDrone(){

    }


    //Exibe a galeria do celular, e permite o usuário escolher 1 foto
    private void abrirGaleria() {
        //ALTERAR ESSE MÉTODO  E O onActivityResult PARA LIBERAR O USUARIO SELECIONAR MAIS DE 1 FOTO POR VEZ
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, SELECIONAR_GALERIA);
    }

    //Recebe a foto escolhida pelo usuário da galera e envia para o método de adicionar na tela
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECIONAR_GALERIA) {
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
        ImageView iv = new ImageView(root.getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageBitmap(foto);
        int dimensao = linearLayout_drone_galeria.getHeight();
        iv.setLayoutParams(new ViewGroup.LayoutParams(dimensao,dimensao));
        // ARRUMAR ESSA PORRA AQUI EM BAIXO
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_drone_galeria.removeView(v);
            }
        });
        linearLayout_drone_galeria.addView(iv);

    }

    //====================== CARROSSEL DE PEÇAS =============================

    //Cria um carrossel de peças na tela, sendo 1 Card para cada peça que o usuário deve escolher
    private void setCarrosselPecasAdapter() {
        CarrosselFragmentAdapter adapter = new CarrosselFragmentAdapter(getChildFragmentManager());
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