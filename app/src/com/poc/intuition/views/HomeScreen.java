package com.poc.intuition.views;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.response.PurchaseCategoryResponse;
import com.poc.intuition.widgets.Meter;

public class HomeScreen extends FragmentActivity implements IListener<PurchaseCategoryResponse>, Animation.AnimationListener {

    private PurchaseCategoryService purchaseCategoryService;
    private Meter meter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        LinearLayout widgetContainer = (LinearLayout) findViewById(R.id.widget_container);
        meter = Meter.redWidget(this, widgetContainer);
        meter.show();

//        Drawable blurredDrawable = new FastBlur(this).getBlurredDrawable();
//        findViewById(R.id.parent).setBackground(blurredDrawable);

        ReceiptFragment receiptFragment = new ReceiptFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up)
                .add(R.id.receipt_container, receiptFragment, "ReceiptFragment")
                .addToBackStack("ReceiptFragment")
                .commit();

//        purchaseCategoryService = new PurchaseCategoryService(this.getApplicationContext(), this);
//        purchaseCategoryService.createNewCategoryWithName("david", "Blooo");
    }

    @Override
    public void serviceResponse(PurchaseCategoryResponse response) {


    }

    @Override
    public void onAnimationStart(Animation animation) {
        //Do nothing
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        meter.animate();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //Do nothing
    }
}
