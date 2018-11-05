package com.example.fastjobs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fastjobs.Entity.Category;
import com.example.fastjobs.Entity.Commune;
import com.example.fastjobs.Entity.District;
import com.example.fastjobs.Entity.Province;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.CategorySupport;
import com.example.fastjobs.firebase.CommuneSupport;
import com.example.fastjobs.firebase.DistrictSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.ProvinceSupport;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostPragment extends Fragment {


    private Spinner spinnerCategoriesPost;
    private Spinner spinnerProvincePost;
    private Spinner spinnerDistrictPost;
    private Spinner spinnerCommunePost;
    private CategorySupport categorySupport;
    private ProvinceSupport provinceSupport;
    private DistrictSupport districtSupport;
    private CommuneSupport communeSupport;
    public NewPostPragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post_pragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categorySupport = new CategorySupport();
        provinceSupport = new ProvinceSupport();
        districtSupport = new DistrictSupport();
        communeSupport = new CommuneSupport();
        spinnerCategoriesPost = view.findViewById(R.id.spinnerCategoriesPost);
        spinnerProvincePost = view.findViewById(R.id.spinnerProvincePost);
        spinnerDistrictPost = view.findViewById(R.id.spinnerDistrictPost);
        spinnerCommunePost = view.findViewById(R.id.spinnerCommunePost);

        categorySupport.getAll(new CallbackSupport<Category>() {

            @Override
            public void onCallback(Category category, String key, List<Category> categories) {
                ArrayAdapter<Category> adapter;
                adapter = new ArrayAdapter<Category>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        categories
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoriesPost.setAdapter(adapter);
                spinnerCategoriesPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        provinceSupport.getAll(new CallbackSupport<Province>() {
            @Override
            public void onCallback(Province province, String key, List<Province> provinces) {
                ArrayAdapter<Province> adapter;
                adapter = new ArrayAdapter<Province>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        provinces
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvincePost.setAdapter(adapter);
                spinnerProvincePost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Province provinceSelected = (Province)parent.getItemAtPosition(position);
                        districtSupport.getAll(provinceSelected.getProvince_id(), new CallbackSupport<District>() {
                            @Override
                            public void onCallback(District district, String key, List<District> districts) {
                                ArrayAdapter<District> adapter;
                                adapter = new ArrayAdapter<District>(
                                        getContext(),
                                        android.R.layout.simple_spinner_item,
                                        districts
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerDistrictPost.setAdapter(adapter);
                                spinnerDistrictPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        District districtSelected = (District)parent.getItemAtPosition(position);
                                        communeSupport.getAll(districtSelected.getDistrict_id(), new CallbackSupport<Commune>() {
                                            @Override
                                            public void onCallback(Commune commune, String key, List<Commune> communes) {
                                                ArrayAdapter<Commune> adapter;
                                                adapter = new ArrayAdapter<Commune>(
                                                        getContext(),
                                                        android.R.layout.simple_spinner_item,
                                                        communes
                                                );
                                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerCommunePost.setAdapter(adapter);
                                                spinnerCommunePost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }
}
