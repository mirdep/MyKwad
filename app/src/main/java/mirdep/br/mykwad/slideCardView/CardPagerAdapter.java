package mirdep.br.mykwad.slideCardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;
import mirdep.br.mykwad.R;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mViews = new ArrayList<>();
    }

    public void addCardItem() {
        mViews.add(null);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.cardview_peca, container, false);
        container.addView(view);
        bind(mViews.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardView item, View view) {
        //inicializar itens do cardview
    }

}
