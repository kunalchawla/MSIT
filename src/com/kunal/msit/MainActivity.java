package com.kunal.msit;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.kunal.msit.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;

public class MainActivity extends Activity {
	
	/*******************
	 * EDITABLE VALUES *
	 *******************/
	/* Stimuli Array Declarations */
	String[] PracticeOne = new String[]{"100", "003", "020", "003", "020"};
	String[] PracticeTwo = new String[]{"131", "331", "212", "112", "212"};
	String[] PracticeThree = new String[]{"020", "003", "100"};
	String[] PracticeFour = new String[]{"322", "121", "211"};
	String[] TestOne = new String[]{"100", "003", "020", "003", "100", "003", "020", "100", "020", "003", "100", "020", "003", "003", "100", "100", "020", "003", "100", "020", "020", "003", "100", "020"};
	int plusDelay = 250;
	int stimuliPause = 2500;
	int welcomeScreenPause = 3000;
	int instructionScreenPause = 3000;
	int greatJobScreenPause = 3000;
	
	
	/* View Declarations */
	Button button1;
	Button button2;
	Button button3;
	Button nextButton;
	ImageView welcomeImage;
	ImageView instructionsImage;
	ImageView practiceImage;
	ImageView greatJobImage;
	ImageView playImage;
	ImageView instructionsTextImage;
	ImageView readyTextImage;
	TextView stimuliText;
	
	/* Array Indexes Declarations */
	int practiceOneArrayIndex = 0;
	int practiceTwoArrayIndex = 0;
	int practiceThreeArrayIndex = 0;
	int practiceFourArrayIndex = 0;
	int testOneArrayIndex = 0;
	int testTwoArrayIndex = 0;
	int trial = 1;
	int numPracticeTrials = PracticeOne.length + PracticeTwo.length + PracticeThree.length + PracticeFour.length;
	
	/* Time Declarations */
	long startTime = 0;
   	long currentTime = 0;
   	long elapsedTime = 0;
	
	/* Variable Declarations */
	String buttonPressed;
	String reactionTime;
	String taskName = "MSIT";
	String userId = "";
	String password1 = "123";
	String password2 = "321";
	String columnHeaderTaskName = "msit_";
   	
   	/* File Declarations */
   	String storageState = Environment.getExternalStorageState();
   	File file;
      
    /* Unnecessary Methods? */
//  @Override
//  protected void onStart(){ 
//      super.onStart();
//  }


//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//      // Inflate the menu; this adds items to the action bar if it is present.
//      getMenuInflater().inflate(R.menu.main, menu);
//      return true;
//  }
  
