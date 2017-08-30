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
        sqlIteHelper = new SQLIteHelper(futureMatch.getContext());

        try {
            Document document = Jsoup.connect("http://football.sport-express.ru/command/68/").get();

            //Будущий матч
            //Получаем названия команд и изображения для будущего матча
            Elements elementsNextMatch = document.select("#match_box_2 img");
            String nameHomeNext = elementsNextMatch.get(1).attr("alt");
            String nameGuestNext = elementsNextMatch.get(2).attr("alt");

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

            //Получаем результат прошлого матча
            Elements elementsResultLastMatch = document.select("#match_box_1 span");
            String resultLastMatch = elementsResultLastMatch.get(1).text();

            ContextWrapper contextWrapper = new ContextWrapper(futureMatch.getContext());

            ////1-ая картинка
            String hrefHomeImageNM = elementsNextMatch.get(1).attr("src");
            String nameImageHomeNM = hrefHomeImageNM.replaceAll("[^0-9]", "") + ".png";
            hrefHomeImageNM = "http://ss.sport-express.ru/img/football/commands/"+ nameImageHomeNM;

            InputStream in = new URL(hrefHomeImageNM).openStream();
            Bitmap bitmapHomeNM = BitmapFactory.decodeStream(in);
            in.close();

            File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath = new File(directory, "/"+ nameImageHomeNM);
            FileOutputStream fos = new FileOutputStream(mypath);
            bitmapHomeNM.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            String dirBitmapHomeNM = directory.getAbsolutePath() + "/" + nameImageHomeNM;

            /////2-ая картинка
            String hrefGuestImageNM = elementsNextMatch.get(2).attr("src");
            String nameImageGuestNM = hrefGuestImageNM.replaceAll("[^0-9]", "") + ".png";
            hrefGuestImageNM = "http://ss.sport-express.ru/img/football/commands/"+ nameImageGuestNM;

            in = new URL(hrefGuestImageNM).openStream();
            Bitmap bitmapGuestNM = BitmapFactory.decodeStream(in);
            in.close();

            directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            mypath = new File(directory, "/"+ nameImageGuestNM);
            fos = new FileOutputStream(mypath);
            bitmapGuestNM.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            String dirBitmapGuestNM = directory.getAbsolutePath() + "/" + nameImageGuestNM;

            /////3-я картинка
            String hrefHomeImageLM = elementsLastMatch.get(1).attr("src");
            String nameImageHomeLM = hrefHomeImageLM.replaceAll("[^0-9]", "") + ".png";
            hrefHomeImageLM = "http://ss.sport-express.ru/img/football/commands/"+ nameImageHomeLM;

            in = new URL(hrefHomeImageLM).openStream();
            Bitmap bitmapHomeLM = BitmapFactory.decodeStream(in);
            in.close();

            directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            mypath = new File(directory, "/"+ nameImageHomeLM);
            fos = new FileOutputStream(mypath);
            bitmapHomeLM.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            String dirBitmapHomeLM = directory.getAbsolutePath() + "/" + nameImageHomeLM;

            /////4-ая картинка
            String nameImageGuestLM = hrefGuestImageLM.replaceAll("[^0-9]", "") + ".png";
            hrefGuestImageLM = "http://ss.sport-express.ru/img/football/commands/"+ nameImageGuestLM;

            in = new URL(hrefGuestImageLM).openStream();
            Bitmap bitmapGuestLM = BitmapFactory.decodeStream(in);
            in.close();

            directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            mypath = new File(directory, "/"+ nameImageGuestLM);
            fos = new FileOutputStream(mypath);
            bitmapGuestLM.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            String dirBitmapGuestLM = directory.getAbsolutePath() + "/" + nameImageGuestLM;

            Elements elementsTableImages = document.select(".teamlogo img");

            //Добавляем данные в ArrayList
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

            //Добавляем пути изображений
            arrayList.add(dirBitmapHomeLM);  //9
            arrayList.add(dirBitmapGuestLM);  //10
            arrayList.add(dirBitmapHomeNM);  //11
            arrayList.add(dirBitmapGuestNM);  //12


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

                in = new URL(hrefTable).openStream();
                Bitmap bitmapTable = BitmapFactory.decodeStream(in);
                in.close();

                directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
                mypath = new File(directory, "/"+ nameImageTable);
                fos = new FileOutputStream(mypath);
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
