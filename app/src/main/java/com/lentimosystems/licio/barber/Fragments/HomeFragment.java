package com.lentimosystems.licio.barber.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lentimosystems.licio.barber.Adapter.HomeSliderAdapter;
import com.lentimosystems.licio.barber.Adapter.LookbookAdapter;
import com.lentimosystems.licio.barber.BookingActivity;
import com.lentimosystems.licio.barber.Common.Common;
import com.lentimosystems.licio.barber.Interface.BannerLoadListener;
import com.lentimosystems.licio.barber.Interface.LookbookLoadListener;
import com.lentimosystems.licio.barber.Model.Banner;
import com.lentimosystems.licio.barber.R;
import com.lentimosystems.licio.barber.Service.PicassoImageService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LookbookLoadListener, BannerLoadListener {
    private Unbinder unbinder;

    @BindView(R.id.layout_user_info)
    LinearLayout layout_user_info;
    @BindView(R.id.txt_username)
    TextView txt_username;
    @BindView(R.id.banner_slider)Slider banner_slider;
    @BindView(R.id.recycler_look_book)
    RecyclerView recycler_look_book;
    @OnClick(R.id.cardview_booking)
    void booking()
    {
        startActivity(new Intent(getActivity(), BookingActivity.class));
    }

    CollectionReference bannerRef, lookbookRef;

    BannerLoadListener bannerLoadListener;
    LookbookLoadListener lookbookLoadListener;


    public HomeFragment() {
     bannerRef = FirebaseFirestore.getInstance().collection("Banner");
        lookbookRef = FirebaseFirestore.getInstance().collection("Lookbook");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this,view);

        Slider.init(new PicassoImageService());
        bannerLoadListener = this;
        lookbookLoadListener = this;

        if (AccountKit.getCurrentAccessToken()!=null)
        {
            setUserInformation();
            loadBanner();
            loadLookbook();
        }

        return view;
    }

    private void loadLookbook() {
        lookbookRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Banner> lookbooks = new ArrayList<>();
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult())
                            {
                                Banner banner = bannerSnapshot.toObject(Banner.class);
                                lookbooks.add(banner);
                            }
                            lookbookLoadListener.onLookbookLoadSuccess(lookbooks);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lookbookLoadListener.onLookbookLoadFailed(e.getMessage());
            }
        });
    }

    private void loadBanner() {
        bannerRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Banner> banners = new ArrayList<>();
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult())
                            {
                                Banner banner = bannerSnapshot.toObject(Banner.class);
                                banners.add(banner);
                            }
                            bannerLoadListener.onBannerLoadSuccess(banners);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bannerLoadListener.onBannerLoadFailed(e.getMessage());
            }
        });
    }

    private void setUserInformation() {
        layout_user_info.setVisibility(View.VISIBLE);
        txt_username.setText(Common.currentUser.getName());
    }

    @Override
    public void onLookbookLoadSuccess(List<Banner> banners) {
        recycler_look_book.setHasFixedSize(true);
        recycler_look_book.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_look_book.setAdapter(new LookbookAdapter(getActivity(),banners));
    }

    @Override
    public void onLookbookLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {
        banner_slider.setAdapter(new HomeSliderAdapter(banners));
    }

    @Override
    public void onBannerLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
