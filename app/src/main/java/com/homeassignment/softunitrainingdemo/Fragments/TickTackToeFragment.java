package com.homeassignment.softunitrainingdemo.Fragments;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.homeassignment.softunitrainingdemo.Async.ComputerMoveTask;
import com.homeassignment.softunitrainingdemo.Constants;
import com.homeassignment.softunitrainingdemo.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TickTackToeFragment extends Fragment implements View.OnClickListener{


    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;
    ImageButton btn4;
    ImageButton btn5;
    ImageButton btn6;
    ImageButton btn7;
    ImageButton btn8;
    ImageButton btn9;
    ArrayList<ImageButton> allCells = new ArrayList<>();

int[][] field = new int[3][3];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tick_tack_toe, container, false);
        if (savedInstanceState == null){

            initializeBtns(view);
    }else{
            String  result = savedInstanceState.getString(Constants.SERVICE_RESULT);
            if (result!=null) {

                setBtnsByServiceResult(result);

            }
        }
        addCellInList();
        // Inflate the layout for this fragment
        return view;


    }

    private void setBtnsByServiceResult(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            String matrix = obj.getString("Field");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initializeBtns(View view) {


        btn1 = (ImageButton)view.findViewById(R.id.imageButton1);
        btn1.setTag(Constants.EMPTY_CELL);
        btn1.setOnClickListener(this);

        btn2 = (ImageButton)view.findViewById(R.id.imageButton2);
        btn2.setTag(Constants.EMPTY_CELL);
        btn2.setOnClickListener(this);

        btn3 = (ImageButton)view.findViewById(R.id.imageButton3);
       btn3.setTag(Constants.EMPTY_CELL);
        btn3.setOnClickListener(this);

        btn4 = (ImageButton)view.findViewById(R.id.imageButton4);
        btn4.setTag(Constants.EMPTY_CELL);
        btn4.setOnClickListener(this);

        btn5 = (ImageButton)view.findViewById(R.id.imageButton5);
        btn5.setTag(Constants.EMPTY_CELL);
        btn5.setOnClickListener(this);

        btn6 = (ImageButton)view.findViewById(R.id.imageButton6);
        btn6.setTag(Constants.EMPTY_CELL);
        btn6.setOnClickListener(this);

        btn7 = (ImageButton)view.findViewById(R.id.imageButton7);
       btn7.setTag(Constants.EMPTY_CELL);
        btn7.setOnClickListener(this);

        btn8 = (ImageButton)view.findViewById(R.id.imageButton8);
       btn8.setTag(Constants.EMPTY_CELL);
        btn8.setOnClickListener(this);

        btn9 = (ImageButton)view.findViewById(R.id.imageButton9);
        btn9.setTag(Constants.EMPTY_CELL);
        btn9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:

                 makeMove(btn1);

                break;
            case R.id.imageButton2:
                makeMove(btn2);

                break;
            case R.id.imageButton3:
                makeMove(btn3);
                break;
            case R.id.imageButton4:
                makeMove(btn4);
                break;
            case R.id.imageButton5:
                makeMove(btn5);
                break;
            case R.id.imageButton6:
                makeMove(btn6);
                break;
            case R.id.imageButton7:
                makeMove(btn7);
                break;
            case R.id.imageButton8:
                makeMove(btn8);
                break;
            case R.id.imageButton9:
                makeMove(btn9);
                break;
        }
    }

  private void  makeMove(ImageButton btn){

      String tag = String.valueOf(btn.getTag()) ;
      if (tag== Constants.EMPTY_CELL)//getResources().getDrawable(R.drawable.empty_cell))
      {
          btn.setBackgroundResource(R.drawable.filled_cell);
          btn.setTag(Constants.PLAYER_CELL);
          getFieldByCells();


      String fieldAsString = getFieldAsString();
          try {
             // FragmentTransaction transaction = getFragmentManager().beginTransaction();
              ComputerMoveTask task  = new ComputerMoveTask(allCells,getActivity());
              task.execute(Constants.PATH_TO_SERVICE,fieldAsString);
          } catch (Exception e) {
              e.printStackTrace();
          }
          //call http post to make move
          //get result form check for end
      }
      else
      {
          makeToast("This cell is full");
      }
    }

    private String getFieldAsString() {
        String result = "";
        for (int row=0; row < field.length; row++)
        {
            for (int col=0; col < field[row].length; col++)
            {
                result+=field[row][col]+ ",";

            }
        }

        return  result;
    }

    private void getFieldByCells() {

        //0,0
        setFieldCellByButton(btn1,0,0);
        setFieldCellByButton(btn2,0,1);
        setFieldCellByButton(btn3,0,2);
        setFieldCellByButton(btn4,1,0);
        setFieldCellByButton(btn5,1,1);
        setFieldCellByButton(btn6,1,2);
        setFieldCellByButton(btn7,2,0);
        setFieldCellByButton(btn8,2,1);
        setFieldCellByButton(btn9,2,2);




    }

    private  void setFieldCellByButton(ImageButton btn,int row,int col){
        String tag =String.valueOf(btn.getTag() );
        if (tag==Constants.EMPTY_CELL){
            field[row][col] = Constants.EMPTY_CELL_VALUE;
        }else if(tag==Constants.COMPUTER_CELL){
            field[row][col] = Constants.COMPUTER_CELL_VALUE;
        }else if(tag==Constants.PLAYER_CELL){
            field[row][col] = Constants.PLAYER_CELL_VALUE;
        }
    }





    private  void makeToast(String message)
{
    Toast toast = Toast.makeText(getActivity(),message,Toast.LENGTH_LONG);
    toast.show();
}



    private void addCellInList(){

       allCells.add(btn1);
       allCells.add(btn2);
       allCells.add(btn3);
       allCells.add(btn4);
       allCells.add(btn5);
       allCells.add(btn6);
       allCells.add(btn7);
       allCells.add(btn8);
       allCells.add(btn9);
    }



}
