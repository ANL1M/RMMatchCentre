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

        if(arrayList.size() != 0){
            tvNumberOne.setText(arrayList.get(13));
            tvNumberTwo.setText(arrayList.get(14));
            tvNumberThree.setText(arrayList.get(15));
            tvNumberFour.setText(arrayList.get(16));
            tvNumberFive.setText(arrayList.get(17));

            tvTeamOne.setText(arrayList.get(18));
            tvTeamTwo.setText(arrayList.get(19));
            tvTeamThree.setText(arrayList.get(20));
            tvTeamFour.setText(arrayList.get(21));
            tvTeamFive.setText(arrayList.get(22));

            tvGamesOne.setText(arrayList.get(23));
            tvGamesTwo.setText(arrayList.get(24));
            tvGamesThree.setText(arrayList.get(25));
            tvGamesFour.setText(arrayList.get(26));
            tvGamesFive.setText(arrayList.get(27));

            tvPointsOne.setText(arrayList.get(28));
            tvPointsTwo.setText(arrayList.get(29));
            tvPointsThree.setText(arrayList.get(30));
            tvPointsFour.setText(arrayList.get(31));
            tvPointsFive.setText(arrayList.get(32));

            String s1 = arrayList.get(33);
            ivOne.setImageDrawable(Drawable.createFromPath(s1));

            String s2 = arrayList.get(34);
            ivTwo.setImageDrawable(Drawable.createFromPath(s2));

            String s3 = arrayList.get(35);
            ivThree.setImageDrawable(Drawable.createFromPath(s3));

            String s4 = arrayList.get(36);
            ivFour.setImageDrawable(Drawable.createFromPath(s4));

            String s5 = arrayList.get(37);
            ivFive.setImageDrawable(Drawable.createFromPath(s5));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setResult();
    }
}
