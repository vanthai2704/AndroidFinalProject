package com.example.fastjobs.MessageFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fastjobs.R;
import com.example.fastjobs.Adapter.ListMessageAdapter;
import com.example.fastjobs.Entity.MessageOne;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.MessageSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListMessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MessageSupport messageSupport;
    private LoginSupport loginSupport;
    private ListView listViewListMessage;

    public ListView getListViewListMessage() {
        return listViewListMessage;
    }

    private OnFragmentInteractionListener mListener;

    public ListMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMessageFragment newInstance(String param1, String param2) {
        ListMessageFragment fragment = new ListMessageFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewListMessage = view.findViewById(R.id.listViewListMessage);
        final FloatingActionButton fab = view.findViewById(R.id.floatingActionNewMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View viewEdit = inflater.inflate(R.layout.new_message_layout, null);
                final EditText email = viewEdit.findViewById(R.id.editTextNewMessageTo);
                builder.setView(viewEdit)
                        .setTitle("New message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!email.getText().toString().equals("")){
                                    MessageFragment messageFragment = MessageFragment.newInstance(
                                            email.getText().toString().replaceAll("\\.","_"), null);
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.frameLayout));
                                    fragmentTransaction.add(R.id.frameLayout, messageFragment);
                                    fragmentTransaction.commit();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null);


                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        messageSupport = new MessageSupport();
        loginSupport = new LoginSupport();
        String from = loginSupport.getCurrentUserEmail().replaceAll("\\.","_");
        messageSupport.getMessageOne(this ,from, 1, 100, new CallbackSupport<MessageOne>() {

            @Override
            public void onCallback(MessageOne messageOne, String key,final List<MessageOne> messageOnes) {
                ListMessageAdapter listMessageAdapter = new ListMessageAdapter(messageOnes, getActivityMessage());
                listViewListMessage.setAdapter(listMessageAdapter);
                listViewListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MessageFragment messageFragment = MessageFragment.newInstance(
                                ((MessageOne)listViewListMessage.getAdapter().getItem(position)).getTo(), null);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.frameLayout));
                        fragmentTransaction.add(R.id.frameLayout, messageFragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        });
    }
    public final ListMessageFragment getActivityMessage(){
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_message, container, false);
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
}
