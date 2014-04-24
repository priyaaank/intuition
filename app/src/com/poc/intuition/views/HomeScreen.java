package com.poc.intuition.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import com.poc.intuition.widgets.Gauge;
import com.poc.intuition.widgets.Meter;

public class HomeScreen extends Activity implements IListener<PurchaseCategoryResponse> {

    private PurchaseCategoryService purchaseCategoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        LinearLayout widgetContainer = (LinearLayout) findViewById(R.id.widget_container);
        Meter widget = Meter.redWidget(this, widgetContainer);
        widget.showAndAnimateView();

        purchaseCategoryService = new PurchaseCategoryService(this.getApplicationContext(), this);
        purchaseCategoryService.createNewCategoryWithName("david", "Blooo");
    }

    @Override
    public void serviceResponse(PurchaseCategoryResponse response) {


    }
}
