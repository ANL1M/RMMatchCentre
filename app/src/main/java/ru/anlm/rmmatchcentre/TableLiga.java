package ru.anlm.rmmatchcentre;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableLiga extends Fragment{

    TextView tvNumberOne, tvNumberTwo, tvNumberThree, tvNumberFour, tvNumberFive;
    TextView tvTeamOne, tvTeamTwo, tvTeamThree, tvTeamFour, tvTeamFive;
    TextView tvGamesOne, tvGamesTwo, tvGamesThree, tvGamesFour, tvGamesFive;
    TextView tvPointsOne, tvPointsTwo, tvPointsThree, tvPointsFour, tvPointsFive;

    ImageView ivOne, ivTwo, ivThree, ivFour, ivFive;
    SQLIteHelper sqlIteHelper;
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.table_liga, container, false);

        tvNumberOne = (TextView) rootView.findViewById(R.id.tvNumberOne);
        tvNumberTwo = (TextView) rootView.findViewById(R.id.tvNumberTwo);
        tvNumberThree = (TextView) rootView.findViewById(R.id.tvNumberThree);
        tvNumberFour = (TextView) rootView.findViewById(R.id.tvNumberFour);
        tvNumberFive = (TextView) rootView.findViewById(R.id.tvNumberFive);

        tvTeamOne = (TextView) rootView.findViewById(R.id.tvTeamOne);
        tvTeamTwo = (TextView) rootView.findViewById(R.id.tvTeamTwo);
        tvTeamThree = (TextView) rootView.findViewById(R.id.tvTeamThree);
        tvTeamFour = (TextView) rootView.findViewById(R.id.tvTeamFour);
        tvTeamFive = (TextView) rootView.findViewById(R.id.tvTeamFive);

        tvGamesOne = (TextView) rootView.findViewById(R.id.tvGamesOne);
        tvGamesTwo = (TextView) rootView.findViewById(R.id.tvGamesTwo);
        tvGamesThree = (TextView) rootView.findViewById(R.id.tvGamesThree);
        tvGamesFour = (TextView) rootView.findViewById(R.id.tvGamesFour);
        tvGamesFive = (TextView) rootView.findViewById(R.id.tvGamesFive);

        tvPointsOne = (TextView) rootView.findViewById(R.id.tvPointsOne);
        tvPointsTwo = (TextView) rootView.findViewById(R.id.tvPointsTwo);
        tvPointsThree = (TextView) rootView.findViewById(R.id.tvPointsThree);
        tvPointsFour = (TextView) rootView.findViewById(R.id.tvPointsFour);
        tvPointsFive = (TextView) rootView.findViewById(R.id.tvPointsFive);

        ivOne = (ImageView) rootView.findViewById(R.id.ivOne);
        ivTwo = (ImageView) rootView.findViewById(R.id.ivTwo);
        ivThree = (ImageView) rootView.findViewById(R.id.ivThree);
        ivFour = (ImageView) rootView.findViewById(R.id.ivFour);
        ivFive = (ImageView) rootView.findViewById(R.id.ivFive);

        sqlIteHelper = new SQLIteHelper(getActivity());

        return rootView;
    }

    private void setResult(){

        arrayList = new ArrayList<>();
        arrayList = sqlIteHelper.readForDB();

        TextView[] screenElem = {tvNumberOne, tvNumberTwo, tvNumberThree, tvNumberFour, tvNumberFive,
                tvTeamOne, tvTeamTwo, tvTeamThree, tvTeamFour, tvTeamFive,
                tvGamesOne, tvGamesTwo, tvGamesThree, tvGamesFour, tvGamesFive,
                tvPointsOne, tvPointsTwo, tvPointsThree, tvPointsFour, tvPointsFive};

        ImageView[] imageElem = {ivOne, ivTwo, ivThree, ivFour, ivFive};

        if(arrayList.size() != 0){

            for (int i = 0; i < screenElem.length; i++) {
                screenElem[i].setText(arrayList.get(i+13));
            }

            for (int i = 0; i < imageElem.length ; i++) {
                String pathString = arrayList.get(i+33);
                imageElem[i].setImageDrawable(Drawable.createFromPath(pathString));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setResult();
    }
}
