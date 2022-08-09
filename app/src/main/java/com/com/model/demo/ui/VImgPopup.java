package com.com.model.demo.ui;

import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.com.model.demo.R;
import com.com.model.demo.databinding.PopupVimgBinding;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;


public class VImgPopup extends CenterPopupView {
    PopupVimgBinding mBinding;
    OnConfirmListener confirmListener;


    public VImgPopup(@NonNull Context context) {
        super(context);
    }

    public VImgPopup(@NonNull Context context, OnConfirmListener confirmListener) {
        super(context);
        this.confirmListener = confirmListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_vimg;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
    }

    @Override
    protected void doAfterShow() {
        super.doAfterShow();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mBinding = DataBindingUtil.bind(getPopupImplView());
        if (mBinding != null) {
            mBinding.slidePuzzle.setDimensionRatio("668:300");
            mBinding.slidePuzzle.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drag_cover_b));
            mBinding.slidePuzzle.setOnVerifyListener(aBoolean -> {
                if (aBoolean) {
                    mBinding.slidePuzzle.postDelayed(() -> {
                        if (null != confirmListener) {
                            confirmListener.onConfirm();
                        }
                        dismiss();
                    }, 700);
                }
                return null;
            });
        }
    }

    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }

    protected int getPopupWidth() {
        return this.getWidth();
    }

    protected int getPopupHeight() {
        return 0;
    }

}