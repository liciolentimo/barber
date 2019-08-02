package com.lentimosystems.licio.barber.Interface;

import java.util.List;

public interface AllSalonLoadListener {
    void onAllSalonLoadSuccess(List<String> areaNameList);
    void onAllSalonLoadFailed(String message);
}
