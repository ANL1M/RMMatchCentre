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
            tvNumberOne.setText(sqlIteHelper.readForDB().get(13));
            tvNumberTwo.setText(sqlIteHelper.readForDB().get(14));
            tvNumberThree.setText(sqlIteHelper.readForDB().get(15));
            tvNumberFour.setText(sqlIteHelper.readForDB().get(16));
            tvNumberFive.setText(sqlIteHelper.readForDB().get(17));

            tvTeamOne.setText(sqlIteHelper.readForDB().get(18));
            tvTeamTwo.setText(sqlIteHelper.readForDB().get(19));
            tvTeamThree.setText(sqlIteHelper.readForDB().get(20));
            tvTeamFour.setText(sqlIteHelper.readForDB().get(21));
            tvTeamFive.setText(sqlIteHelper.readForDB().get(22));

            tvGamesOne.setText(sqlIteHelper.readForDB().get(23));
            tvGamesTwo.setText(sqlIteHelper.readForDB().get(24));
            tvGamesThree.setText(sqlIteHelper.readForDB().get(25));
            tvGamesFour.setText(sqlIteHelper.readForDB().get(26));
            tvGamesFive.setText(sqlIteHelper.readForDB().get(27));

            tvPointsOne.setText(sqlIteHelper.readForDB().get(28));
            tvPointsTwo.setText(sqlIteHelper.readForDB().get(29));
            tvPointsThree.setText(sqlIteHelper.readForDB().get(30));
            tvPointsFour.setText(sqlIteHelper.readForDB().get(31));
            tvPointsFive.setText(sqlIteHelper.readForDB().get(32));

            String s1 = sqlIteHelper.readForDB().get(33);
            ivOne.setImageDrawable(Drawable.createFromPath(s1));

            String s2 = sqlIteHelper.readForDB().get(34);
            ivTwo.setImageDrawable(Drawable.createFromPath(s2));

            String s3 = sqlIteHelper.readForDB().get(35);
            ivThree.setImageDrawable(Drawable.createFromPath(s3));

            String s4 = sqlIteHelper.readForDB().get(36);
            ivFour.setImageDrawable(Drawable.createFromPath(s4));

            String s5 = sqlIteHelper.readForDB().get(37);
            ivFive.setImageDrawable(Drawable.createFromPath(s5));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setResult();
    }
}
