package com.lentimosystems.licio.barber.Interface;

import com.lentimosystems.licio.barber.Model.Banner;

import java.util.List;

public interface LookbookLoadListener {
    void onLookbookLoadSuccess(List<Banner> banners);
    void onLookbookLoadFailed(String message);
}
