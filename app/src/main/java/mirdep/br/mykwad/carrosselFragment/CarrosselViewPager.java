package mirdep.br.mykwad.carrosselFragment;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CarrosselViewPager extends ViewPager{
    private final int PADDING = 100;

    public CarrosselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOffscreenPageLimit(10);

        this.setPadding(PADDING, 0, PADDING, 0);
        this.setClipToPadding(false);
        this.setPageMargin(20);
    }

}
