package com.lentimosystems.licio.barber.Interface;

import com.lentimosystems.licio.barber.Model.Salon;

import java.util.List;

public interface BranchLoadListener {
    void onBranchLoadSuccess(List<Salon> salonList);
    void onBranchLoadFailed(String message);
}
