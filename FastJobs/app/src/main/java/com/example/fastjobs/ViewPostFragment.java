package com.example.fastjobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fastjobs.Entity.Category;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.CategorySupport;
import com.example.fastjobs.firebase.CommuneSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText jobTitle, jobContent, jobremuneration,jobCategory,jobLocation,jobPrice;
    private Button backlistpost;
    private Button buy;
    private PostSupport postSupport;
    private UserSupport userSupport;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPostFragment newInstance(String param1, String param2) {
        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_post, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobTitle = view.findViewById(R.id.viewJobName);
        jobContent = view.findViewById(R.id.viewJobContent);
        jobremuneration = view.findViewById(R.id.viewRemuneration);
        jobCategory = view.findViewById(R.id.viewJobCategory);
        jobLocation = view.findViewById(R.id.viewJobLocation);
        jobPrice = view.findViewById(R.id.viewPrice);
        backlistpost = view.findViewById(R.id.viewBackButton);
        buy = view.findViewById(R.id.viewBuyButton);
        jobTitle.setInputType(0);
        jobContent.setInputType(0);
        jobremuneration.setInputType(0);
        jobCategory.setInputType(0);
        jobLocation.setInputType(0);
        jobPrice.setInputType(0);

        postSupport = PostSupport.getInstance();
        postSupport.get(mParam1, new CallbackSupport<Post>() {
            @Override
            public void onCallback(final Post post, String key, List<Post> posts) {
                jobTitle.setText(post.getPost_title());
                jobContent.setText(post.getPost_content());
                jobremuneration.setText(post.getRemuneration()+ "VND");
                jobPrice.setText(post.getPrice()+"VND");
                (new CategorySupport()).get(post.getCategory_id(), new CallbackSupport<Category>() {

                    @Override
                    public void onCallback(Category category, String key, List<Category> categories) {
                        jobCategory.setText(category.getCategory_name());
                    }
                });
                CommuneSupport.getInstance().getFullLocation(post.getCommune_id(), new CallbackSupport<String>() {
                    @Override
                    public void onCallback(String s, String key, List<String> strings) {
                        jobLocation.setText(post.getPost_location_detail()+","+s);
                    }
                });
            }
        });

        backlistpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contentLayout,new HomeFragment());
                ft.commit();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSupport = UserSupport.getInstance();
                userSupport.addToCart(mParam1);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contentLayout,new CartFragment());
                ft.commit();
            }
        });
    }
}
