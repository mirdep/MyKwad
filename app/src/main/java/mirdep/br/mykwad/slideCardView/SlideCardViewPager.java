package mirdep.br.mykwad.slideCardView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import mirdep.br.mykwad.BaseApp;

public class SlideCardViewPager extends ViewPager{

    public CarrosselFragmentAdapter adapter;

    public SlideCardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void adicionarCarrosselAdapter(CarrosselFragmentAdapter adapter){
        this.adapter = adapter;
        this.setOffscreenPageLimit(1);
        this.setAdapter(adapter);
//        mCardShadowTransformer = new ShadowTransformer(this, adapter);
//        mCardShadowTransformer.enableScaling(false);
//        this.setPageTransformer(false, mCardShadowTransformer);
    }


}
