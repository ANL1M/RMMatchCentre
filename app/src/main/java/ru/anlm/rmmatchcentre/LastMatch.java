package ru.anlm.rmmatchcentre;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LastMatch extends Fragment{

    TextView tvRefreshDateLM, tvGuestLastMatch, tvHomeLastMatch, tvLastMatchDate, tvLigaNameLM, tvLastMatchResult;
    SQLIteHelper sqlIteHelper;
    ImageView ivHomeLM, ivGuestLM;
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.last_match, container, false);

        tvRefreshDateLM = (TextView) rootView.findViewById(R.id.tvRefreshDateLM);
        tvGuestLastMatch = (TextView) rootView.findViewById(R.id.tvGuestLastMatch);
        tvHomeLastMatch = (TextView) rootView.findViewById(R.id.tvHomeLastMatch);
        tvLastMatchDate = (TextView) rootView.findViewById(R.id.tvLastMatchDate);
        tvLigaNameLM = (TextView) rootView.findViewById(R.id.tvLigaNameLM);
        tvLastMatchResult = (TextView) rootView.findViewById(R.id.tvLastMatchResult);
        ivHomeLM = (ImageView)  rootView.findViewById(R.id.ivHomeLM);
        ivGuestLM = (ImageView)  rootView.findViewById(R.id.ivGuestLM);
        sqlIteHelper = new SQLIteHelper(getActivity());

        return rootView;
    }

    private void setResult() {

        arrayList = new ArrayList<>();
        arrayList = sqlIteHelper.readForDB();

        if(arrayList.size() != 0){

            tvHomeLastMatch.setText(arrayList.get(4));
            tvGuestLastMatch.setText(arrayList.get(5));
            tvLastMatchDate.setText(arrayList.get(6));
            tvLigaNameLM.setText(arrayList.get(7));
            tvLastMatchResult.setText(arrayList.get(8));

            String s = arrayList.get(9);
            ivHomeLM.setImageDrawable(Drawable.createFromPath(s));

            String s1 = arrayList.get(10);
            ivGuestLM.setImageDrawable(Drawable.createFromPath(s1));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setResult();
    }
}

