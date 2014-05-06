package com.poc.intuition.experiments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.digitalaria.gama.carousel.Carousel;
import com.poc.intuition.R;

public class CarFragment extends Fragment {

    private Carousel carousel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car, container, false);
        carousel = (Carousel) v.findViewById(R.id.carousel);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carousel.setType(Carousel.TYPE_CYLINDER);
//        carousel.setOverScrollBounceEnabled(true);
//        carousel.setInfiniteScrollEnabled(false);
//        carousel.setItemRearrangeEnabled(true);
        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext());
        carousel.setAdapter(adapter);

        // change the first selected position. (optional)
        carousel.setCenterPosition(3);
    }
}