    /* Disable back button */
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initializeViews();
        userIdAlert();
    }
    
    public void initializeViews() {
    	welcomeImage = (ImageView) findViewById(R.id.welcomeImage);
    	instructionsImage = (ImageView) findViewById(R.id.instructionsImage);
    	instructionsTextImage = (ImageView) findViewById(R.id.instructionsTextImage);
    	readyTextImage = (ImageView) findViewById(R.id.readyTextImage);
    	practiceImage = (ImageView) findViewById(R.id.practiceImage);
    	greatJobImage = (ImageView) findViewById(R.id.greatJobImage);
    	playImage = (ImageView) findViewById(R.id.playImage);
    	stimuliText = (TextView) findViewById(R.id.textViewStimuli);
    	button1 = (Button) findViewById(R.id.button1);
    	button2 = (Button) findViewById(R.id.button2);
    	button3 = (Button) findViewById(R.id.button3);
    	nextButton = (Button) findViewById(R.id.nextButton);
    }
    
    public void userIdAlert() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Enter User ID:");
    	final EditText input = new EditText(this);
    	input.setInputType(InputType.TYPE_CLASS_NUMBER);
    	InputFilter[] FilterArray = new InputFilter[1];
    	FilterArray[0] = new InputFilter.LengthFilter(4);
    	input.setFilters(FilterArray);
    	alert.setView(input);
    	
    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    	  String value = input.getText().toString();
    	  if (value.length() == 4) {
	    	  userId = value;
	    	  createCSV();
	    	  showWelcome();
    	  } else {
    		  userIdAlert();
    	  }
    	  }
    	});
    	alert.show();
    }
    
    public void createCSV(){
    	try {
        	Date date = Calendar.getInstance().getTime();
        	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        	SimpleDateFormat sdfTime = new SimpleDateFormat("HH-mm-ss");
        	String fileNameDate = sdfDate.format(date) + "_" + sdfTime.format(date);
        	
        	String fileName = taskName + "_" + userId  + "_" + fileNameDate + ".csv";
            String csvHeader = generateTrialHeaders();
            String newLine = "\r\n";
            String firstThreeCells = userId + ", " + sdfDate.format(date) + ", " + sdfTime.format(date) + ", ";
            
            /* Note: this creates an internal file that is not accessible, so we've disabled this feature. */
//            FileOutputStream fos = openFileOutput(fileName,
//                    Context.MODE_APPEND | Context.MODE_APPEND);
//            fos.write(csvHeader.getBytes("UTF-16"));
//            fos.close();
            
            
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            	file = new File(getExternalFilesDir(null),
            			fileName);
            	FileOutputStream fos2 = new FileOutputStream(file, true);
                fos2.write(csvHeader.getBytes("UTF-16"));
                fos2.write(newLine.getBytes("UTF-16"));
                fos2.write(firstThreeCells.getBytes("UTF-16"));
                fos2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String generateTrialHeaders() {
    	String header = "ID, Date, Time, ";
    	int headerTrial = 1;
    	
    	for (int i=0; i<PracticeOne.length; i++) {
    		header = header + columnHeaderTaskName + headerTrial + "_[" + PracticeOne[i] + "] ,";				
    		header = header + "rt_" + columnHeaderTaskName + headerTrial + "_[" + PracticeOne[i] + "] ,";
    		headerTrial++;
    	}
    	
    	for (int i=0; i<PracticeTwo.length; i++) {
    		header = header + columnHeaderTaskName + headerTrial + "_[" + PracticeTwo[i] + "] ,";				
    		header = header + "rt_" + columnHeaderTaskName + headerTrial + "_[" + PracticeTwo[i] + "] ,";
    		headerTrial++;
    	}
    	
    	for (int i=0; i<PracticeThree.length; i++) {
    		header = header + columnHeaderTaskName + headerTrial + "_[" + PracticeThree[i] + "] ,";				
    		header = header + "rt_" + columnHeaderTaskName + headerTrial + "_[" + PracticeThree[i] + "] ,";
    		headerTrial++;
    	}
    	
    	for (int i=0; i<PracticeFour.length; i++) {
    		header = header + columnHeaderTaskName + headerTrial + "_[" + PracticeFour[i] + "] ,";				
    		header = header + "rt_" + columnHeaderTaskName + headerTrial + "_[" + PracticeFour[i] + "] ,";
    		headerTrial++;
    	}
    	
    	for (int i=0; i<TestOne.length; i++) {
    		header = header + columnHeaderTaskName + headerTrial + "_[" + TestOne[i] + "] ,";				
    		header = header + "rt_" + columnHeaderTaskName + headerTrial + "_[" + TestOne[i] + "] ,";
    		headerTrial++;
    	}
    	
    	//add more tests here, please.
    	
    	return header;
    }
    
    public void showWelcome() {
    	welcomeImage.setVisibility(View.VISIBLE);
    	welcomeImage.postDelayed(new Runnable () {
    		@Override
    		public void run() {
    			showPracticeInstructions();
    		}
    	}, welcomeScreenPause);
    }
    
    public void showPracticeInstructions() {
    	welcomeImage.setVisibility(View.INVISIBLE);
		instructionsImage.setVisibility(View.VISIBLE);
		instructionsTextImage.setVisibility(View.VISIBLE);
		welcomeImage.postDelayed(new Runnable () {
    		@Override
    		public void run() {
    			nextButton.setVisibility(View.VISIBLE);
    			nextButton.setOnClickListener(new OnClickListener() {
    	    		@Override
    	    		public void onClick(View arg0) {
    	    			startPracticeAlert();
    	    		}
    	    	});
    		}
    	}, instructionScreenPause);
    }
    
    void startPracticeAlert() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Enter Password to Continue:");
    	final EditText input = new EditText(this);
    	input.setInputType(InputType.TYPE_CLASS_NUMBER);
    	InputFilter[] FilterArray = new InputFilter[1];
    	FilterArray[0] = new InputFilter.LengthFilter(3);
    	input.setFilters(FilterArray);
    	alert.setView(input);
    	
    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    	  String value = input.getText().toString();
	    	  if (value.equals(password1)) {
	    		  instructionsImage.setVisibility(View.INVISIBLE);
	    		  instructionsTextImage.setVisibility(View.INVISIBLE);
	    		  showPracticeScreen();
	    	  } else {
	    		  startPracticeAlert();
	    	  }
    	  }
    	});
    	alert.show();
    }
    
    void showPracticeScreen() {
    	practiceImage.setVisibility(View.VISIBLE);
    	readyTextImage.setVisibility(View.VISIBLE);
    	nextButton.setText("Yes");
    	nextButton.setVisibility(View.VISIBLE);
    	nextButton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			startPractice();
    			showPracticeStimuli(nextButton);
    		}
    	});
    }
    
    void startPractice() {
    	practiceImage.setVisibility(View.INVISIBLE);
    	instructionsTextImage.setVisibility(View.INVISIBLE);
    	readyTextImage.setVisibility(View.INVISIBLE);
    	nextButton.setVisibility(View.INVISIBLE);
    	showStimuliAndButtons();
    	addPracticeListenerOnButtons();	
    }
    
    public void addPracticeListenerOnButtons() {
    	button1.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showPracticeStimuli(button1);
    		}
    	});
    	
    	button2.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showPracticeStimuli(button2);
    		}
    	});
    	
    	button3.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showPracticeStimuli(button3);
    		}
    	});
	}
    
    void showPracticeStimuli(final Button button){
    	//Calculate time duration for each trial
    	if(trial == 1){
    		startTime = System.nanoTime();
    	}else{
    		currentTime = System.nanoTime();
    		elapsedTime = currentTime - startTime;
    		startTime = currentTime;
    		reactionTime = String.valueOf(TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)) + ", ";
    		buttonPressed = button.getText().toString() + ", ";
    		    		
    		try {
    			FileOutputStream fos3 = new FileOutputStream(file, true); 
    			fos3.write(buttonPressed.getBytes("UTF-16"));
    			fos3.write(reactionTime.getBytes("UTF-16"));
    			fos3.close();
    		} catch (Exception e) {
                e.printStackTrace();
            }
    	}
