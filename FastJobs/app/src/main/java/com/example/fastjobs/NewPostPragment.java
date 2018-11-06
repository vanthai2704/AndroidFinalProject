package com.example.fastjobs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.fastjobs.Adapter.GridViewImageAdapter;
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
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.ProvinceSupport;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostPragment extends Fragment {
    public static int REQUEST_CODE_CHOOSE_IMAGE = 123;

    private PostSupport postSupport;
    private Spinner spinnerCategoriesPost;
    private Spinner spinnerProvincePost;
    private Spinner spinnerDistrictPost;
    private Spinner spinnerCommunePost;
    private CategorySupport categorySupport;
    private ProvinceSupport provinceSupport;
    private DistrictSupport districtSupport;
    private CommuneSupport communeSupport;
    private EditText jobTitle, jobContent, jobremuneration, editTextTimeToDisplay, editTextContactNew, editTextLocationDetailNew;
    private Button addpost;
    private Button buttonChooseImage;
    private GridView gridViewImageInNew;
    private List<Image> images = new ArrayList<>();
    private GridViewImageAdapter gridViewImageAdapter;
    String category;
    String commune;
    private Activity activityTmp;
    private Context contextTmp;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public NewPostPragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Đăng Bài");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post_pragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityTmp = getActivity();
        contextTmp = getContext();
        postSupport = new PostSupport();
        categorySupport = new CategorySupport();
        provinceSupport = new ProvinceSupport();
        districtSupport = new DistrictSupport();
        communeSupport = new CommuneSupport();
        spinnerCategoriesPost = view.findViewById(R.id.spinnerCategoriesPost);
        spinnerProvincePost = view.findViewById(R.id.spinnerProvincePost);
        spinnerDistrictPost = view.findViewById(R.id.spinnerDistrictPost);
        spinnerCommunePost = view.findViewById(R.id.spinnerCommunePost);
        jobTitle = view.findViewById(R.id.jobName);
        jobContent = view.findViewById(R.id.jobContent);
        jobremuneration = view.findViewById(R.id.remuneration);
        editTextContactNew= view.findViewById(R.id.editTextContactNew);
        editTextLocationDetailNew= view.findViewById(R.id.editTextLocationDetailNew);
        addpost = view.findViewById(R.id.jobPost);
        buttonChooseImage = view.findViewById(R.id.buttonChooseImage);
        editTextTimeToDisplay = view.findViewById(R.id.editTextTimeToDisplay);
        gridViewImageInNew = view.findViewById(R.id.gridViewImageInNew);

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),123);
            }
        });

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = jobTitle.getText().toString();
                String content = jobContent.getText().toString();
                double remuneration = Double.parseDouble(jobremuneration.getText().toString());
                double timeToDisplay = Double.parseDouble(editTextTimeToDisplay.getText().toString());
                String location_coordinate="";
                String contact = editTextContactNew.getText().toString();
                String locationDetail = editTextLocationDetailNew.getText().toString();
                try {
                    String location = spinnerDistrictPost.getSelectedItem().toString()
                            + " "+ spinnerDistrictPost.getSelectedItem().toString()
                            + " "+ spinnerProvincePost.getSelectedItem().toString();
                    Geocoder gc = new Geocoder(activityTmp.getApplicationContext());
                    List<Address> addresses= gc.getFromLocationName(location, 1); // get ddthe found Aress Objects
                    location_coordinate = addresses.get(0).getLatitude() +"-" + addresses.get(0).getLongitude();
                } catch (Exception ex) {
                    // handle the exception
                     ex.printStackTrace();
                }

                String user_id = (new LoginSupport()).getCurrentUserEmail().replaceAll("\\.","_");
                Post post = new Post(commune,category,user_id,50000,remuneration,location_coordinate,
                        title,content,PostSupport.ACTIVE,new Date(),timeToDisplay,images,contact,locationDetail);
                postSupport.insert(post,contextTmp);


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contentLayout,new MyFragment());
                ft.commit();


            }
        });

        categorySupport.getAll(new CallbackSupport<Category>() {

            @Override
            public void onCallback(Category category, String key, List<Category> categories) {
                ArrayAdapter<Category> adapter;
                adapter = new ArrayAdapter<Category>(
                        contextTmp,
                        android.R.layout.simple_spinner_item,
                        categories
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoriesPost.setAdapter(adapter);
                spinnerCategoriesPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        setCategory(((Category)parent.getItemAtPosition(position)).getCategory_id());
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
                        contextTmp,
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
                                        contextTmp,
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
                                                        contextTmp,
                                                        android.R.layout.simple_spinner_item,
                                                        communes
                                                );
                                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerCommunePost.setAdapter(adapter);
                                                spinnerCommunePost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        setCommune(((Commune)parent.getItemAtPosition(position)).getCommune_id());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null )
        {
            Image image = new Image("A", "POST_IMAGE", data.getData());
            images.add(image);
            gridViewImageAdapter = new GridViewImageAdapter(images, this);
            gridViewImageInNew.setAdapter(gridViewImageAdapter);
        }
    }
}
