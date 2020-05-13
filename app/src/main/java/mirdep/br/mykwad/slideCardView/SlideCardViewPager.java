package mirdep.br.mykwad.slideCardView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SlideCardViewPager extends ViewPager{


    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    public SlideCardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem();
        mCardAdapter.addCardItem();
        mCardAdapter.addCardItem();
        mCardAdapter.addCardItem();

        mCardShadowTransformer = new ShadowTransformer(this, mCardAdapter);

        this.setAdapter(mCardAdapter);
        this.setPageTransformer(false, mCardShadowTransformer);
        this.setOffscreenPageLimit(3);

        mCardShadowTransformer.enableScaling(true);
    }


}
