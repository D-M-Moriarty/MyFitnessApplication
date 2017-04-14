package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;

import java.util.concurrent.TimeUnit;

public class TabataMainActivity extends AppCompatActivity implements View.OnClickListener {


    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private int intPrepareVal;
    private int intWorkVal;
    private int intRestVal;
    private int intCyclesVal;
    private int currentCycleValue = 1;

    private String currentActivity;
    private int currentActivityCounter;

    private boolean counterActive = true;
    private int tickCount = 0;

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    // click sound
    MediaPlayer click;
    MediaPlayer airhorn;

    // GUI elements
    private ProgressBar progressBarCircle;
    private TextView timerTextView;
    private ImageView imageViewReset;
    private ImageView imageViewPause;
    private CountDownTimer countDownTimer;
    private TextView currentActivityTextView;
    private ImageView activityImageView;
    private TextView cyclesTextView;
    private ImageView imageViewStart;
    private ImageView goBackImageView;
    private ImageView skipForwardImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_main);

        // getting the values the user set on the previous page
        intPrepareVal = getIntent().getIntExtra("prepareValue", 0);
        intWorkVal = getIntent().getIntExtra("workValue", 0);
        intRestVal = getIntent().getIntExtra("restValue", 0);
        intCyclesVal = getIntent().getIntExtra("cyclesValue", 0);

        // GUI elements
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        timerTextView = (TextView) findViewById(R.id.textViewTime);
        imageViewReset = (ImageView) findViewById(R.id.imageViewReset);
        imageViewStart = (ImageView) findViewById(R.id.imageViewStart);
        imageViewPause = (ImageView) findViewById(R.id.imageViewPause);
        currentActivityTextView = (TextView)findViewById(R.id.currentActivityTextView);
        activityImageView = (ImageView) findViewById(R.id.activityImageView);
        cyclesTextView = (TextView) findViewById(R.id.cyclesTextView);
        goBackImageView = (ImageView) findViewById(R.id.goBackImageView);
        skipForwardImageView = (ImageView) findViewById(R.id.skipForwardImageView);

        // finding click sound
        click = MediaPlayer.create(getApplicationContext(), R.raw.click);
        // airhorn
        airhorn = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);


        // Listeners
        imageViewPause.setOnClickListener(this);
        imageViewStart.setOnClickListener(this);
        goBackImageView.setOnClickListener(this);
        skipForwardImageView.setOnClickListener(this);

        currentActivity = "prepare";
        currentActivityCounter = intPrepareVal;

        startTimer();

        // TODO add sounds from device to play in the background during workout !!!!
        // TODO figure out how to change the progress bar


    }

    public void skipForward() {

        countDownTimer.cancel();

        // setting the activity state
        if (currentActivity.equals("prepare")) {

            currentActivity = "work";
            setTimerValues(intWorkVal);
            activityImageView.setImageResource(R.drawable.ic_fitness_center_black_24dp);

        } else if (currentActivity.equals("work")) {

            currentActivity = "rest";
            setTimerValues(intRestVal);
            activityImageView.setImageResource(R.drawable.ic_accessibility_black_24dp);

        } else if (currentActivity.equals("rest")) {

            if (currentCycleValue <= intCyclesVal) {
                currentActivity = "prepare";
                setTimerValues(intPrepareVal);
                activityImageView.setImageResource(R.drawable.ic_directions_run_black_24dp);
            }

            // keeping track of the current cycle
            if (currentCycleValue <= intCyclesVal)
                currentCycleValue++;


        }

        // keep starting the timer as long as there is cycles left
        if (currentCycleValue <= intCyclesVal) {

            cyclesTextView.setText(currentCycleValue + "/" + intCyclesVal);
            startCountDownTimer();

        } else {

            imageViewPause.setVisibility(View.INVISIBLE);
            imageViewStart.setVisibility(View.VISIBLE);

        }

        currentActivityTextView.setText(currentActivity);

    }

    public void goBack() {

        countDownTimer.cancel();

        // setting the activity state
        if (currentActivity.equals("rest")) {

            currentActivity = "work";
            setTimerValues(intWorkVal);
            activityImageView.setImageResource(R.drawable.ic_fitness_center_black_24dp);

        } else if (currentActivity.equals("work")) {

            currentActivity = "prepare";
            setTimerValues(intPrepareVal);
            activityImageView.setImageResource(R.drawable.ic_directions_run_black_24dp);

        } else if (currentActivity.equals("prepare")) {

            // keeping track of the current cycle
            if (currentCycleValue > 1) {

                currentCycleValue--;

                currentActivity = "rest";
                setTimerValues(intRestVal);
                activityImageView.setImageResource(R.drawable.ic_accessibility_black_24dp);

            }


        }

        // keep starting the timer as long as there is cycles left
        if (currentCycleValue > 0) {

            cyclesTextView.setText(currentCycleValue + "/" + intCyclesVal);
            startCountDownTimer();

        } else {

            startCountDownTimer();
            imageViewPause.setVisibility(View.VISIBLE);
            imageViewStart.setVisibility(View.INVISIBLE);

        }

        currentActivityTextView.setText(currentActivity);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewStart:
                startTimer();
                break;
            case R.id.imageViewPause:
                pauseTimer();
                break;
            case R.id.skipForwardImageView:
                skipForward();
                break;
            case R.id.goBackImageView:
                goBack();
                break;
        }
    }

    private void pauseTimer() {

        counterActive = false;

        // changing stop icon to start icon
        imageViewPause.setVisibility(View.INVISIBLE);
        imageViewStart.setVisibility(View.VISIBLE);
        // changing the timer status to stopped
        timerStatus = TimerStatus.STOPPED;
        //System.out.println("Cancelling the timer");
        countDownTimer.cancel();

    }

    private void startTimer() {

        if (timerStatus == TimerStatus.STOPPED) {

            counterActive = true;
            //System.out.println(counterActive + " Counter active");

            // call to initialize the timer values
            setTimerValues(currentActivityCounter);
            // call to initialize the progress bar values
            setProgressBarValues();

            imageViewPause.setVisibility(View.VISIBLE);
            imageViewStart.setVisibility(View.INVISIBLE);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        }

    }


    /**
     * method to initialize the values for count down timer
     */
    private void setTimerValues(int timeVal) {
        int time = timeVal;
        currentActivityCounter = time;

        timeCountInMilliSeconds = time * 10 * 100;
    }

    //method to start count down timer
    private void startCountDownTimer() {

        if (counterActive) {

            countDownTimer = new CountDownTimer(timeCountInMilliSeconds + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    // checking every second
                    if ((int) (millisUntilFinished / 10) % 1 == 0) {

                        tickCount++;

                        // if one tick has passed
                        if (tickCount > 1) {

                            // decrement the value of the work variable
                            if ((int) (millisUntilFinished / 10) % 1 == 0)
                                currentActivityCounter--;

                        }


                    }


                    timerTextView.setText(hmsTimeFormatter(millisUntilFinished));

                    char lastDigit = timerTextView.getText().charAt(timerTextView.length() - 1);
                    char secondLastDigit = timerTextView.getText().charAt(timerTextView.length() - 2);

                    // playing the ticking sound from 3 seconds to 0
                    if (lastDigit < '4' && lastDigit > '0' && secondLastDigit == '0') {

                        // play click
                        click.start();

                    }

                    progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

                }

                @Override
                public void onFinish() {

                    timerTextView.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    // call to initialize the progress bar values
                    setProgressBarValues();
                    // hiding the reset icon
                    imageViewReset.setVisibility(View.GONE);

                    // changing the timer status to stopped
                    timerStatus = TimerStatus.STOPPED;

                    timerTextView.setText("00:00:00");

                    // play airhorn
                    airhorn.start();

                    // progress forward
                    skipForward();

                }

            }.start();

        }


    }


    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;

    }

}
