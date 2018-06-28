package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.hubert.guide.util.LogUtil;

/**
 * Created by hubert on 2018/6/28.
 */
public class RelativeGuide {

    @IntDef({android.view.Gravity.LEFT, android.view.Gravity.TOP,
            android.view.Gravity.RIGHT, android.view.Gravity.BOTTOM})
    public @interface Gravity {

    }

    public static class MarginInfo {
        int leftMargin;
        int topMargin;
        int rightMargin;
        int bottomMargin;
        int gravity;

        @Override
        public String toString() {
            return "MarginInfo{" +
                    "leftMargin=" + leftMargin +
                    ", topMargin=" + topMargin +
                    ", rightMargin=" + rightMargin +
                    ", bottomMargin=" + bottomMargin +
                    ", gravity=" + gravity +
                    '}';
        }
    }

    public HighLight highLight;
    @LayoutRes
    public int layout;
    public int padding;
    @Gravity
    public int gravity;

    public RelativeGuide(@LayoutRes int layout, @Gravity int gravity) {
        this.layout = layout;
        this.gravity = gravity;
    }

    public RelativeGuide(@LayoutRes int layout, @Gravity int gravity, int padding) {
        this.layout = layout;
        this.gravity = gravity;
        this.padding = padding;
    }

    public View getGuideLayout(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        MarginInfo marginInfo = getMarginInfo(gravity, viewGroup, view);
        LogUtil.e(marginInfo.toString());
        offsetMargin(marginInfo, viewGroup, view);
        layoutParams.gravity = marginInfo.gravity;
        layoutParams.leftMargin += marginInfo.leftMargin;
        layoutParams.topMargin += marginInfo.topMargin;
        layoutParams.rightMargin += marginInfo.rightMargin;
        layoutParams.bottomMargin += marginInfo.bottomMargin;
        view.setLayoutParams(layoutParams);
        return view;
    }

    private MarginInfo getMarginInfo(@Gravity int gravity, ViewGroup viewGroup, View view) {
        MarginInfo marginInfo = new MarginInfo();
        RectF rectF = highLight.getRectF(viewGroup);
        switch (gravity) {
            case android.view.Gravity.LEFT:
                marginInfo.gravity = android.view.Gravity.RIGHT;
                marginInfo.rightMargin = (int) (viewGroup.getWidth() - rectF.left - padding);
                marginInfo.topMargin = (int) rectF.top;
                break;
            case android.view.Gravity.TOP:
                marginInfo.gravity = android.view.Gravity.BOTTOM;
                marginInfo.bottomMargin = (int) (viewGroup.getHeight() - rectF.top - padding);
                marginInfo.leftMargin = (int) rectF.left;
                break;
            case android.view.Gravity.RIGHT:
                marginInfo.leftMargin = (int) (rectF.right + padding);
                marginInfo.topMargin = (int) rectF.top;
                break;
            case android.view.Gravity.BOTTOM:
                marginInfo.topMargin = (int) (rectF.bottom + padding);
                marginInfo.leftMargin = (int) rectF.left;
                break;
        }
        return marginInfo;
    }

    protected void offsetMargin(MarginInfo marginInfo, ViewGroup viewGroup, View view) {
        //do nothing
    }

}