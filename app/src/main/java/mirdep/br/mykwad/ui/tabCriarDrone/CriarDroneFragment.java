package mirdep.br.mykwad.ui.tabCriarDrone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.viewpagerindicator.LinePageIndicator;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.slideCardView.CarrosselFragmentAdapter;
import mirdep.br.mykwad.slideCardView.SlideCardViewPager;
import mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel.PecaFragmentCarrossel;

import static android.app.Activity.RESULT_OK;

public class CriarDroneFragment extends Fragment {

    private static int SELECIONAR_GALERIA = 1;

    private SlideCardViewPager viewPager_carrossel_pecas;
    private LinePageIndicator indicador;

    private ImageView imageView_escolher_fotos;
    private LinearLayout linearLayout_drone_galeria;

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
        indicador = root.findViewById(R.id.indicator);
    }

    private void addListeners() {
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
    }


    private void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, SELECIONAR_GALERIA);
    }

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

    private void adicionarFotoDrone(Bitmap foto){
        ImageView iv = new ImageView(root.getContext());
        iv.setImageBitmap(foto);
        iv.setLayoutParams(new ViewGroup.LayoutParams(150,ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout_drone_galeria.addView(iv);
    }

    private void setCarrosselPecasAdapter() {
        CarrosselFragmentAdapter adapter = new CarrosselFragmentAdapter(getChildFragmentManager());
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_1)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_2)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_3)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_4)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_5)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_6)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_7)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_8)));
        adapter.adicionarFragmento(new PecaFragmentCarrossel(getString(R.string.peca_9)));
        viewPager_carrossel_pecas.setAdapter(adapter);
        indicador.setViewPager(viewPager_carrossel_pecas);
    }
}