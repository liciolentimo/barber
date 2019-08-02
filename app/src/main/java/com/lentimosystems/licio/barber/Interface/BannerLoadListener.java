package com.lentimosystems.licio.barber.Interface;

import com.lentimosystems.licio.barber.Model.Banner;

import java.util.List;

public interface BannerLoadListener {
    void onBannerLoadSuccess(List<Banner> banners);
    void onBannerLoadFailed(String message);
}
