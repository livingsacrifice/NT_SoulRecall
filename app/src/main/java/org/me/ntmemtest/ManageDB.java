 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ntmemtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.*;
import java.util.*;

/**
 *
 * @author srichard
 */
public class ManageDB extends Activity {
    private Button clearDbButton;
    private Button uploadButton;
    private Button clearBibleButton;
    private Button VersesToCSVButton;
    private Button ClearScoresButton;
    String version;
    public DataBaseHelper myDbHelper;
    ProgressThread progThread;
    ProgressDialog progDialog;
    boolean alertCancel;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ToDo add your GUI initialization code here

      setContentView(R.layout.managedata);

      clearDbButton = (Button) findViewById(R.id.cleardbutton);
      clearDbButton.setOnClickListener(new OnClickListener() {
         public void onClick(final View v) {

            new AlertDialog.Builder(ManageDB.this).setMessage("Are you sure (this will delete all data)?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                           Log.i("Database", "deleting database");
                            myDbHelper.deleteAllDataYesIAmSure();
                            myDbHelper.resetDbConnection();
                           Toast.makeText(ManageDB.this, "Data deleted", Toast.LENGTH_SHORT).show();
                           ManageDB.this.startActivity(new Intent(ManageDB.this, NT_MemTest.class));
                        }
                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                     }).show();
         }
      });
      
      Spinner spinner5 = (Spinner) findViewById(R.id.sVersionTwo);
      ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
              this, R.array.version_list, android.R.layout.simple_spinner_item);
      adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner5.setAdapter(adapter5);
      spinner5.setOnItemSelectedListener(new VersionOnItemSelectedListener());
      spinner5.setSelection(1);
      
      uploadButton = (Button) findViewById(R.id.uploadbutton);
      uploadButton.setOnClickListener(new OnClickListener() {
         public void onClick(final View v) {
        	 new AlertDialog.Builder(ManageDB.this).setMessage("This process could take several minutes. Continue?")
             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	alertCancel = false;
                	showDialog(0);
                }
             }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
             }).show();
        	 
         }
      });
      
      clearBibleButton = (Button) findViewById(R.id.clearbiblebutton);
      clearBibleButton.setOnClickListener(new OnClickListener() {
         public void onClick(final View v) {

            new AlertDialog.Builder(ManageDB.this).setMessage("Are you sure (this will clear all bibles)?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                           Log.i("Database", "deleting biles from database");
                            myDbHelper.deleteAllBiblesYesIAmSure();
                            myDbHelper.resetDbConnection();
                           Toast.makeText(ManageDB.this, "Bibles deleted", Toast.LENGTH_SHORT).show();
                           ManageDB.this.startActivity(new Intent(ManageDB.this, NT_MemTest.class));
                        }
                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                     }).show();
         }
      });
      
      ClearScoresButton = (Button) findViewById(R.id.clearscoresbutton);
      ClearScoresButton.setOnClickListener(new OnClickListener() {
         public void onClick(final View v) {

            new AlertDialog.Builder(ManageDB.this).setMessage("Are you sure (this will reset scores to zero)?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        	myDbHelper.clearScores();
                        	Log.i("Database", "clearing all plus and minus values");
                        	Toast.makeText(ManageDB.this,"Scores set to zero.", Toast.LENGTH_SHORT).show();
                        	ManageDB.this.startActivity(new Intent(ManageDB.this, NT_MemTest.class));
                        }
                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                     }).show();
         }
      });
      
      myDbHelper = new DataBaseHelper(this);
      try {
      	myDbHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}

    }
    private boolean isExternalStorageAvail() {
      return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
       
   }
    
    // Method to create a progress bar dialog of either spinner or horizontal type
    @Override
    protected Dialog onCreateDialog(int id) {

            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progDialog.setMax(12);
            progDialog.setMessage("Inserting verses into database:");
            progThread = new ProgressThread(handler);
            progThread.start();
            return progDialog;

    }
    
 // Handler on the main (UI) thread that will receive messages from the 
    // second thread and update the progress.
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // Get the current value of the variable total from the message data
            // and update the progress bar.
            int total = msg.getData().getInt("total");
            progDialog.setProgress(total);
            if (total >= 12){
            	alertCancel = true;
                removeDialog(0);
                progThread.setState(ProgressThread.DONE);
            }
        }
    };

   public class VersionOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

       public void onItemSelected(AdapterView<?> parent,
           View view, int pos, long id) {
           version = parent.getItemAtPosition(pos).toString();
       }

       public void onNothingSelected(AdapterView parent) {
         // Do nothing.
       }
   }
   
   private class ProgressThread extends Thread {	
       
       // Class constants defining state of the thread
       final static int DONE = 0;
       final static int RUNNING = 1;
       
       Handler mHandler;
       int mState;
       int total;
       int countAdd = 0;

       // Constructor with an argument that specifies Handler on main thread
       // to which messages will be sent by this thread.
       
       ProgressThread(Handler h) {
           mHandler = h;
       }
       
       // Override the run() method that will be invoked automatically when 
       // the Thread starts.  Do the work required to update the progress bar on this
       // thread but send a message to the Handler on the main UI thread to actually
       // change the visual representation of the progress. In this example we count
       // the index total down to zero, so the horizontal progress bar will start full and
       // count down.
       
       @Override
       public void run() {
           mState = RUNNING;
           total = 0;
           String[] letters = {"a", "b", "c", "d", "e", "f"};
           while (mState == RUNNING) {
               try {
                   int versionCount = myDbHelper.checkBibleVersion(version);
                   if (versionCount > 30000) {
                       //version already stored
                       total = 12;
                       Message msg = mHandler.obtainMessage();
                       Bundle b = new Bundle();
                       b.putInt("total", total);
                       msg.setData(b);
                       mHandler.sendMessage(msg);
                   } else {
                       if (versionCount > 0) {
                           //version only partially stored. then continue
                           myDbHelper.resetBibleVersion(version);
                       }
                       AssetManager assetManager = getResources().getAssets();
                       InputStream inputStream = null;
                       try {
                           // Control speed of update (but precision of delay not guaranteed)
                           Thread.sleep(25);
                           for (int j = 0; j < letters.length; j++) {
                               inputStream = assetManager.open(version.toLowerCase() + "_verses_" + letters[j] + ".csv");
                               if (inputStream != null) {
                                   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                   String line = null;
                                   Vector<String> insertList = new Vector<String>();
                                   while ((line = reader.readLine()) != null) {
                                       //get verse from comma separated line
                                       Vector<String> store = new Vector<String>();
                                       StringBuffer curVal = new StringBuffer();
                                       boolean inquotes = false;
                                       for (int i = 0; i < line.length(); i++) {
                                           char ch = line.charAt(i);
                                           if (inquotes) {
                                               if (ch == '\"') {
                                                   inquotes = false;
                                               } else {
                                                   curVal.append(ch);
                                                   if (ch == '\'') {
                                                       curVal.append(ch);
                                                   }
                                               }
                                           } else {
                                               if (ch == '\"') {
                                                   inquotes = true;
                                                   if (curVal.length() > 0) {
                                                       //if this is the second quote in a value, add a quote
                                                       //this is for the double quote in the middle of a value
                                                       curVal.append('\"');
                                                   }
                                               } else if (ch == ',') {
                                                   store.add(curVal.toString());
                                                   curVal = new StringBuffer();
                                               } else {
                                                   curVal.append(ch);
                                                   if (ch == '\'') {
                                                       curVal.append(ch);
                                                   }
                                               }
                                           }
                                       }
                                       store.add(curVal.toString());
                                       //upload verse
                                       String bookx = store.get(0).toString();
                                       int chapterx = Integer.parseInt(store.get(1).toString());
                                       int versex = Integer.parseInt(store.get(2).toString());
                                       String wordsx = store.get(3).toString();
                                       insertList.add("insert into bible (book, chapter, verse, version, words) "
                                               + "values ('" + bookx + "', " + chapterx + ", " + versex + ",'" + version + "','" + wordsx + "')");
                                   }
                                   total++;
                                   Message msg = mHandler.obtainMessage();
                                   Bundle b = new Bundle();
                                   b.putInt("total", total);
                                   msg.setData(b);
                                   mHandler.sendMessage(msg);

                                   myDbHelper.batchInsert(insertList);

                                   total++;
                                   msg = mHandler.obtainMessage();
                                   b = new Bundle();
                                   b.putInt("total", total);
                                   b.putInt("countAdd", countAdd);
                                   msg.setData(b);
                                   mHandler.sendMessage(msg);
                               }

                           }


                       }
                       catch (IOException e) {
                           e.printStackTrace();
                       }
                   }

                   if (version.equals("KJVS")) {
                       if (!myDbHelper.checkStrong()) {
                           AssetManager assetManager = getResources().getAssets();
                           InputStream inputStream = null;
                           try {
                               // check if KJVS. save strong's dictionary if yes

                               inputStream = assetManager.open("strongs.csv");
                               if (inputStream != null) {
                                   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                   String line = null;
                                   Vector<String> insertList = new Vector<String>();
                                   Vector<String> store = new Vector<String>();
                                   StringBuffer curVal = new StringBuffer();
                                   boolean inquotes = false;
                                   while ((line = reader.readLine()) != null) {
                                       //get verse from comma separated line
                                       if (!inquotes){
                                           store = new Vector<String>();
                                           curVal = new StringBuffer();
                                       }
                                       for (int i = 0; i < line.length(); i++) {
                                           char ch = line.charAt(i);
                                           if (inquotes) {
                                               if (ch == '\"') {
                                                   inquotes = false;
                                               } else {
                                                   curVal.append(ch);
                                                   if (ch == '\'') {
                                                       curVal.append(ch);
                                                   }
                                               }
                                           } else {
                                               if (ch == '\"') {
                                                   inquotes = true;
                                                   if (curVal.length() > 0) {
                                                       //if this is the second quote in a value, add a quote
                                                       //this is for the double quote in the middle of a value
                                                       curVal.append('\"');
                                                   }
                                               } else if (ch == ',') {
                                                   store.add(curVal.toString());
                                                   curVal = new StringBuffer();
                                               } else {
                                                   curVal.append(ch);
                                                   if (ch == '\'') {
                                                       curVal.append(ch);
                                                   }
                                               }
                                           }
                                       }
                                       if (!inquotes) {
                                           store.add(curVal.toString());
                                           //upload verse
                                           String numberx = store.get(0).toString();
                                           String definitionx = store.get(1).toString();
                                           insertList.add("insert into strong (str_number, str_definition) "
                                                   + "values ('" + numberx + "', '" + definitionx + "')");
                                       }
                                       else {
                                           curVal.append("  ");
                                       }
                                   }
                                   myDbHelper.batchInsert(insertList);
                               }
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   }
               } catch (InterruptedException e) {
                   //Log.e("ERROR", "Thread was Interrupted");
               }
           }
       }
       
       // Set current state of thread (use state=ProgressThread.DONE to stop thread)
       public void setState(int state) {
           mState = state;
       }
   }
}