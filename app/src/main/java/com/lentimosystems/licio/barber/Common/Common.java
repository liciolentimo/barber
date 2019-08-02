package com.lentimosystems.licio.barber.Common;

import com.lentimosystems.licio.barber.Model.Salon;
import com.lentimosystems.licio.barber.Model.User;

public class Common {
    public static String IS_LOGIN = "ISLogin";
    public static User currentUser;
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVED";
    public static Salon currentSalon;
    public static int step = 0;
    public static String city = "";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE";
}
