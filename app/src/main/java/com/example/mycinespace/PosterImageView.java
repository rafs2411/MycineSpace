package com.example.mycinespace;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class PosterImageView extends AppCompatImageView {

    private static final float PROPORCAO_ALTURA = 1.4f;

    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int largura = MeasureSpec.getSize(widthMeasureSpec);
        int altura = (int) (largura * PROPORCAO_ALTURA);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(largura, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(altura, MeasureSpec.EXACTLY));
    }
}