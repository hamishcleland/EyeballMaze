package com.example.eyeball_maze;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.eyeball_maze.Model.*;
import com.google.android.material.snackbar.Snackbar;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Game theGame;
    ImageView[][] levelImages = new ImageView[4][6];
    Menu levelSelect;
    MediaPlayer mp;

    int totalGoals;

    int mpVolume = 1;
    Chronometer timeText;
    long timeWhenStopped;

    //On Creation of Level
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.win_sound);
        levelImages[0][0] = findViewById(R.id.square0_0);
        levelImages[1][0] = findViewById(R.id.square1_0);
        levelImages[2][0] = findViewById(R.id.square2_0);
        levelImages[3][0] = findViewById(R.id.square3_0);
        levelImages[0][1] = findViewById(R.id.square0_1);
        levelImages[1][1] = findViewById(R.id.square1_1);
        levelImages[2][1] = findViewById(R.id.square2_1);
        levelImages[3][1] = findViewById(R.id.square3_1);
        levelImages[0][2] = findViewById(R.id.square0_2);
        levelImages[1][2] = findViewById(R.id.square1_2);
        levelImages[2][2] = findViewById(R.id.square2_2);
        levelImages[3][2] = findViewById(R.id.square3_2);
        levelImages[0][3] = findViewById(R.id.square0_3);
        levelImages[1][3] = findViewById(R.id.square1_3);
        levelImages[2][3] = findViewById(R.id.square2_3);
        levelImages[3][3] = findViewById(R.id.square3_3);
        levelImages[0][4] = findViewById(R.id.square0_4);
        levelImages[1][4] = findViewById(R.id.square1_4);
        levelImages[2][4] = findViewById(R.id.square2_4);
        levelImages[3][4] = findViewById(R.id.square3_4);
        levelImages[0][5] = findViewById(R.id.square0_5);
        levelImages[1][5] = findViewById(R.id.square1_5);
        levelImages[2][5] = findViewById(R.id.square2_5);
        levelImages[3][5] = findViewById(R.id.square3_5);
        getSupportActionBar().setTitle("Eyeball Maze!");
        getSupportActionBar().setIcon(R.drawable.baseline_remove_red_eye_24);
    }

    //Start Game Button Method
    public void startGameClick(View view) {
        this.addGame();
        for (int i = 0; i < theGame.getLevelHeight(); i++) {
            for (int j = 0; j < theGame.getLevelWidth(); j++) {
                updateImages(i, j);
            }
        }
        this.addEyeballView();
        this.highlightGoals();
    }

    //Sets Volume to Either 0 or 1 based on sound toggle
    public void soundToggleOnClick(View view) {
        Switch soundToggle = findViewById(R.id.soundToggle);
        if (soundToggle.isChecked()) {
            mpVolume = 1;
            mp.setVolume(mpVolume, mpVolume);
        } else {
            mpVolume = 0;
            mp.setVolume(mpVolume, mpVolume);
        }
    }

    //Adds a Game with the Selected Level
    public void addGame() {
        String levelName;
        theGame = new Game();
        if (levelSelect.findItem(R.id.level1select).isChecked()) {
            levelName = this.Level1();
        } else if ((levelSelect.findItem(R.id.level2select).isChecked())) {
            levelName = this.Level2();
        } else if (((levelSelect.findItem(R.id.level3select).isChecked()))) {
            levelName = this.Level3();
        } else {
            levelName = "No Level Selected";
        }
        TextView levelNameText = findViewById(R.id.currentLevelText);
        TextView levelTitleText = findViewById(R.id.currentLevelTitle);
        TextView goalCountTitle = findViewById(R.id.goalCountTitle);
        TextView goalCount = findViewById(R.id.goalCount);
        TextView moveCountTitle = findViewById(R.id.moveCountTitle);
        TextView moveCount = findViewById(R.id.moveCount);
        TextView timerTitle = findViewById(R.id.timerTitle);
        timeText = findViewById(R.id.timeText);
        levelNameText.setText(levelName);
        levelTitleText.setVisibility(View.VISIBLE);
        goalCountTitle.setVisibility(View.VISIBLE);
        moveCountTitle.setVisibility(View.VISIBLE);
        timerTitle.setVisibility(View.VISIBLE);
        timeText.setVisibility(View.VISIBLE);
        totalGoals = theGame.getGoalCount();
        goalCount.setText(theGame.getGoalCount() + " of " + totalGoals);
        moveCount.setText(String.valueOf(theGame.getMoveCount()));
        if (timeText != null) {
            timeText.setBase(SystemClock.elapsedRealtime());
        }
        timeWhenStopped = 0;
        timeText.start();
    }

    //The contents for Level 1
    public String Level1() {
        String levelName = "Level 1";
        theGame.addLevel(4, 6);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.STAR), 0, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.DIAMOND), 1, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 2, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 3, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 0, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 2);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 2, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 3, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 0, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 1, 3);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 2, 3);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 3, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 0, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.FLOWER), 1, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 2, 4);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 3, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 1, 0);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 2, 5);
        theGame.addEyeball(1, 0, Direction.UP);
        theGame.addGoal(2, 5);
        return levelName;
    }

    //The contents for Level 2
    public String Level2() {
        String levelName = "Level 2";
        theGame.addLevel(4, 6);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 0, 1);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 1, 1);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.DIAMOND), 2, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 3, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.STAR), 0, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 2, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 3, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 0, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 1, 3);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 2, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 3, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.DIAMOND), 0, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 1, 4);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 2, 4);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.DIAMOND), 3, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 0);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 2, 5);
        theGame.addEyeball(1, 0, Direction.UP);
        theGame.addGoal(2, 5);
        return levelName;
    }

    //The contents for Level 3
    public String Level3() {
        String levelName = "Level 3";
        theGame.addLevel(4, 6);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.STAR), 0, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.DIAMOND), 1, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 2, 1);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 3, 1);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 0, 2);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.FLOWER), 1, 2);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 2, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 3, 2);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 0, 3);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.STAR), 1, 3);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 2, 3);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 3, 3);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.CROSS), 0, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.FLOWER), 1, 4);
        theGame.addSquare(new PlayableSquare(Color.YELLOW, Shape.DIAMOND), 2, 4);
        theGame.addSquare(new PlayableSquare(Color.GREEN, Shape.CROSS), 3, 4);
        theGame.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 1, 0);
        theGame.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 2, 5);
        theGame.addEyeball(1, 0, Direction.UP);
        theGame.addGoal(2, 5);
        theGame.addGoal(3, 4);
        return levelName;
    }

    //The method that runs when a square is clicked
    public void squareOnClick(View view) {
        String position = view.getTag().toString();
        Integer xCoNew = Integer.valueOf(String.valueOf(position.charAt(0)));
        Integer yCoNew = Integer.valueOf(String.valueOf(position.charAt(2)));
        //If false, play error messages
        if (!theGame.canMoveTo(yCoNew, xCoNew)) {
            String alertMessage = "You cannot make this move";
            Message moveMessage = theGame.MessageIfMovingTo(yCoNew, xCoNew);
            if (moveMessage == Message.BACKWARDS_MOVE) {
                alertMessage = "You are cannot move back the way you came, please try another move";
                mp.release();
                mp = MediaPlayer.create(this,R.raw.backwards_move_error);
                mp.setVolume(mpVolume,mpVolume);
                mp.start();
            } else if (moveMessage == Message.MOVING_DIAGONALLY) {
                alertMessage = "You are cannot move diagonally, please try another move";
                mp.release();
                mp = MediaPlayer.create(this,R.raw.diagonal_move_error);
                mp.setVolume(mpVolume,mpVolume);
                mp.start();
            } else if (moveMessage == Message.MOVING_OVER_BLANK) {
                alertMessage = "You are cannot move over blank squares, please try another move";
                mp.release();
                mp = MediaPlayer.create(this,R.raw.blank_shapes_error);
                mp.setVolume(mpVolume,mpVolume);
                mp.start();
            } else if (moveMessage == Message.DIFFERENT_SHAPE_OR_COLOR) {
                alertMessage = "You are cannot move to a different shape or colour, please try another move";
                mp.release();
                mp = MediaPlayer.create(this,R.raw.different_shape_color);
                mp.setVolume(mpVolume,mpVolume);
                mp.start();
            }
            Snackbar snackbar = Snackbar.make(view, alertMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            //If true, move to square
            this.removeEyeballView();
            theGame.moveTo(yCoNew, xCoNew);
            this.addEyeballView();
            this.highlightGoals();
            TextView goalCount = findViewById(R.id.goalCount);
            TextView moveCount = findViewById(R.id.moveCount);
            goalCount.setText(String.valueOf(theGame.getGoalCount()) + " of " + totalGoals);
            moveCount.setText(String.valueOf(theGame.getMoveCount()));
        }
        //If game is over, complete actions
        if (theGame.getGoalCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("You Won the Game in " + theGame.getMoveCount() + " Moves!!!")
                    .setMessage("Do you Want to Restart?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            addGame();
                            for (int i = 0; i < theGame.getLevelHeight(); i++) {
                                for (int j = 0; j < theGame.getLevelWidth(); j++) {
                                    updateImages(i, j);
                                }
                            }
                            addEyeballView();
                            highlightGoals();
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.baseline_celebration_24)
                    .show();
            mp = MediaPlayer.create(this,R.raw.win_sound);
            mp.setVolume(mpVolume,mpVolume);
            mp.start();
            timeText.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        levelSelect = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Sets up levels based on the selected items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.level1select:
                item.setChecked(true);
                levelSelect.findItem(R.id.level2select).setChecked(false);
                levelSelect.findItem(R.id.level3select).setChecked(false);
                this.addGame();
                for (int i = 0; i < theGame.getLevelHeight(); i++) {
                    for (int j = 0; j < theGame.getLevelWidth(); j++) {
                        updateImages(i, j);
                    }
                }
                this.addEyeballView();
                this.highlightGoals();
                return (true);
            case R.id.level2select:
                item.setChecked(true);
                levelSelect.findItem(R.id.level1select).setChecked(false);
                levelSelect.findItem(R.id.level3select).setChecked(false);
                this.addGame();
                for (int i = 0; i < theGame.getLevelHeight(); i++) {
                    for (int j = 0; j < theGame.getLevelWidth(); j++) {
                        updateImages(i, j);
                    }
                }
                this.addEyeballView();
                this.highlightGoals();
                return (true);
            case R.id.level3select:
                levelSelect.findItem(R.id.level1select).setChecked(false);
                levelSelect.findItem(R.id.level2select).setChecked(false);
                item.setChecked(true);
                this.addGame();
                for (int i = 0; i < theGame.getLevelHeight(); i++) {
                    for (int j = 0; j < theGame.getLevelWidth(); j++) {
                        updateImages(i, j);
                    }
                }
                this.addEyeballView();
                this.highlightGoals();
                return (true);
            case R.id.restartGame:
                this.addGame();
                for (int i = 0; i < theGame.getLevelHeight(); i++) {
                    for (int j = 0; j < theGame.getLevelWidth(); j++) {
                        updateImages(i, j);
                    }
                }
                this.addEyeballView();
                this.highlightGoals();
                return (true);
            case R.id.undoButton:
                //Undo Move Logic
                theGame.undoMove();
                for (int i = 0; i < theGame.getLevelHeight(); i++) {
                    for (int j = 0; j < theGame.getLevelWidth(); j++) {
                        updateImages(i, j);
                    }
                }
                TextView moveCount = findViewById(R.id.moveCount);
                moveCount.setText(String.valueOf(theGame.getMoveCount()));
                this.addEyeballView();
                this.highlightGoals();
                return (true);
            case R.id.pauseButton:
                //Pause Level Logic
                ConstraintLayout mazeItems = findViewById(R.id.mazeBoard);
                TextView timerText = findViewById(R.id.timeText);
                if (mazeItems.getVisibility() == View.VISIBLE) {
                    mazeItems.setVisibility(View.INVISIBLE);
                    levelSelect.findItem(R.id.pauseButton).setIcon(R.drawable.baseline_play_circle_filled_24);
                    timeWhenStopped = timeText.getBase() - SystemClock.elapsedRealtime();
                    timeText.stop();
                } else {
                    mazeItems.setVisibility(View.VISIBLE);
                    levelSelect.findItem(R.id.pauseButton).setIcon(R.drawable.baseline_pause_circle_outline_24);
                    timeText.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    timeText.start();
                }
                return (true);
            case R.id.tutVideo:
                //Play Tutorial Video Logic
                ConstraintLayout mazeItem = findViewById(R.id.mazeBoard);
                mazeItem.setVisibility(View.GONE);
                ConstraintLayout vidPlayer = findViewById(R.id.videoView);
                vidPlayer.setVisibility(View.VISIBLE);
                VideoView vidView = findViewById(R.id.vidView);
                vidView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tutorial);
                vidView.start();
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(vidView);
                vidView.setMediaController(mediaController);
                vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    public void onCompletion(MediaPlayer mp)
                    {
                        vidPlayer.setVisibility(View.GONE);
                        mazeItem.setVisibility(View.VISIBLE);
                    }
                });
        }
        return (super.onOptionsItemSelected(item));
    }

    //Updates the images in the Game Based on the Level Information
    public void updateImages(int yPos, int xPos) {
        Color theColour = theGame.getColorAt(yPos, xPos);
        Shape theShape = theGame.getShapeAt(yPos, xPos);
        ImageView square = levelImages[yPos][xPos];
        Drawable background = getDrawable(R.drawable.white_background);
        square.setBackground(background);
        Bitmap image;
        if (theColour == Color.BLUE) {
            switch (theShape) {
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._bs);
                    square.setImageBitmap(image);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._bf);
                    square.setImageBitmap(image);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._bc);
                    square.setImageBitmap(image);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._bd);
                    square.setImageBitmap(image);
                    break;
            }
        } else if (theColour == Color.GREEN) {
            switch (theShape) {
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._gs);
                    square.setImageBitmap(image);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._gf);
                    square.setImageBitmap(image);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._gc);
                    square.setImageBitmap(image);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._gd);
                    square.setImageBitmap(image);
                    break;
            }
        } else if (theColour == Color.RED) {
            switch (theShape) {
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._rs);
                    square.setImageBitmap(image);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._rf);
                    square.setImageBitmap(image);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._rc);
                    square.setImageBitmap(image);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._rd);
                    square.setImageBitmap(image);
                    break;
            }
        } else if (theColour == Color.YELLOW) {
            switch (theShape) {
                case STAR:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._ys);
                    square.setImageBitmap(image);
                    break;
                case FLOWER:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._yf);
                    square.setImageBitmap(image);
                    break;
                case CROSS:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._yc);
                    square.setImageBitmap(image);
                    break;
                case DIAMOND:
                    image = BitmapFactory.decodeResource(getResources(), R.drawable._yd);
                    square.setImageBitmap(image);
                    break;
            }
        } else {
            Log.e("Empty Square", "Nothing found in this square");
            square.setBackground(null);
        }
    }

    //Adds the Eyeball to a square
    private void addEyeballView() {
        int eyeballRow = theGame.getEyeballRow();
        int eyeballColumn = theGame.getEyeballColumn();
        Direction eyeballDirection = theGame.getEyeballDirection();
        ImageView square = levelImages[eyeballRow][eyeballColumn];
        Bitmap shapeImage = ((BitmapDrawable) square.getDrawable()).getBitmap();
        Bitmap eyeballImage;
        switch (eyeballDirection) {
            case UP:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eyesu);
                break;
            case LEFT:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eyesl);
                break;
            case RIGHT:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eyesr);
                break;
            case DOWN:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.eyesd);
                break;
            default:
                eyeballImage = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_question_mark_24);
        }
        Bitmap mergedImages = combineTwoImagesAsOne(shapeImage, eyeballImage);
        square.setImageBitmap(mergedImages);
    }

    //Removes the Eyeball from a square
    private void removeEyeballView() {
        int eyeballRow = theGame.getEyeballRow();
        int eyeballColumn = theGame.getEyeballColumn();
        this.updateImages(eyeballRow, eyeballColumn);
    }

    //Highlights the goals on a level
    private void highlightGoals() {
        List<Point> currentGoalList = theGame.getGoalList();
        List<Point> completedGoalList = theGame.getCompletedGoalList();
        for (Point item : currentGoalList) {
            ImageView goal = levelImages[item.y][item.x];
            Drawable highlight = getDrawable(R.drawable.border);
            goal.setBackground(highlight);
        }
        for (Point item : completedGoalList) {
            ImageView goal = levelImages[item.y][item.x];
            Drawable background = getDrawable(R.drawable.white_background);
            goal.setBackground(background);
        }
    }

    //Combines two images together
    private Bitmap combineTwoImagesAsOne(Bitmap oneImage, Bitmap theOtherImage) {
        Bitmap resultImage = Bitmap.createBitmap(theOtherImage.getWidth(),
                theOtherImage.getHeight(),
                theOtherImage.getConfig());
        Canvas canvas = new Canvas(resultImage);
        canvas.drawBitmap(oneImage, 0f, 0f, null);
        canvas.drawBitmap(theOtherImage, 0, 0, null);
        return resultImage;
    }
}