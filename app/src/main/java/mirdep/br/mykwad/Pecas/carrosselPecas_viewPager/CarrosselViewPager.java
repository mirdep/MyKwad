package mirdep.br.mykwad.Pecas.carrosselPecas_viewPager;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CarrosselViewPager extends ViewPager{

    public CarrosselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOffscreenPageLimit(10);
    }

    private void addPageTransformer(){

    }
}
