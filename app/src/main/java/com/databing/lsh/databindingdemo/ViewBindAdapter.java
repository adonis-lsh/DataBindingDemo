package com.databing.lsh.databindingdemo;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zl on 2017/6/16.
 */

public class ViewBindAdapter {
    @BindingAdapter({"bind:url"})
    public static void setImgUrl(SimpleDraweeView imageView, String uri) {
        if (!TextUtils.isEmpty(uri)) {
            imageView.setImageURI(Uri.parse(uri));
        }
    }
}
