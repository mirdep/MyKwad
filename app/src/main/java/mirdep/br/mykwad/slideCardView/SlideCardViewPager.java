package mirdep.br.mykwad.slideCardView;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SlideCardViewPager extends ViewPager{

    public SlideCardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOffscreenPageLimit(10);
    }

    private void addPageTransformer(){

    }
}
