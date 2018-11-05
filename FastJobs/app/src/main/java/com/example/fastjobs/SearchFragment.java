package com.example.fastjobs;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.fastjobs.Entity.Category;
import com.example.fastjobs.Entity.Commune;
import com.example.fastjobs.Entity.District;
import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.Province;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.CategorySupport;
import com.example.fastjobs.firebase.CommuneSupport;
import com.example.fastjobs.firebase.DistrictSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.ProvinceSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.support.v4.app.Fragment {

    private Spinner spinnerCategoriesSearch;
    private Spinner spinnerProvinceSearch;
    private Spinner spinnerDistrictSearch;
    private Spinner spinnerCommuneSearch;
    private ListView listViewSearchJobs;
    private CategorySupport categorySupport;
    private ProvinceSupport provinceSupport;
    private DistrictSupport districtSupport;
    private CommuneSupport communeSupport;
    private PostSupport postSupport;

    private Post postSearch;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Tìm Kiếm");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postSearch = new Post();
        categorySupport = new CategorySupport();
        provinceSupport = new ProvinceSupport();
        districtSupport = new DistrictSupport();
        communeSupport = new CommuneSupport();
        postSupport = new PostSupport();
        spinnerCategoriesSearch = view.findViewById(R.id.spinnerCategoriesSearch);
        spinnerProvinceSearch = view.findViewById(R.id.spinnerProvinceSearch);
        spinnerDistrictSearch = view.findViewById(R.id.spinnerDistrictSearch);
        spinnerCommuneSearch = view.findViewById(R.id.spinnerCommuneSearch);
        listViewSearchJobs = view.findViewById(R.id.listViewSearchJobs);
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
                spinnerCategoriesSearch.setAdapter(adapter);
                spinnerCategoriesSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        loadData();
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
                spinnerProvinceSearch.setAdapter(adapter);
                spinnerProvinceSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                spinnerDistrictSearch.setAdapter(adapter);
                                spinnerDistrictSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                                spinnerCommuneSearch.setAdapter(adapter);
                                                spinnerCommuneSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void loadData(){
        postSupport.search(postSearch, 1, 100, new CallbackSupport<Post>() {
            @Override
            public void onCallback(Post post, String key, List<Post> posts) {
                ArrayAdapter<Post> postArrayAdapter
                        = new ArrayAdapter<Post>(getContext(), android.R.layout.simple_list_item_1, posts);
                listViewSearchJobs.setAdapter(postArrayAdapter);
            }
        });
    }

}
