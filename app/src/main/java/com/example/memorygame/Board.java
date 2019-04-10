package com.example.memorygame;

import android.view.View;

import java.io.Serializable;

public class Board implements Serializable {
    private int[][] matrix_res;
    private int[] active_res = new int[16];
    public int allocatedActiveRes = 0;
    private static int[] image_res = {R.drawable.battery,R.drawable.brake,R.drawable.formula1,R.drawable.jacket,R.drawable.race,R.drawable.racecar,R.drawable.racinghelmet,R.drawable.steeringwheel,
            R.drawable.battery,R.drawable.brake,R.drawable.formula1,R.drawable.jacket,R.drawable.race,R.drawable.racecar,R.drawable.racinghelmet,R.drawable.steeringwheel};

    Board(){
        renderNewMatrixRes();
    }

    public int getActiveResId(int i){
        if(i<this.allocatedActiveRes && i>=0){
            return this.active_res[i];
        }
        return -1;
    }

    private void renderNewMatrixRes(){
        int len = image_res.length;
        this.matrix_res = new int[4][4];
        int ran_pos;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
               ran_pos = (int)(Math.random()*len);
               this.matrix_res[i][j]=image_res[ran_pos];
               int temp = image_res[ran_pos];
               image_res[ran_pos] = image_res[len-1];
               image_res[len-1] = temp;
               len--;
            }
        }
    }

    public void addNewActiveRes(int id){
        if(this.allocatedActiveRes<16){
            this.active_res[this.allocatedActiveRes]=id;
            this.allocatedActiveRes++;
        }
    }

    public void popNewActiveRes(int id){
        for(int i=0;i<this.allocatedActiveRes;i++){
            if(this.active_res[i]==id){
                this.active_res[i]=0;
                break;
            }
        }
        if(this.allocatedActiveRes!=0){
            this.allocatedActiveRes--;
        }
    }

    public int getImageResource(int id) {
        switch (id) {
            case R.id.one_one:
                return this.matrix_res[0][0];
            case R.id.one_two:
                return this.matrix_res[0][1];
            case R.id.one_three:
                return this.matrix_res[0][2];
            case R.id.one_four:
                return this.matrix_res[0][3];
            case R.id.two_one:
                return this.matrix_res[1][0];
            case R.id.two_two:
                return this.matrix_res[1][1];
            case R.id.two_three:
                return this.matrix_res[1][2];
            case R.id.two_four:
                return this.matrix_res[1][3];
            case R.id.three_one:
                return this.matrix_res[2][0];
            case R.id.three_two:
                return this.matrix_res[2][1];
            case R.id.three_three:
                return this.matrix_res[2][2];
            case R.id.three_four:
                return this.matrix_res[2][3];
            case R.id.four_one:
                return this.matrix_res[3][0];
            case R.id.four_two:
                return this.matrix_res[3][1];
            case R.id.four_three:
                return this.matrix_res[3][2];
            case R.id.four_four:
                return this.matrix_res[3][3];
        }
        return this.matrix_res[0][0];
    }
}
