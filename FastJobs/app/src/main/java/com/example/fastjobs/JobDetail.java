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
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.PostSupport;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText jobTitle, jobContent, jobremuneration,jobCategory,jobLocation;
    private Button backlistpost;
    private PostSupport postSupport;
    private Button buy;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JobDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static JobDetail newInstance(String param1, String param2) {
        JobDetail fragment = new JobDetail();
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
        return inflater.inflate(R.layout.fragment_job_detail, container, false);
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
        jobTitle = view.findViewById(R.id.jobName);
        jobContent = view.findViewById(R.id.jobContent);
        jobremuneration = view.findViewById(R.id.remuneration);
        jobCategory = view.findViewById(R.id.jobCategory);
        jobLocation = view.findViewById(R.id.jobLocation);
        backlistpost = view.findViewById(R.id.backButton);
        buy = view.findViewById(R.id.buyButton);
        jobTitle.setInputType(0);
        jobContent.setInputType(0);
        jobremuneration.setInputType(0);
        jobCategory.setInputType(0);
        jobLocation.setInputType(0);

        if(true){
            buy.setText("Mua Cong Viec");
        }
        else{
            buy.setText("Huy Cong Viec");
        }

        postSupport = new PostSupport();
        postSupport.get(mParam1, new CallbackSupport<Post>() {
            @Override
            public void onCallback(Post post, String key, List<Post> posts) {
                jobTitle.setText(post.getPost_title());
                jobContent.setText(post.getPost_content());
                jobremuneration.setText(post.getRemuneration()+ "VND");
                (new CategorySupport()).get(post.getCategory_id(), new CallbackSupport<Category>() {

                    @Override
                    public void onCallback(Category category, String key, List<Category> categories) {
                        jobCategory.setText(category.getCategory_name());
                    }
                });

            }
        });

        backlistpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(true){
                    ft.replace(R.id.contentLayout,new MyFragment());
                    ft.commit();
                }
                else{
                    ft.replace(R.id.contentLayout,new HomeFragment());
                    ft.commit();
                }
            }
        });

    }
}
