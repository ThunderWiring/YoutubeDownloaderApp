package com.youtubedownloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

/**
 * A RelativeLayout that has Spring animation when dragged.
 * */
public class SpringRelativeLayout extends RelativeLayout implements SpringListener {

    //TODO: use LayoutSpringConfig instead.
    private static final SpringConfig SPRING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(15, 5);

    /*
     * When the layout is dragged, there will be NUMBER_OF_ELEMENTS of shadows behind it, creating a blurring effect.
     * */
    private static final int NUMBER_OF_ELEMENTS = 1;

    private Spring[] mXSprings = new Spring[NUMBER_OF_ELEMENTS];
    private Spring[] mYSprings = new Spring[NUMBER_OF_ELEMENTS];

    public SpringRelativeLayout(Context context) {
        this(context, null);
        setWillNotDraw(false);
    }

    public SpringRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setWillNotDraw(false);
    }

    public SpringRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initInstance();
    }

    private void initInstance() {
        setWillNotDraw(false);
        SpringSystem springSystem = SpringSystem.create();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            mXSprings[i] = springSystem.createSpring();
            mXSprings[i].setSpringConfig(SPRING_CONFIG);

            mYSprings[i] = springSystem.createSpring();
            mYSprings[i].setSpringConfig(SPRING_CONFIG);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXSprings[0].setCurrentValue(w / 2);
        mYSprings[0].setCurrentValue(0);

        mXSprings[0].setEndValue(w / 2);
        mYSprings[0].setEndValue(h / 2);
    }

    @Override
    public void onSpringUpdate(Spring s) {
        LayoutSpringConfig cfg = (LayoutSpringConfig) s.getSpringConfig();
        if (cfg.index < NUMBER_OF_ELEMENTS - 1) {
            Spring[] springs = cfg.horizontal? mXSprings : mYSprings;
            springs[cfg.index + 1].setEndValue(s.getCurrentValue());
        }
        if (cfg.index == 0) {
            invalidate();
        }
    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mXSprings[0].setEndValue(event.getX());
        mYSprings[0].setEndValue(event.getY());
        invalidate();
        return false;
    }

    public float getSpringPositionX(){
        return (float)(mXSprings[0].getCurrentValue());
    }

    public float getSpringPositionY(){
        return (float)(mYSprings[0].getCurrentValue());
    }

    class LayoutSpringConfig extends SpringConfig {
        int index;
        boolean horizontal;
        public LayoutSpringConfig(double tension, double friction, int index, boolean horizontal) {
            super(tension, friction);
            this.index = index;
            this.horizontal = horizontal;
        }
    }

}
