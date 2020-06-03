package mirdep.br.mykwad.ui.carrosselFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import mirdep.br.mykwad.objetos.Peca;


public class CarrosselFragmentAdapter extends FragmentPagerAdapter {

    private List<CarrosselPecaFragment> fragmentos;

    public CarrosselFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        inicializarListaFragmentos();
    }

    private void inicializarListaFragmentos() {
        fragmentos = new ArrayList<>();
    }

    public void adicionarFragmento(CarrosselPecaFragment fragmento) {
        fragmentos.add(fragmento);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return fragmentos.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        Fragment fragmento;
        if (position < fragmentos.size()) {
            fragmento = fragmentos.get(position);
        } else {
            fragmento = null;
        }
        return fragmento;
    }

    public List<Peca> getPecas() {
        List<Peca> pecas = new ArrayList<>();
        for (int i = 0; i < fragmentos.size(); i++) {
            pecas.add(fragmentos.get(i).getPecaEscolhida());
        }
        return pecas;
    }
}
