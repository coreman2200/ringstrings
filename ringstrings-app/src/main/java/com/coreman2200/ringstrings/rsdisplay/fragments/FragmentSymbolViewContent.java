package com.coreman2200.ringstrings.rsdisplay.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coreman2200.ringstrings.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSymbolViewContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSymbolViewContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSymbolViewContent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAGID = "TagId";
    private static final String ARG_LAYOUTID = "LayoutId";

    // TODO: Rename and change types of parameters
    private int mTagId;
    private int mLayoutId;

    private OnFragmentInteractionListener mListener;

    public FragmentSymbolViewContent() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentSymbolViewContent newInstance(int tabid, int layoutid) {
        FragmentSymbolViewContent fragment = new FragmentSymbolViewContent();
        Bundle args = new Bundle();
        args.putInt(ARG_TAGID, tabid);
        args.putInt(ARG_LAYOUTID, layoutid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTagId = getArguments().getInt(ARG_TAGID, R.string.symbolview_details);
            mLayoutId = getArguments().getInt(ARG_LAYOUTID, R.layout.fragment_symbolviewcontent_empty);
        } else {
            mTagId = R.string.symbolview_details;
            mLayoutId = R.layout.fragment_symbolviewcontent_empty;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(mLayoutId, container, false);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
