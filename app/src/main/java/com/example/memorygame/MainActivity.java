package com.example.memorygame;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int[][] matrix_id = {{R.id.one_one,R.id.one_two,R.id.one_three,R.id.one_four},
            {R.id.two_one,R.id.two_two,R.id.two_three,R.id.two_four},
            {R.id.three_one,R.id.three_two,R.id.three_three,R.id.three_four},
            {R.id.four_one,R.id.four_two,R.id.four_three,R.id.four_four}};
    private final int rowNum = 4;
    private final int colNum = 4;
    private Board res_board;

    private ImageButton firstActive;
    private ImageButton secondActive;
    private int activeNum = 0;
    private int numberOfPairs = 0;

    private static final String RES_BOARD_KEY = "res_board";
    private static final String FIRST_ACTIVE_KEY = "firstActive";
    private static final String SECOND_ACTIVE_KEY = "secondActive";
    private static final String ACTIVE_NUM_KEY = "activeNum";
    private static final String NUMBER_OF_PAIRS_KEY = "numberOfPairs";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RES_BOARD_KEY,this.res_board);
        if(this.firstActive!=null){
            outState.putInt(FIRST_ACTIVE_KEY,this.firstActive.getId());
        }
        if(this.secondActive!=null){
            outState.putInt(SECOND_ACTIVE_KEY,this.secondActive.getId());
        }
        outState.putInt(ACTIVE_NUM_KEY,this.activeNum);
        outState.putInt(NUMBER_OF_PAIRS_KEY,this.numberOfPairs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListeners();
        if(savedInstanceState != null){
            this.res_board = (Board)savedInstanceState.getSerializable(RES_BOARD_KEY);
            this.activeNum = savedInstanceState.getInt(ACTIVE_NUM_KEY);
            this.numberOfPairs = savedInstanceState.getInt(NUMBER_OF_PAIRS_KEY);
            if(this.activeNum == 1){
                this.firstActive = findViewById(savedInstanceState.getInt(ACTIVE_NUM_KEY));
            }
            else if(this.activeNum == 2){
                this.secondActive = findViewById(savedInstanceState.getInt(SECOND_ACTIVE_KEY));
            }
        }
        else{
            this.res_board = new Board();
        }
        setResBoard();
    }

    @Override
    public void onClick(View v){
        if(this.activeNum == 0){
            this.firstActive = (ImageButton)v;
            this.firstActive.setImageResource(this.res_board.getImageResource(v.getId()));
            this.res_board.addNewActiveRes(v.getId());
            this.firstActive.setClickable(false);
            this.activeNum++;
        }
        else if(this.activeNum == 1){
            this.secondActive = (ImageButton)v;
            this.secondActive.setImageResource(this.res_board.getImageResource(v.getId()));
            this.res_board.addNewActiveRes(v.getId());
            this.secondActive.setClickable(false);
            this.activeNum++;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(res_board.getImageResource(firstActive.getId()) != res_board.getImageResource(secondActive.getId())){
                        firstActive.setImageResource(R.drawable.logo);
                        res_board.popNewActiveRes(firstActive.getId());
                        secondActive.setImageResource(R.drawable.logo);
                        res_board.popNewActiveRes(secondActive.getId());
                        firstActive.setClickable(true);
                        secondActive.setClickable(true);
                    }
                    else{
                        numberOfPairs++;
                        if(numberOfPairs==8){
                            Toast.makeText(MainActivity.this,"You won! Congratulation.",Toast.LENGTH_LONG);
                        }
                    }
                    activeNum = 0;
                }
            }, 1000);
        }
    }

    private void setListeners(){
        for(int i=0;i<this.rowNum;i++){
            for(int j=0;j<this.colNum;j++){
                findViewById(matrix_id[i][j]).setOnClickListener(this);
            }
        }
    }

    private void setResBoard(){
        for(int i=0;i<this.res_board.allocatedActiveRes;i++){
            ImageButton temp = findViewById(this.res_board.getActiveResId(i));
            temp.setImageResource(this.res_board.getImageResource(this.res_board.getActiveResId(i)));
            temp.setClickable(false);
        }
    }
}
