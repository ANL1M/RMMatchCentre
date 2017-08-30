package ru.anlm.rmmatchcentre;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class JsoupHelper extends AsyncTask<Void, Void, ArrayList<String>>{

    SQLIteHelper sqlIteHelper;
    FutureMatch futureMatch;
    Boolean noInternetException;

    public JsoupHelper(FutureMatch futureMatch){
        this.futureMatch = futureMatch;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> hrefsBitmap = new ArrayList<>();
        sqlIteHelper = new SQLIteHelper(futureMatch.getContext());

        try {
            Document document = Jsoup.connect("http://football.sport-express.ru/command/68/").get();

            //Будущий матч
            //Получаем названия команд и изображения для будущего матча
            Elements elementsNextMatch = document.select("#match_box_2 img");
            String nameHomeNext = elementsNextMatch.get(1).attr("alt");
            String nameGuestNext = elementsNextMatch.get(2).attr("alt");
            String hrefHomeImageNM = elementsNextMatch.get(1).attr("src");
            String hrefGuestImageNM = elementsNextMatch.get(2).attr("src");

            if(nameHomeNext.equals("Реал")) {
                nameHomeNext = "Реал Мадрид";
            }else if (nameGuestNext.equals("Реал")){
                nameGuestNext = "Реал Мадрид";}

            //Получаем дату и название лиги для будущего матча
            Elements elementsDataNextMatch = document.select("#match_box_2 div");
            String dayMatchNext = elementsDataNextMatch.get(0).text();
            String tournirResNext = elementsDataNextMatch.get(1).text();

            //Прошлый матч
            //Получаем названия команд и изображения для прошлого матча
            Elements elementsLastMatch = document.select("#match_box_1 img");
            String nameHomeLast = elementsLastMatch.get(1).attr("alt");
            String nameGuestLast = elementsLastMatch.get(2).attr("alt");
            String hrefHomeImageLM = elementsLastMatch.get(1).attr("src");
            String hrefGuestImageLM = elementsLastMatch.get(2).attr("src");
            
            //Получаем дату и название лиги для будущего матча
            Elements elementsDataLastMatch = document.select("#match_box_1 div");
            String dayMatchLast = elementsDataLastMatch.get(0).text();
            String tournirResLast = elementsDataLastMatch.get(1).text();

            if(nameGuestLast.equals("ДВ")){
                nameGuestLast = elementsLastMatch.get(3).attr("alt");
                hrefGuestImageLM = elementsLastMatch.get(3).attr("src");
                tournirResLast = elementsDataLastMatch.get(2).text();
            }

            if(nameHomeLast.equals("Реал")) {
                nameHomeLast = "Реал Мадрид";
            }else if (nameGuestLast.equals("Реал")){
                nameGuestLast = "Реал Мадрид";}

            //Добавляем к массиву
            hrefsBitmap.add(hrefHomeImageLM);
            hrefsBitmap.add(hrefGuestImageLM);
            hrefsBitmap.add(hrefHomeImageNM);
            hrefsBitmap.add(hrefGuestImageNM);

            //Получаем результат прошлого матча
            Elements elementsResultLastMatch = document.select("#match_box_1 span");
            String resultLastMatch = elementsResultLastMatch.get(1).text();

            ContextWrapper contextWrapper = new ContextWrapper(futureMatch.getContext());
            Elements elementsTableImages = document.select(".teamlogo img");

            //Добавляем данные в ArrayList будущего матча
            arrayList.add(nameHomeNext); //0
            arrayList.add(nameGuestNext); //1
            arrayList.add(dayMatchNext);  //2
            arrayList.add(tournirResNext);  //3

            //Добавляем данные в ArrayList прошлого матча
            arrayList.add(nameHomeLast);  //4
            arrayList.add(nameGuestLast);  //5
            arrayList.add(dayMatchLast);  //6
            arrayList.add(tournirResLast);  //7
            arrayList.add(resultLastMatch);  //8

            for (String href : hrefsBitmap) {
                String teamNumber = href.replaceAll("[^0-9]", "") + ".png";
                String hrefTeamBitmap = "http://ss.sport-express.ru/img/football/commands/"+ teamNumber;

                InputStream in = new URL(hrefTeamBitmap).openStream();
                Bitmap bitmapTeam = BitmapFactory.decodeStream(in);
                in.close();

                File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
                File mypath = new File(directory, "/"+ teamNumber);
                FileOutputStream fos = new FileOutputStream(mypath);
                bitmapTeam.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                String dirBitmapTeam = directory.getAbsolutePath() + "/" + teamNumber;
                arrayList.add(dirBitmapTeam);
            }

            //Получаем номера команд в таблице
            Elements elementsTableNumbers = document.select("table.score td:eq(0)");
            for (int i = 5; i < 10; i++) {
                String resString = elementsTableNumbers.get(i).text();
                arrayList.add(resString);
            }

            //Получаем названия команд в таблице
            Elements elementsTableNames = document.select("table.score td:eq(2)");
            for (int i = 1; i < 6; i++) {
                String resString = elementsTableNames.get(i).text();
                arrayList.add(resString);
            }

            //Получаем количество игр в таблице
            Elements elementsTableGames = document.select("table.score td:eq(3)");
            for (int i = 1; i < 6; i++) {
                String resString = elementsTableGames.get(i).text();
                arrayList.add(resString);
            }

            //Получаем количество игр в таблице
            Elements elementsTablePosition = document.select("table.score td:eq(8)");
            for (int i = 0; i < 5; i++) {
                String resString = elementsTablePosition.get(i).text();
                arrayList.add(resString);
            }

            //Скачиваем, сохраняем на диск и записываем пути в базу изображения вкладки Чемпионат
            for (int i = 0; i < 5; i++) {
                String hrefTable = elementsTableImages.get(i).attr("src");
                String nameImageTable = hrefTable.replaceAll("[^0-9]", "") + ".png";
                hrefTable = "http://ss.sport-express.ru/img/football/commands/"+ nameImageTable;

                InputStream in = new URL(hrefTable).openStream();
                Bitmap bitmapTable = BitmapFactory.decodeStream(in);
                in.close();

                File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
                File mypath = new File(directory, "/"+ nameImageTable);
                FileOutputStream fos = new FileOutputStream(mypath);
                bitmapTable.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                String dirBitmapTable = directory.getAbsolutePath() + "/" + nameImageTable;
                arrayList.add(dirBitmapTable);
            }

            noInternetException = false;

        } catch (IOException e) {
            e.printStackTrace();
            noInternetException = true;
        }
        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> arrayList) {
        super.onPostExecute(arrayList);

        if(!noInternetException){
            sqlIteHelper.writeForDB(arrayList);
            futureMatch.mSwipeLayout.setRefreshing(false);

            futureMatch.setResultFutureMatch();
        }else {
            futureMatch.mSwipeLayout.setRefreshing(false);
            Toast.makeText(futureMatch.getContext(), R.string.internet_error, Toast.LENGTH_LONG).show();
        }
    }
}
