package com.haarismemon.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.transition.move;
import static com.haarismemon.connect3.R.drawable.yellow;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    Boolean isRedPlayerTurn = false;

    Boolean[][] gameState = new Boolean[3][3];

    boolean isGameOver = false;

    int numberOfMoves = 0;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        int row = tappedCounter / 3;
        int column = tappedCounter % 3;

        if(gameState[row][column] == null && !isGameOver) {

            gameState[row][column] = isRedPlayerTurn;

            ++numberOfMoves;

            counter.setTranslationY(-1000f);

            if (isRedPlayerTurn) {
                counter.setImageResource(R.drawable.red);
            } else {
                counter.setImageResource(yellow);
            }

            counter.animate().translationYBy(1000f).rotation(36000).setDuration(300);

            //check if game won
            if(checkWinner(isRedPlayerTurn, row, column)) {

                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                String winner = "";
                if(isRedPlayerTurn) winner = "Red";
                else winner = "Yellow";

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner + " has won!");

                isGameOver = true;

            } else if(numberOfMoves == 9) {

                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText("Draw!");

                isGameOver = true;

            } else {

                TextView turnText = (TextView) findViewById(R.id.turnText);

                if (isRedPlayerTurn) {
                    isRedPlayerTurn = false;
                    turnText.setText("Yellow's Turn");
                    turnText.setBackgroundColor(Color.parseColor("#fff200"));
                } else {
                    isRedPlayerTurn = true;
                    turnText.setText("Red's Turn");
                    turnText.setBackgroundColor(Color.parseColor("#ff2f00"));
                }

            }
        }
    }

    private boolean checkWinner(boolean isRedPlayer, int rowMove, int columnMove) {

        Boolean checkRow = gameState[rowMove][0];

        if(checkRow != null && checkRow.equals(gameState[rowMove][1]) &&
                checkRow.equals(gameState[rowMove][2])) {
            return true;
        }

        Boolean checkCol = gameState[0][columnMove];

        if(checkCol != null && checkCol.equals(gameState[1][columnMove]) &&
                checkCol.equals(gameState[2][columnMove])) {
            return true;
        }

        Boolean checkForwardDiagonal = gameState[2][0];

        if(checkForwardDiagonal != null && checkForwardDiagonal.equals(gameState[1][1]) &&
                checkForwardDiagonal.equals(gameState[0][2])) {
            return true;
        }

        Boolean checkBackDiagonal = gameState[0][0];

        if(checkBackDiagonal != null && checkBackDiagonal.equals(gameState[1][1]) &&
                checkBackDiagonal.equals(gameState[2][2])) {
            return true;
        }

        return false;
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        isRedPlayerTurn = false;

        for(int i = 0; i < gameState.length; ++i) {
            for (int j = 0; j < gameState[i].length; j++) {
                gameState[i][j] = null;
            }
        }

        GridLayout gridlayout = (GridLayout) findViewById(R.id.gridlayout);
        for (int i = 0; i < gridlayout.getChildCount(); i++) {
            ((ImageView) gridlayout.getChildAt(i)).setImageResource(0);
        }

        isGameOver = false;

        numberOfMoves = 0;

        TextView turnText = (TextView) findViewById(R.id.turnText);
        turnText.setText("Yellow's Turn");
        turnText.setBackgroundColor(Color.parseColor("#fff200"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
