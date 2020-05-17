package mirdep.br.mykwad.ui.tabCriarDrone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.slideCardView.CarrosselFragmentAdapter;
import mirdep.br.mykwad.slideCardView.SlideCardViewPager;
import mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel.PecaFragmentCarrossel;

public class CriarDroneFragment extends Fragment {

    private SlideCardViewPager viewPager_carrossel_pecas;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_criardrone, container, false);
        inicializarInterface();
        return root;
    }

    private void inicializarInterface(){
        viewPager_carrossel_pecas = root.findViewById(R.id.viewPager_carrossel_pecas);
        viewPager_carrossel_pecas.setAdapter(carrosselPecasAdapter());
    }

    private CarrosselFragmentAdapter carrosselPecasAdapter(){
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
        return adapter;
    }
}