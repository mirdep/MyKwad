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
import mirdep.br.mykwad.ui.tabCriarDrone.pecasCarrossel.EscFragmentCarrossel;

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
        viewPager_carrossel_pecas.adicionarCarrosselAdapter(getCarrosselPecasAdapter());
    }

    private CarrosselFragmentAdapter getCarrosselPecasAdapter(){
        CarrosselFragmentAdapter adapter = new CarrosselFragmentAdapter(getParentFragmentManager());

        adapter.adicionarFragmento(new EscFragmentCarrossel());
        adapter.adicionarFragmento(new EscFragmentCarrossel());
        adapter.adicionarFragmento(new EscFragmentCarrossel());
        adapter.adicionarFragmento(new EscFragmentCarrossel());
        adapter.adicionarFragmento(new EscFragmentCarrossel());
        adapter.adicionarFragmento(new EscFragmentCarrossel());

        return adapter;
    }
}