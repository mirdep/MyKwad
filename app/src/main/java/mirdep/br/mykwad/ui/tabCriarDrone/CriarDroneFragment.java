package mirdep.br.mykwad.ui.tabCriarDrone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import mirdep.br.mykwad.R;
import mirdep.br.mykwad.slideCardView.SlideCardViewPager;

public class CriarDroneFragment extends Fragment {

    private SlideCardViewPager slideCardViewPager_pecas;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_criardrone, container, false);
        inicializarInterface();
        return root;
    }

    private void inicializarInterface(){
        slideCardViewPager_pecas = root.findViewById(R.id.slideViewPager_pecas);
    }
}