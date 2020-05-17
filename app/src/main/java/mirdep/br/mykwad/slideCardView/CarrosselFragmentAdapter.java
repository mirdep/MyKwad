package mirdep.br.mykwad.slideCardView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


public class CarrosselFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentos;

    public CarrosselFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        inicializarListaFragmentos();
    }

    private void inicializarListaFragmentos(){
        fragmentos = new ArrayList<>();
    }

    public void adicionarFragmento(Fragment fragmento){
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
        if(position < fragmentos.size()){
            fragmento = fragmentos.get(position);
        } else {
            fragmento = null;
        }
        return fragmento;
    }
}
