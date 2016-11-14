package com.krishna.tictactoe;

import android.animation.Animator;
import android.app.admin.SystemUpdatePolicy;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageView> imgs = new ArrayList<>();
    int numberOfTurns = 0;
    int playerNumber = 1;
    int[] gameState = {0,0,0,0,0,0,0,0,0};
    int[][] resultArray = {{10,10,10},{10,10,10},{10,10,10}};
    public void drop(View view){
        numberOfTurns++;
        ImageView img = (ImageView) view;
        imgs.add(img);
        int imgTag = Integer.parseInt(img.getTag().toString());
        final int current = playerNumber;
        if(gameState[imgTag]==0){
            img.setTranslationY(-1000f);
            if(playerNumber==1){
                img.setImageResource(R.drawable.x);
                gameState[imgTag] = 1;
                playerNumber++;
            }else{
                img.setImageResource(R.drawable.o);
                gameState[imgTag] = 2;
                playerNumber--;
            }

            img.animate().setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if(isGameOver()) {
                        Toast.makeText(getApplicationContext(), "Player " + current + " won!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if(isGameOver()) {
                        try
                        {
                            reset();
                        } catch(InterruptedException ex)
                        {
                            return;
                        }

                    }

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).translationYBy(1000f).setDuration(350);

            updateResultArray(img, current);

        }
    }

    public void updateResultArray(ImageView view, int current){
        System.out.println(view.getTag().toString());
        switch (view.getTag().toString()){
            case "0":
                resultArray[0][0] = current;
                break;
            case "1":
                resultArray[0][1] = current;
                break;
            case "2":
                resultArray[0][2] = current;
                break;
            case "3":
                resultArray[1][0] = current;
                break;
            case "4":
                resultArray[1][1] = current;
                break;
            case "5":
                resultArray[1][2] = current;
                break;
            case "6":
                resultArray[2][0] = current;
                break;
            case "7":
                resultArray[2][1] = current;
                break;
            case "8":
                resultArray[2][2] = current;
                break;
        }
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(resultArray[i][j]);
            }
            System.out.println();
        }
    }


    public boolean isGameOver(){
        if(numberOfTurns == 9)
            return true;
        boolean result = false;
        int leftDiagonalSum=0;
        int rightDiagonalSum=0;

        // Checking rows and left diagonal
        for(int i=0;i<3;i++){
            int rowSum = 0;
            int columnSum = 0;
            for(int j=0;j<3;j++){
                rowSum += resultArray[i][j];
                columnSum += resultArray[j][i];
                if(i == j)
                    leftDiagonalSum += resultArray[i][j];
                if(i+j == 2)
                    rightDiagonalSum += resultArray[i][j];
            }
            if(rowSum == 6 || rowSum ==3 || columnSum ==6 || columnSum ==3) {
                result = true;
                break;
            }
        }

        // Check diagonal sum
        if(leftDiagonalSum==3 || leftDiagonalSum==6 || rightDiagonalSum==3 || rightDiagonalSum==6)
            result = true;

        System.out.println(result);

        return result;
    }

    public void reset() throws InterruptedException {
        for(int i=0;i<9;i++)
            gameState[i]=0;

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                resultArray[i][j]=10;

        playerNumber = 1;
        numberOfTurns = 0;

        Thread.sleep(2000);

        for(ImageView img : imgs)
        {
            img.setImageDrawable(null);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