//    	Log.i(String.valueOf(trial), String.valueOf(TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)));
		if(practiceOneArrayIndex<PracticeOne.length){
			disableButtons();
			stimuliText.setText("+");
			button.postDelayed(new Runnable(){
				@Override
				public void run(){
					enableButtons();
					stimuliText.setText(PracticeOne[practiceOneArrayIndex]);    	    		
					practiceOneArrayIndex++;
    	    		trial++;
				}
			}, plusDelay);
		} else if(practiceTwoArrayIndex<PracticeTwo.length){
			disableButtons();
			stimuliText.setText("+");
			button.postDelayed(new Runnable(){
				@Override
				public void run(){
					enableButtons();
					stimuliText.setText(PracticeTwo[practiceTwoArrayIndex]);    	    		
					practiceTwoArrayIndex++;
    	    		trial++;
				}
			}, plusDelay);
		} else if(practiceThreeArrayIndex<PracticeThree.length){
			disableButtons();
			stimuliText.setText("+");
			button.postDelayed(new Runnable(){
				@Override
				public void run(){
					enableButtons();
					stimuliText.setText(PracticeThree[practiceThreeArrayIndex]);    	    		
					practiceThreeArrayIndex++;
    	    		trial++;
				}
			}, plusDelay);
		} else if(practiceFourArrayIndex<PracticeFour.length){
			disableButtons();
			stimuliText.setText("+");
			button.postDelayed(new Runnable(){
				@Override
				public void run(){
					enableButtons();
					stimuliText.setText(PracticeFour[practiceFourArrayIndex]);    	    		
					practiceFourArrayIndex++;
    	    		trial++;
				}
			}, plusDelay);
		} else {
			EndPractice();
		}
    }
    
    void EndPractice() {
    	hideStimuliAndButtons();
    	greatJobImage.setVisibility(View.VISIBLE);
    	greatJobImage.postDelayed(new Runnable() {
    		@Override
    		public void run(){
    			showTrialInstructions();
    		}
    	}, greatJobScreenPause);
    }
    
    void showTrialInstructions() {
    	
    	greatJobImage.setVisibility(View.INVISIBLE);
		instructionsImage.setVisibility(View.VISIBLE);
		instructionsTextImage.setVisibility(View.VISIBLE);
		welcomeImage.postDelayed(new Runnable () {
    		@Override
    		public void run() {
    			nextButton.setVisibility(View.VISIBLE);
    			nextButton.setOnClickListener(new OnClickListener() {
    	    		@Override
    	    		public void onClick(View arg0) {
    	    			startTrialAlert();
    	    		}
    	    	});
    		}
    	}, instructionScreenPause);   
    }
    
    void startTrialAlert() {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Enter Password to Continue:");
    	final EditText input = new EditText(this);
    	input.setInputType(InputType.TYPE_CLASS_NUMBER);
    	InputFilter[] FilterArray = new InputFilter[1];
    	FilterArray[0] = new InputFilter.LengthFilter(3);
    	input.setFilters(FilterArray);
    	alert.setView(input);
    	
    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    	  String value = input.getText().toString();
    	  if (value.equals(password2)) {
    		  instructionsImage.setVisibility(View.INVISIBLE);
    		  instructionsTextImage.setVisibility(View.INVISIBLE);
    		  showPlayScreen();
    	  } else {
    		  startTrialAlert();
    	  }
    	  }
    	});
    	alert.show();
    }
    
    void showPlayScreen() {
    	playImage.setVisibility(View.VISIBLE);
    	readyTextImage.setVisibility(View.VISIBLE);
    	nextButton.setVisibility(View.VISIBLE);
    	nextButton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			startTrial();
    			showTrialStimuli(nextButton);
    		}
    	});
    }
    
    void startTrial() {
    	playImage.setVisibility(View.INVISIBLE);
    	readyTextImage.setVisibility(View.INVISIBLE);
    	nextButton.setVisibility(View.INVISIBLE);
    	showStimuliAndButtons();
    	addTrialListenerOnButtons();	
    }
    
    public void addTrialListenerOnButtons() {
    	button1.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showTrialStimuli(button1);
    		}
    	});
    	
    	button2.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showTrialStimuli(button2);
    		}
    	});
    	
    	button3.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			showTrialStimuli(button3);
    		}
    	});
	}
    
    void showTrialStimuli(final Button button){
    	//Calculate time duration for each trial
    	if(trial == numPracticeTrials + 1){
    		startTime = System.nanoTime();
    	}else{
    		currentTime = System.nanoTime();
    		elapsedTime = TimeUnit.MILLISECONDS.convert(currentTime - startTime, TimeUnit.NANOSECONDS);
    		startTime = currentTime;
    		reactionTime = String.valueOf(elapsedTime) + ", ";
    		if (elapsedTime > 2700) {
    			buttonPressed = " , ";
    		} else {
    			buttonPressed = button.getText().toString() + ", ";
    		}
    		
    		try {
    			FileOutputStream fos3 = new FileOutputStream(file, true); 
    			fos3.write(buttonPressed.getBytes("UTF-16"));
    			fos3.write(reactionTime.getBytes("UTF-16"));
    			fos3.close();
    		} catch (Exception e) {
                e.printStackTrace();
            }
    	}
//    	Log.i(String.valueOf(trial), String.valueOf(elapsedTime));
    	if(testOneArrayIndex<TestOne.length){
			disableButtons();
			stimuliText.setText("+");
			button.postDelayed(new Runnable(){
				@Override
				public void run(){
					final int myTrial = trial;
					enableButtons();
					stimuliText.setText(TestOne[testOneArrayIndex]);
					Runnable r = new Runnable() {
						@Override
						public void run(){
							if (myTrial == trial - 1) {
								showTrialStimuli(button);
							}
						}
					};
					button.postDelayed(r, stimuliPause);
					testOneArrayIndex++;
    	    		trial++;
				}
			}, plusDelay);
		} else {
			trial++;
			EndTrial();
		}
    }
    
    void EndTrial() {
    	hideStimuliAndButtons();
    	greatJobImage.setVisibility(View.VISIBLE);
    	greatJobImage.postDelayed(new Runnable() {
    		@Override
    		public void run(){
    			exitApplication();
    		}
    	}, greatJobScreenPause);
    }
    
    void exitApplication() {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    }

    
    /* Helper Methods */
    
    void showStimuliAndButtons() {
    	stimuliText.setVisibility(View.VISIBLE);
    	button1.setVisibility(View.VISIBLE);
    	button2.setVisibility(View.VISIBLE);
    	button3.setVisibility(View.VISIBLE);
    }
    
    void hideStimuliAndButtons() {
    	stimuliText.setVisibility(View.INVISIBLE);
    	button1.setVisibility(View.INVISIBLE);
    	button2.setVisibility(View.INVISIBLE);
    	button3.setVisibility(View.INVISIBLE);
    }
    
    void disableButtons(){
    	button1.setClickable(false);
		button2.setClickable(false);
		button3.setClickable(false);
    }
    void enableButtons(){
    	button1.setClickable(true);
		button2.setClickable(true);
		button3.setClickable(true);
    }
  
}
