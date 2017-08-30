package ru.anlm.rmmatchcentre;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FutureMatch extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public SwipeRefreshLayout mSwipeLayout;
    TextView teamHome, teamAway, dateMatch, ligaName, statusTV;
    ImageView ivHomeFM, ivGuestFM;
    SQLIteHelper sqlIteHelper;
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.future_match, container, false);

        teamHome = (TextView) rootView.findViewById(R.id.tvHomeLastMatch);
        teamAway = (TextView) rootView.findViewById(R.id.tvGuestLastMatch);
        dateMatch = (TextView) rootView.findViewById(R.id.tvLastMatchDate);
        ligaName = (TextView) rootView.findViewById(R.id.tvLigaNameFM);
        statusTV = (TextView) rootView.findViewById(R.id.tvRefreshDateFM);
        ivHomeFM = (ImageView)  rootView.findViewById(R.id.ivHomeFM);
        ivGuestFM = (ImageView)  rootView.findViewById(R.id.ivGuestLM);

        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.BLUE);

        sqlIteHelper = new SQLIteHelper(getActivity());

        return rootView;
    }

    @Override
    public void onRefresh() {

        JsoupHelper jsoupHelper = new JsoupHelper(FutureMatch.this);
        jsoupHelper.execute();
    }

    public void setResultFutureMatch(){

        arrayList = new ArrayList<>();
        arrayList = sqlIteHelper.readForDB();

        if(arrayList.size() != 0){
            teamHome.setText(arrayList.get(0));
            teamAway.setText(arrayList.get(1));
            dateMatch.setText(arrayList.get(2));
            ligaName.setText(arrayList.get(3));

            String s = arrayList.get(11);
            ivHomeFM.setImageDrawable(Drawable.createFromPath(s));

            String s1 = arrayList.get(12);
            ivGuestFM.setImageDrawable(Drawable.createFromPath(s1));
        }else {
            onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setResultFutureMatch();
    }
}
