package com.homeassignment.softunitrainingdemo.Async;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.homeassignment.softunitrainingdemo.Constants;
import com.homeassignment.softunitrainingdemo.Fragments.TickTackToeFragment;
import com.homeassignment.softunitrainingdemo.R;
import com.homeassignment.softunitrainingdemo.TickTackToe;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Bon on 5/25/2015.
 */
public class ComputerMoveTask extends AsyncTask<String, Void, String> {

   Context context;
//    Fragment gameFragment;
//    FragmentTransaction gameTransaction;
    ArrayList<ImageButton> allBtns;
    public ComputerMoveTask( ArrayList<ImageButton> btns,Context context){//, Fragment fragment,FragmentTransaction transaction) {
        allBtns = btns;
        this.context = context;
//        this.gameFragment = fragment;
//        gameTransaction = transaction;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("DOWNLOAD", "Starting now...");
    }

    @Override
    protected String doInBackground(String... params) {

        String resultString = "";

        try {

            String urlStr = params[0];
            String matrixAsString = params[1];

            URL url = new URL(urlStr + matrixAsString);

            HttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost(String.valueOf(url));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//            nameValuePairs.add(new BasicNameValuePair("mastrixAsString", matrixAsString   ));
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httpPost);



            resultString = getStringByResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }


        return resultString;
    }

    private String getStringByResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String resultString;InputStream inputStream = entity.getContent();
        InputStreamReader byteReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader reader = new BufferedReader(byteReader, 8);
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        resultString = sb.toString();

        if(inputStream != null) {
            inputStream.close();
        }
        return resultString;
    }

    private static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext())
        {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry)iter.next();

            //creates a key for Map
            String key = (String)pairs.getKey();

            //Create a new map
            int[][] m = (int[][])pairs.getValue();

//            //object for storing Json
//            JSONObject data = new JSONObject();
//
//            //gets the value
//            Iterator iter2 = m.entrySet().iterator();
//            while (iter2.hasNext())
//            {
//                Map.Entry pairs2 = (Map.Entry)iter2.next();
//                data.put((String)pairs2.getKey(), (String)pairs2.getValue());
//            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, m);
        }
        return holder;
    }



    @Override
    protected void onPostExecute(String result) {


        try {
            int[][] resultField = new int[3][3] ;
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("Field");
            JSONArray firstRow = arr.getJSONArray(0);
            JSONArray socondRow = arr.getJSONArray(1);
            JSONArray thirdRow = arr.getJSONArray(2);



            resultField[0][0] = firstRow.getInt(0);
            resultField[0][1] = firstRow.getInt(1);
            resultField[0][2] = firstRow.getInt(2);
            resultField[1][0] = socondRow.getInt(0);
            resultField[1][1] = socondRow.getInt(1);
            resultField[1][2] = socondRow.getInt(2);
            resultField[2][0] = thirdRow.getInt(0);
            resultField[2][1] = thirdRow.getInt(1);
            resultField[2][2] = thirdRow.getInt(2);


            boolean isEnd = obj.getBoolean("IsEndGame");
            int winnerPlayerNumber = obj.getInt("WinnerNumber");


            setFieldCellByButton(resultField,allBtns.get(0),0,0);
            setFieldCellByButton(resultField,allBtns.get(1),0,1);
            setFieldCellByButton(resultField,allBtns.get(2),0,2);
            setFieldCellByButton(resultField,allBtns.get(3),1,0);
            setFieldCellByButton(resultField,allBtns.get(4),1,1);
            setFieldCellByButton(resultField,allBtns.get(5),1,2);
            setFieldCellByButton(resultField,allBtns.get(6),2,0);
            setFieldCellByButton(resultField,allBtns.get(7),2,1);
            setFieldCellByButton(resultField,allBtns.get(8),2,2);

            if (isEnd==true){
                String message = "";
                if (winnerPlayerNumber==Constants.PLAYER_CELL_VALUE){
                    message = "Congrats, you have won!";
                }else if(winnerPlayerNumber  == Constants.COMPUTER_CELL_VALUE){
                    message = "Sorry, you have lost!";
                }else{
                    message = "Drow!";
                }
                Toast toast = Toast.makeText( context,message,Toast.LENGTH_LONG);
                toast.show();

//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                Intent startGame = new Intent (context,TickTackToe.class);
                context.startActivity(startGame);
            }

//            for (int i=0;i<allBtns.size();i++) {
//                allBtns.get(i).setTag(Constants.COMPUTER_CELL_VALUE);
//                allBtns.get(i).setBackgroundResource(R.drawable.filed_cell_oponent);
//
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private  void setFieldCellByButton(int[][] field,ImageButton btn,int row,int col){

        if(field[row][col]== Constants.PLAYER_CELL_VALUE){
            btn.setTag(Constants.PLAYER_CELL);
            btn.setBackgroundResource(R.drawable.filled_cell);
        }else if(field[row][col]== Constants.COMPUTER_CELL_VALUE){
            btn.setTag(Constants.COMPUTER_CELL);
            btn.setBackgroundResource(R.drawable.filed_cell_oponent);
        }

//        if (tag==Constants.EMPTY_CELL){
//            field[row][col] = Constants.EMPTY_CELL_VALUE;
//        }else if(tag==Constants.COMPUTER_CELL){
//            field[row][col] = Constants.COMPUTER_CELL_VALUE;
//        }else if(tag==Constants.PLAYER_CELL){
//            field[row][col] = Constants.PLAYER_CELL_VALUE;
//        }
    }

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.common_signin_btn_icon_dark)
//                        .setContentTitle("Download finished")
//                        .setContentText("The download of the startbucks shops just finished!");
//
//        // Immediately display the notification icon in the notification bar.
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, mBuilder.build());
//
//
//
//        Intent intent = new Intent(context,MapsActivity.class);
//        intent.putExtra("NotFirstTime",true);
//        try {
//            //  JSONParser.LogJson(new JSONObject(result));
//            JSONParser.addToDataBase(new JSONObject(result),context);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        context.startActivity(intent);
    }


