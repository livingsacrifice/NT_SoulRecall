
package org.me.ntmemtest;
import android.content.Context;
import android.app.*;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.View.*;
import android.util.TypedValue;
import android.os.*;
import java.util.*;
import java.lang.*;
import android.content.*;
import android.content.res.*;
import java.io.*;
import android.app.AlertDialog.Builder;
import android.view.animation.*;
import android.gesture.*;
import android.gesture.GestureOverlayView.*;
import android.graphics.Color;
import android.preference.*;
import android.app.ProgressDialog;

/**
 *
 * @author srichard
 */
public class NT_MemTest extends Activity implements OnGesturePerformedListener {
	public boolean storeBook = false;
    public int defTextColor = -99999;
    public int bgColor = 0;
    public int textColor = -99999;
    public int hintLength = 5;
    public boolean showEditText = true;
    public boolean reverseMode = true;
    public boolean screenOn = false;
    public boolean hintDisplayed;
    String prevBook = "James";
    int prevChapter = 1;
    int prevVerse = 1;
    String book = "James";
    int chapter = 1;
    int verse = 1;
    int chapterCount = 5;
    int verseCount = 27;
    String version = "KJV";
    String auto = "Increment";
    boolean preview = false;
    String verseText = "";
    String txtIn = "";
    String txtOut = "";
    String txtWord = "";
    String txtOrder = "";
    public int[] chap_length = {0, 27, 26, 18, 17, 20};
    public String RandText = "RandText";
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner5;
    Spinner spinner6;
    TextView strongtext;
    public int strongLength = 1;
    public int strongSelection=  1;
    EditText edittext;
    LinearLayout editlayout;
    LinearLayout ll00;
    LinearLayout ll01;
    LinearLayout ll02;
    LinearLayout ll03;
    LinearLayout ll04;
    LinearLayout llVerse;
    TextView tvBlank01;
    TextView returntext;
    TextView returnverse;
    TextView scoreoutput;
    TextView txtLeft;
    TextView txtRight;
    CheckBox checkbox;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter6;
    public DataBaseHelper myDbHelper;
    boolean confirmDelete;
    AlertDialog.Builder builder;
    boolean chapterScope;
    boolean chapterCancel;
    ViewFlipper vf;
    boolean flashStyle;
    private GestureLibrary gestureLib;
    public boolean addOT = false;
    public boolean addSig = true;
    public boolean darkMode = false;
    public boolean volControl = false;
    ProgressThread progThread;
    ProgressDialog progDialog;
    Button btnAddVerse;
    Button btnAddChapter;
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        hintLength = sharedPrefs.getInt("hint_length", 5);
        textColor = sharedPrefs.getInt("color1", -99999);
        bgColor = sharedPrefs.getInt("color2", 0);
        showEditText = sharedPrefs.getBoolean("show_edittext", false);
        reverseMode = sharedPrefs.getBoolean("reverseMode", true);
        addOT = sharedPrefs.getBoolean("add_OT", false);
        darkMode = sharedPrefs.getBoolean("darkMode", false);
        if (darkMode) {
            bgColor = Color.BLACK;
            textColor = Color.WHITE;
        }
        volControl = sharedPrefs.getBoolean("volControl", false);
        addSig = sharedPrefs.getBoolean("add_sig",true);
        screenOn = sharedPrefs.getBoolean("screen_on",false);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int widthD = display.getWidth(); 
        int heightD = display.getHeight();

        GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
        View inflate = getLayoutInflater().inflate(R.layout.main, null);
        gestureOverlayView.addView(inflate);
        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLib.load()) {
                finish();
        }
        setContentView(gestureOverlayView);
        
        spinner = (Spinner) findViewById(R.id.sBook);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.book_list, android.R.layout.simple_spinner_item);
        if (addOT){
            adapter = ArrayAdapter.createFromResource(this, R.array.book_list_all, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new BookOnItemSelectedListener());

        spinner2 = (Spinner) findViewById(R.id.sChapter);
        adapter2 = ArrayAdapter.createFromResource(
                this, R.array.James_list, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new ChapterOnItemSelectedListener());


        spinner3 = (Spinner) findViewById(R.id.sVerse);
        adapter3 = ArrayAdapter.createFromResource(
                this, R.array.James_1_list, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new VerseOnItemSelectedListener());
        
        Spinner spinner4 = (Spinner) findViewById(R.id.sAuto);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.auto_list, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new AutoOnItemSelectedListener());
        spinner4.setSelection(0);

        spinner5 = (Spinner) findViewById(R.id.sVersion);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
                this, R.array.version_list, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(new VersionOnItemSelectedListener());
        spinner5.setSelection(1);


        final Button btnVotD = (Button) findViewById(R.id.btnVotD);
        btnVotD.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                getVotD();
            }
        });

        final Button btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                NT_MemTest.this.openOptionsMenu();
                NT_MemTest.this.invalidateOptionsMenu();
            }
        });

        btnAddVerse = (Button) findViewById(R.id.btnAddVerse);
        btnAddVerse.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                addVerseExec();
            }
        });

        btnAddChapter = (Button) findViewById(R.id.btnAddChapter);
        btnAddChapter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	chapterCancel = true;
            	showDialog(0);
            }
        });

        flashStyle = false;
        vf = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        txtLeft = (TextView) findViewById(R.id.fakeLeft);
        txtRight = (TextView) findViewById(R.id.fakeRight);
        returntext = (TextView) findViewById(R.id.returnText);
        returnverse = (TextView) findViewById(R.id.returnVerse);
        int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+48)); //18 = 8pt font
        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        scoreoutput = (TextView) findViewById(R.id.scoreOutput);
        edittext = (EditText) findViewById(R.id.editText);

        spinner6 = (Spinner) findViewById(R.id.sStrongNumbers);
        adapter6 = ArrayAdapter.createFromResource(
                this, R.array.strong_list, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.setOnItemSelectedListener(new StrongOnItemSelectedListener());
        spinner6.setSelection(1);
        strongtext = (TextView) findViewById(R.id.tvStrongDefinition);

        editlayout = (LinearLayout) findViewById(R.id.editLayout);
        ll00 = (LinearLayout) findViewById(R.id.ll00);
        ll01 = (LinearLayout) findViewById(R.id.ll01);
        ll02 = (LinearLayout) findViewById(R.id.ll02);
        ll03 = (LinearLayout) findViewById(R.id.ll03);
        ll04 = (LinearLayout) findViewById(R.id.ll04);
        llVerse = (LinearLayout) findViewById(R.id.llVerse);
        tvBlank01 = (TextView) findViewById(R.id.tvBlank01);

        ViewGroup.LayoutParams params0 = ll00.getLayoutParams();
        params0.width = widthD;
        params0.height = heightD;

        checkbox = (CheckBox) findViewById(R.id.chPreview);
        checkbox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                preview = ((CheckBox) v).isChecked();
                updCurrState();
            }
        });

        updColors();
        toggleEditLayout();

        vf.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!version.equals("KJVS")) {
                    vf.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_in_up));
                    if (vf.getCurrentView() == returnverse) {
                        checkVerse();
                    } else if (hintDisplayed) {
                        checkVerse();
                    } else {
                        //reset versetext to name of verse in case verse(s) added
                        returnverse.setText(book + " " + chapter + ":" + verse);
                        int textSizeDIP = (int) Math.sqrt(105000 / (returnverse.getText().length() + 30)); //18 = 8pt font
                        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
                        vf.showNext();
                    }
                }
            }
        });
        txtLeft.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                getPrevVerse();
            }
        });
        txtRight.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                getNextVerse();
            }
        });

        myDbHelper = new DataBaseHelper(this);
        try {
        	myDbHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}



        myDbHelper.weightVerses("KJV");
        myDbHelper.weightVerses("NKJV");
        myDbHelper.weightVerses("NIV");
        myDbHelper.weightVerses("NASB");
        myDbHelper.weightVerses("HCSB");
        myDbHelper.weightVerses("RVR");
        myDbHelper.weightVerses("ESV");
        myDbHelper.weightVerses("AMP");

        myDbHelper.checkSave();
        myDbHelper.checkBible();
        myDbHelper.getSave();
        auto = myDbHelper.saveAuto;
        preview = myDbHelper.saveHint;
        version = myDbHelper.saveVersion;
        checkbox.setChecked(preview);
        if (auto.equals("Increment")){
            spinner4.setSelection(0);
        }else if (auto.equals("Random")){
            spinner4.setSelection(1);
        }else if (auto.equals("MyVerses")){
            spinner4.setSelection(2);
        }
        if (version.equals("KJV")){
            spinner5.setSelection(1);
        }else if (version.equals("NIV")){
            spinner5.setSelection(3);
        }else if (version.equals("NKJV")){
            spinner5.setSelection(4);
        }else if (version.equals("NASB")){
            spinner5.setSelection(2);
        }else if (version.equals("HCSB")){
            spinner5.setSelection(0);
        }else if (version.equals("RVR")){
            spinner5.setSelection(7);
        }else if (version.equals("ESV")){
            spinner5.setSelection(5);
        }else if (version.equals("AMP")){
            spinner5.setSelection(6);
        }else if (version.equals("KJVS")){
            spinner5.setSelection(8);
        }

        getSaveVerse();
        flashBack(true);

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Entry already exists!" + '\n' + "Would you like to remove?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertExec();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
                //do nothing
           }
        });
        AlertDialog alert = builder.create();

        if (screenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }


    // Method to create a progress bar dialog of either spinner or horizontal type
    @Override
    protected Dialog onCreateDialog(int id) {

            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progDialog.setMax(verseCount);
            if (storeBook) {
                progDialog.setMax(sumArray(chap_length));
            }
            progDialog.setMessage("Remaining verses:");
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
            int countAdd = msg.getData().getInt("countAdd");
            progDialog.setProgress(total);
            if (total <= 0){
                removeDialog(0);
                progThread.setState(ProgressThread.DONE);
                if (countAdd == 0 && chapterCancel){
                	chapterCancel = false;
                    chapterScope = true;
                    builder.show();
                    //no verses added. remove whole chapter
                } else if (countAdd > 0 && chapterCancel){
                	chapterCancel = false;
                    Toast.makeText(NT_MemTest.this, "Chapter added.", Toast.LENGTH_SHORT).show();
                    returntext.setText("Chapter added.");
                    returnverse.setText("Verse added.");
                }
            }
        }
    };
    
    
    
    
    @Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
        for (Prediction prediction : predictions) {
            if (version.equals("KJVS")) {
                if (prediction.score > 1.0) {
                    if (prediction.name.equals("Right to left")) {
                        nxtStrong();
                        break;
                    } else if (prediction.name.equals("Left to right")) {
                        if (flashStyle) {
                            flashBack(false);
                        } else {
                            prvStrong();
                        }
                        break;
                    }
                }
            } else {
                if (prediction.score > 1.0) {
                    if (prediction.name.equals("Right to left")) {
                        getNextVerse();
                        break;
                    } else if (prediction.name.equals("Caret")) {
                        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                        verseIncorrect();
                        break;
                    } else if (prediction.name.equals("Check")) {
                        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                        verseCorrect();
                        break;
                    } else if (prediction.name.equals("Left to right")) {
                        if (flashStyle) {
                            flashBack(false);
                        } else {
                            getPrevVerse();
                        }
                        break;
                    }
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.quit:
            startActivity(new Intent( NT_MemTest.this, SearchBible.class));
            break;
        case R.id.flash:
            if (version.equals("KJVS")){
                Toast.makeText(NT_MemTest.this, "Not available for this translation.", Toast.LENGTH_SHORT).show();
            }
            else {
                flashBack(false);
            }
            break;
        case R.id.info:
            String infotext;
            infotext = "Gestures:" + '\n' +
                    "1) right to left: get next verse" + '\n' +
                    "2) check: know the verse" + '\n' +
                    "3) caret: trouble with verse" + '\n' +
                    "#2 & #3 are for saved verses only." + '\n' +
                    "Tap the verse text to alternate with verse reference." + '\n' +
                    "Menu->Database to cache different translations" + '\n'+ '\n' + dispStats();
                new AlertDialog.Builder(NT_MemTest.this).setMessage(
                         infotext).setPositiveButton("OK",
                         new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                         }).show();
            break;
        case R.id.share:
            String message =  book + " " + chapter + ":" + verse + " - " + verseText + " (" + version + ")";
            if(addSig){
                message = message + " - Sent via NTSoulRecall AndroidApp.";
            }
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "NT Soul Recall: Share current verse..."));
            break;
        case R.id.settings:
            startActivity(new Intent(this, ShowSettingsActivity.class));
            break;
        case R.id.backup:
            startActivity(new Intent(this, ManageDB.class));
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      // Save UI state changes to the savedInstanceState.
      // This bundle will be passed to onCreate if the process is
      // killed and restarted.
      savedInstanceState.putString("MyBook", book);
      savedInstanceState.putInt("MyChapter", chapter);
      savedInstanceState.putInt("MyVerse", verse);
      savedInstanceState.putString("MyAuto", auto);
      savedInstanceState.putString("MyVersion", version);
      // etc.
      super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
      // Restore UI state from the savedInstanceState.
      // This bundle has also been passed to onCreate.
        book = savedInstanceState.getString("MyBook");
        chapter = savedInstanceState.getInt("MyChapter");
        verse = savedInstanceState.getInt("MyVerse");
        auto = savedInstanceState.getString("MyAuto");
        version = savedInstanceState.getString("MyVersion");
    }

    @Override
    protected void onResume()
    {
        
        updPrefs();
        super.onResume();
    }


    public class BookOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            try{
            String bookx = parent.getItemAtPosition(pos).toString();
            if (!book.equals(bookx)){
                book = parent.getItemAtPosition(pos).toString();
                updBook();
                updChapter();
                returnverse.setText(book + " " + chapter + ":" + verse);
                int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
                returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
                if (vf.getCurrentView() == returntext){
                    vf.showNext();
                }
            }
            }catch (Exception e){
                Toast.makeText(NT_MemTest.this, "Error selecting book.", Toast.LENGTH_SHORT).show();
            }
            if (!book.equals(myDbHelper.saveBook)){
                updCurrState();
            }
            
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class ChapterOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            try{
            int chapterx = Integer.valueOf(parent.getItemAtPosition(pos).toString());
            if (chapter != chapterx){
                chapter = chapterx;
                updChapter();
                returnverse.setText(book + " " + chapter + ":" + verse);
                int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
                returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
                if (vf.getCurrentView() == returntext){
                    vf.showNext();
                }
            }
            }catch (Exception e){
                Toast.makeText(NT_MemTest.this, "Error selecting chapter.", Toast.LENGTH_SHORT).show();
            }
            if (chapter != myDbHelper.saveChapter){
                updCurrState();
            }
            
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class VerseOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            try{
                verse = Integer.valueOf(parent.getItemAtPosition(pos).toString());
            }catch (Exception e){
                Toast.makeText(NT_MemTest.this, "Error selecting verse.", Toast.LENGTH_SHORT).show();
            }
            returnverse.setText(book + " " + chapter + ":" + verse);
            int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
            returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
            setVerseText(preview);
            if ((vf.getCurrentView() == returntext) && !reverseMode){
                vf.showNext();
            }else if((vf.getCurrentView() == returnverse) && reverseMode){
                vf.showNext();
            }
            if (verse != myDbHelper.saveVerse){
                updCurrState();
            }
            
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class AutoOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            auto = parent.getItemAtPosition(pos).toString();
            if (!auto.equals(myDbHelper.saveAuto)){
                updCurrState();    
            }

        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class VersionOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
            try{
                String versionx = parent.getItemAtPosition(pos).toString();
                if (!version.equals(versionx)){
                    version = versionx;
                    returnverse.setText(book + " " + chapter + ":" + verse);
                    int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
                    returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
                    setVerseText(preview);
                    if ((vf.getCurrentView() == returntext) && !reverseMode){
                        vf.showNext();
                    }else if((vf.getCurrentView() == returnverse) && reverseMode){
                        vf.showNext();
                    }
                    if (version.equals("KJVS")){
                        btnAddVerse.setVisibility(View.GONE);
                        btnAddChapter.setVisibility(View.GONE);
                    }
                    else {
                        btnAddVerse.setVisibility(View.VISIBLE);
                        btnAddChapter.setVisibility(View.VISIBLE);
                    }
                    flashBack(true);
                }
            }catch (Exception e){
                Toast.makeText(NT_MemTest.this, "Error selecting version.", Toast.LENGTH_SHORT).show();
            }
            if (!version.equals(myDbHelper.saveVersion)){
                updCurrState();
            }
            }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }

    public class StrongOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            try{
                String strongx = parent.getItemAtPosition(pos).toString();
                strongSelection = pos+1;
                setStrongDefinition(strongx);
            }catch (Exception e){
                if (version.equals("KJVS")) {
                    Toast.makeText(NT_MemTest.this, "Error selecting number.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    public void updCurrState(){
        myDbHelper.setSave(book, chapter, verse, version, auto, preview);
    }

    public void checkVerse(){
        //Get verse
        verseText = "";
        boolean verseSaved = myDbHelper.checkVerse(book, chapter, verse, version, false);
        if (verseSaved){
            verseText = myDbHelper.QueryVerse(book, chapter, verse, version, false);
        }else{
        	verseSaved = myDbHelper.checkVerse(book, chapter, verse, version, true);
        	if (verseSaved){
        		verseText = myDbHelper.QueryVerse(book, chapter, verse, version, true);
        	}else{
        		verseText = Module.check1(version, book, chapter, verse);
        	}
        }
        String inText = edittext.getText().toString();
        String text1 = "";
        String text2 = "";
        String wordScore = "";
        String orderScore = "";
        if (inText.equals("")){
            text1 = inText;
            if (preview && !hintDisplayed){
                VerseCompare cVerse = new VerseCompare(inText, verseText, preview, hintLength);
                cVerse.fiveWords(verseText);
                text2 = cVerse.getFiveWords();
                hintDisplayed = true;
            }
            else{
                text2 = verseText;
                hintDisplayed = false;
            }
            wordScore = "0.0%";
            orderScore = "0.0%";
        }
        else{
            VerseCompare cVerse = new VerseCompare(inText, verseText, preview, hintLength);
            if (hintDisplayed){
                cVerse = new VerseCompare(inText, verseText, false, hintLength);
                hintDisplayed = false;
            }else if (preview){
                hintDisplayed = true;
            }
            
            cVerse.checkVerse();
            text1 = cVerse.getUser();
            text2 = cVerse.getVerse();
            wordScore = cVerse.getWordScore();
            orderScore = cVerse.getOrderScore();
        }
        
        edittext.setText(text1);
        returntext.setText(text2);
        
        //adjust font of returntext based on length of verse
        double factorX = 1.15;
        if (flashStyle){
            factorX = 2.36;
        }else if (!showEditText){
            factorX = 1.62;
        }
        int textSizeDIP = (int)Math.sqrt(factorX*105000/(text2.length()+48)); //18 = 8pt font
        returntext.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (version.equals("KVJS")){
            returnverse.setText(text2);
            returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
            updStrong();
        }

        txtIn = (text1);
        txtOut = (text2);
        txtWord = (wordScore);
        txtOrder = (orderScore);
        scoreoutput.setText("Words: " + txtWord + ";" + '\n' + "Order: " + txtOrder);
        if (orderScore.equals("100.0%")){
            getNextVerse();
        }
        if (vf.getCurrentView() == returnverse){
            vf.showNext();
        }
    }

    public void updBook(){
        chap_length = Module.updChapters(book);
        RandText = ("RandText");
        chapterCount = chap_length.length-1;
        verseCount = chap_length[1];
        ArrayAdapter<CharSequence> adapterx;
        adapterx = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item);
        adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterx);
        for (int i = 1; i <= chapterCount; i++){
            adapterx.add(Integer.toString(i));
        }
        chapter = 1;
        verse = 1;
    }

    public void updChapter(){
        try {
        verseCount = chap_length[chapter];
        ArrayAdapter<CharSequence> adapterx;
        adapterx = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item);
        adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapterx);
        for (int i = 1; i <= verseCount; i++){
            adapterx.add(Integer.toString(i));
        }
        spinner3.setSelection(0);
        verse = 1;
        }
        catch (Exception e) {

        }
    }
    
    public void updStrong(){
        try {
            //loop through vereText to find braces
            boolean inBraces = false;
            int positionStart = 0;
            String strongWord = "";
            Vector<String> store = new Vector<String>();
            for (int i = 0; i <= verseText.length() - 1; i ++){
                if (verseText.substring(i,i+1).equals("{")){
                    inBraces = true;
                    positionStart = i+1;
                }
                else if(verseText.substring(i,i+1).equals("}")){
                    inBraces = false;
                    //strongWord = verseText.substring(positionStart,i).replace('(',' ').replace(')',' ').trim();
                    strongWord = verseText.substring(positionStart,i);
                    if (!strongWord.contains("(")) {
                        store.add(strongWord);
                    }
                }
            }
            ArrayAdapter<CharSequence> adapterx;
            adapterx = new ArrayAdapter(
                    this, android.R.layout.simple_spinner_item);
            adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapterx);
            strongLength = store.size();
            for (int i = 0; i <= strongLength-1; i++){
                adapterx.add(store.get(i).toString());
            }
            spinner6.setSelection(0);
            strongSelection = 1;
        }
        catch (Exception e) {
            
        }
    }

    public void nxtRandom(){
            //set timer
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        time2.add(Calendar.SECOND, 5);
        //reset if all verses have been used
        int randcount = 0;
        for (int i = 0; i < RandText.length(); i++){
            if (this.RandText.substring(i,i+1).equals(".")){
                randcount += 1;
            }
        }

        if (randcount == Module.SumArray(chap_length)){
            //resetting random verse list
            time2 = Calendar.getInstance();
            time2.add(Calendar.SECOND, 5);
            RandText = ("RandText");
        }
        boolean foundNewVerse = false;
        while (!foundNewVerse){
            //check if 5 seconds has passed, if so reset random list
            Calendar time3 = Calendar.getInstance();
            if (time2.before(time3)){
                RandText = ("RandText");
            }
            //pick random verse from book
            int number1 = Module.RandomNumber(Module.SumArray(chap_length));
            String numstring = Integer.toString(number1);
            if (numstring.length() == 1){
                numstring = "000" + numstring;
            }
            else if (numstring.length() == 2){
                numstring = "00" + numstring;
            }
            else if (numstring.length() == 3){
                numstring = "0" + numstring;
            }
            boolean recentVerse = false;
            //check if verse used recently
            for (int i = 0; i <= RandText.length()-4; i++){
                if (RandText.substring(i,i+4).equals(numstring)){
                    recentVerse = true;
                }
            }
            if (recentVerse){
                continue;
            }
            //add verse to string
            RandText += numstring + ".";
            //proceed
            int verseCount2 = 0;
            for (int i = 1; i <= chap_length.length-1; i++){
                verseCount2 += chap_length[i];
                if (verseCount2 >= number1){
                    spinner2.setSelection(i - 1);
                    chapter = i;
                    updChapter();
                    spinner3.setSelection(chap_length[i] - (verseCount2 - number1)-1);
                    verse = (chap_length[i] - (verseCount2 - number1));
                    foundNewVerse = true;
                    break;
                }
            }
        }
        returnverse.setText(book + " " + chapter + ":" + verse);
        int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (vf.getCurrentView() == returntext){
            vf.showNext();
        }
    }
    public void prvStrong(){
        int strongSelected = strongSelection-1;
        if (strongSelected > 0){
            spinner6.setSelection(strongSelected-1);
            strongSelection -= 1;
        }
    }
    public void nxtStrong(){
        int strongSelected = strongSelection-1;
        if (strongSelection < strongLength){
            spinner6.setSelection(strongSelected+1);
            strongSelection+=1;
        }
    }
    public void prvVerse(){
    	//check if at first verse
    	int verseSelected = verse-1;
    	if (verseSelected > 0){
    		spinner3.setSelection(verseSelected -1);
    		verse = verseSelected;
    	}
    	else{
    		//if at first verse, check if at first chapter
    		int chapterSelected = chapter -1;
    		if (chapterSelected > 0){
    			spinner2.setSelection(chapterSelected - 1);
    			chapter = chapterSelected;
    			updChapter();
    			spinner3.setSelection(verseCount-1);
    			verse = verseCount;
    			//if at x:1 go to last verse of chapter x-1
    		}
    		else {
    			spinner2.setSelection(chapterCount-1);
    			
    			chapter = chapterCount;
    			updChapter();
    			spinner3.setSelection(verseCount -1);
    			verse = verseCount;
    			//if at 1:1 go to last verse of last chapter
    		}
    	}
        returnverse.setText(book + " " + chapter + ":" + verse);
        int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (vf.getCurrentView() == returntext){
            vf.showNext();
        }
    }
    public void nxtVerse(){
        //check if at last verse
        int verseSelected = verse - 1;
        if (verseSelected + 1 < verseCount){
            spinner3.setSelection(verseSelected + 1);
            verse = verseSelected + 2;
        }
        else {
        //if at last verse, check if at last chapter
            int chapterSelected = chapter - 1;
            if (chapterSelected + 1 < chapterCount){
                spinner2.setSelection(chapterSelected + 1);
                chapter = chapterSelected + 2;
                updChapter();
                spinner3.setSelection(0);
                verse = 1;
            }
            else{
                spinner2.setSelection(0);
                updChapter();
                chapter = 1;
                spinner3.setSelection(0);
                verse = 1;
                //if at last chapter, goto 1:1
            }

        }
        returnverse.setText(book + " " + chapter + ":" + verse);
        int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (vf.getCurrentView() == returntext){
            vf.showNext();
        }
    }

    public void nxtMyVerse(){
        try{
            String[][] tempVerses = new String[4][1];
            int cntVerseList = tempVerses[0].length -1;
            int cntVerseTotal = 0;
             if (version.equals("KJV")){
                tempVerses = myDbHelper.KJVverses;
                cntVerseTotal = myDbHelper.KJVcount;
                cntVerseList = myDbHelper.KJVvcount;
            }else if (version.equals("NIV")){
                tempVerses = myDbHelper.NIVverses;
                cntVerseTotal = myDbHelper.NIVcount;
                cntVerseList = myDbHelper.NIVvcount;
            }else if (version.equals("NKJV")){
                tempVerses = myDbHelper.NKJVverses;
                cntVerseTotal = myDbHelper.NKJVcount;
                cntVerseList = myDbHelper.NKJVvcount;
            }else if (version.equals("NASB")){
                tempVerses = myDbHelper.NASBverses;
                cntVerseTotal = myDbHelper.NASBcount;
                cntVerseList = myDbHelper.NASBvcount;
            }else if (version.equals("HCSB")){
                tempVerses = myDbHelper.HCSBverses;
                cntVerseTotal = myDbHelper.HCSBcount;
                cntVerseList = myDbHelper.HCSBvcount;
            }else if (version.equals("RVR")){
                 tempVerses = myDbHelper.RVRverses;
                 cntVerseTotal = myDbHelper.RVRcount;
                 cntVerseList = myDbHelper.RVRvcount;
             }else if (version.equals("ESV")){
                 tempVerses = myDbHelper.ESVverses;
                 cntVerseTotal = myDbHelper.ESVcount;
                 cntVerseList = myDbHelper.ESVvcount;
             }else if (version.equals("AMP")){
                 tempVerses = myDbHelper.AMPverses;
                 cntVerseTotal = myDbHelper.AMPcount;
                 cntVerseList = myDbHelper.AMPvcount;
             }
            
            if (cntVerseList > 0){
                int number2 = Module.RandomNumber(cntVerseTotal);
                //match number2 to number1 based on weight of tempVerses
                int number1 = 0;
                int cumWeight = 0;
                for (int i = 1; i <= cntVerseList;i++){
                    cumWeight += Math.pow(Integer.valueOf(tempVerses[3][i]),3);
                    if (cumWeight >= number2){
                        number1 = i;
                        i = cntVerseList;
                    }
                }

                book = tempVerses[0][number1];
                //set the spinners
                ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
                int spinnerPosition = myAdap.getPosition(book);
                spinner.setSelection(spinnerPosition);
                updBook();
                chapter = Integer.valueOf(tempVerses[1][number1]);
                spinner2.setSelection(chapter-1);
                updChapter();
                verse = Integer.valueOf(tempVerses[2][number1]);
                spinner3.setSelection(verse - 1);
            }else{
                Toast.makeText(NT_MemTest.this, "No verses saved for this version.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(NT_MemTest.this, "Error finding next saved verse.", Toast.LENGTH_SHORT).show();
        }
        returnverse.setText(book + " " + chapter + ":" + verse);
        int textSizeDIP = (int)Math.sqrt(105000/(returnverse.getText().length()+30)); //18 = 8pt font
        returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (vf.getCurrentView() == returntext){
            vf.showNext();
        }
    }
    
    public void getVotD(){
    	String[][] verses = votd.VerseOfTheDay();
    	String[][] savedVerses = myDbHelper.ListVerses(version);
    	votd.shuffleArray(verses, addOT);
    	String bookV = "James";
    	String chapterV = "1";
    	String verseV = "1";
    	boolean foundVerse = false;
    	int klimit = 365;
    	if (addOT)
    		klimit = verses[0].length;
    	outerLoopx:
    	for (int k = 0; k < klimit; k ++){
    		if (verses[0][k] != null){
    			foundVerse = true;
    			if (savedVerses.length == 3){
    				for (int l = 0; l < savedVerses[0].length; l++){
    					if (verses[0][k] == savedVerses[0][l] &&
    							verses[1][k] == savedVerses[1][l] &&
    							verses[2][k] == savedVerses[2][l]){
    						foundVerse=false;
    					}
    				}
    			}
    			if (foundVerse){
    				bookV = verses[0][k];
    				chapterV = verses[1][k];
    				verseV = verses[2][k];
    				break outerLoopx;
    			}
    		}
    	}
    	if (foundVerse){
    		book = bookV;
            //set the spinners
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(book);
            spinner.setSelection(spinnerPosition);
            updBook();
            chapter = Integer.parseInt(chapterV);
            spinner2.setSelection(chapter-1);
            updChapter();
            verse = Integer.parseInt(verseV);
            spinner3.setSelection(verse - 1);
    	}else{
    		Toast.makeText(NT_MemTest.this, "All verses of the day are saved.", Toast.LENGTH_SHORT);
    	}
    }

    public void addVerseExec(){
        boolean verseDict = myDbHelper.checkVerse(book, chapter, verse, version, false);
        if (verseDict){
            chapterScope = false;
            builder.show();
            //builder dialog will handle removing verse
        }else{
            //add verse
            verseText = Module.check1(version, book, chapter, verse);
            myDbHelper.addVerse(book, chapter, verse, version, verseText, false);
            Toast.makeText(NT_MemTest.this, "Verse added.", Toast.LENGTH_SHORT).show();
            returntext.setText("Verse added.");
            returnverse.setText("Verse added.");
        }
    }

    public void addChapExec(){
        try{
        int countAdd = 0;
        boolean verseDict;
        for (int i = 1; i <= verseCount; i++){
            verseDict = myDbHelper.checkVerse(book, chapter, i, version, false);
            if (verseDict == false){
                String vText = Module.check1(version, book, chapter, i);
                myDbHelper.addVerse(book, chapter, i, version, vText, false);
                countAdd += 1;
            }
        }
        if (countAdd == 0){
            chapterScope = true;
            builder.show();
            //no verses added. remove whole chapter
        } else{
            Toast.makeText(NT_MemTest.this, "Chapter added.", Toast.LENGTH_SHORT).show();
            returntext.setText("Chapter added.");
            returnverse.setText("Verse added.");
        }
        //myDbHelper.weightVerses(version);
        }catch (Exception e){
            Toast.makeText(NT_MemTest.this, "Error adding chapter.", Toast.LENGTH_SHORT).show();
        }
    }

    public void verseCorrect(){
        boolean verseInDB = myDbHelper.checkVerse(book, chapter, verse, version, false);
        if (verseInDB){
            myDbHelper.VerseRight(book, chapter, verse, version, true);
        }
        getNextVerse();
    }

    public void verseIncorrect(){
        boolean verseInDB = myDbHelper.checkVerse(book, chapter, verse, version, false);
        if (verseInDB){
            myDbHelper.VerseRight(book, chapter, verse, version, false);
        }
        getNextVerse();
    }

    public void flashBack(boolean noChange){

        Resources r = getResources();
        int orient = r.getConfiguration().orientation;
        if (noChange){
            flashStyle = !flashStyle;
        }
        ViewGroup.LayoutParams params1 = ll01.getLayoutParams();
        ViewGroup.LayoutParams params2 = ll02.getLayoutParams();
        ViewGroup.LayoutParams params3 = tvBlank01.getLayoutParams();
        ViewGroup.LayoutParams params4 = editlayout.getLayoutParams();
        ViewGroup.LayoutParams params5 = ll03.getLayoutParams();
        ViewGroup.LayoutParams params6 = llVerse.getLayoutParams();
        ViewGroup.LayoutParams params7 = ll04.getLayoutParams();
        if (orient == 2){
            //landscape
            if (flashStyle){
                //convert back to normal
                int px480 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 476, r.getDisplayMetrics());
                int px320 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
                int px100 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, r.getDisplayMetrics());
                int px50 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, r.getDisplayMetrics());
                int px12 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, r.getDisplayMetrics());
                int px200 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 232, r.getDisplayMetrics());
                int px36 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, r.getDisplayMetrics());
                int px67 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 63, r.getDisplayMetrics());
                int px157 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 153, r.getDisplayMetrics());
                params1.height = px50;
                params2.height = px50;
                params3.height = px12;
                params4.height = px67;
                params5.height = px12;
                params6.height = px157;
                if (version.equals("KJVS")) {
                    params7.height = px157;
                } else {
                    params7.height = 0;
                }
                ll01.setLayoutParams(new LinearLayout.LayoutParams(params1));
                ll02.setLayoutParams(new LinearLayout.LayoutParams(params2));
                tvBlank01.setLayoutParams(new LinearLayout.LayoutParams(params3));
                editlayout.setLayoutParams(new LinearLayout.LayoutParams(params4));
                ll03.setLayoutParams(new LinearLayout.LayoutParams(params5));
                llVerse.setLayoutParams(new LinearLayout.LayoutParams(params6));
                ll04.setLayoutParams(new LinearLayout.LayoutParams(params7));
            }else{
                //convert to fullscreen
                int px480 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 460, r.getDisplayMetrics());
                int px320 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 316, r.getDisplayMetrics());
                params1.height = 0;
                params2.height = 0;
                params3.height = 0;
                params4.height = 0;
                params5.height = 0;
                params6.height = px320;
                params7.height = 0;
                ll01.setLayoutParams(new LinearLayout.LayoutParams(params1));
                ll02.setLayoutParams(new LinearLayout.LayoutParams(params2));
                tvBlank01.setLayoutParams(new LinearLayout.LayoutParams(params3));
                editlayout.setLayoutParams(new LinearLayout.LayoutParams(params4));
                ll03.setLayoutParams(new LinearLayout.LayoutParams(params5));
                llVerse.setLayoutParams(new LinearLayout.LayoutParams(params6));
                ll04.setLayoutParams(new LinearLayout.LayoutParams(params7));
            }
        }else{
            if (flashStyle){
                //convert back to normal
                int px320 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 316, r.getDisplayMetrics());
                int px100 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, r.getDisplayMetrics());
                int px50 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, r.getDisplayMetrics());
                int px12 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, r.getDisplayMetrics());
                int px200 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 232, r.getDisplayMetrics());
                int px36 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, r.getDisplayMetrics());
                params1.height = px50;
                params2.height = px50;
                params3.height = px12;
                params4.height = px100;
                params5.height = px36;
                params6.height = px200;
                if (version.equals("KJVS")) {
                    params7.height = px320;
                } else {
                    params7.height = 0;
                }
                ll01.setLayoutParams(new LinearLayout.LayoutParams(params1));
                ll02.setLayoutParams(new LinearLayout.LayoutParams(params2));
                tvBlank01.setLayoutParams(new LinearLayout.LayoutParams(params3));
                editlayout.setLayoutParams(new LinearLayout.LayoutParams(params4));
                ll03.setLayoutParams(new LinearLayout.LayoutParams(params5));
                llVerse.setLayoutParams(new LinearLayout.LayoutParams(params6));
                ll04.setLayoutParams(new LinearLayout.LayoutParams(params7));
            }else{
                //convert to fullscreen
                int px480 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 460, r.getDisplayMetrics());
                int px320 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 316, r.getDisplayMetrics());
                params1.height = 0;
                params2.height = 0;
                params3.height = 0;
                params4.height = 0;
                params5.height = 0;
                params6.height = px480;
                params7.height = 0;
                ll01.setLayoutParams(new LinearLayout.LayoutParams(params1));
                ll02.setLayoutParams(new LinearLayout.LayoutParams(params2));
                tvBlank01.setLayoutParams(new LinearLayout.LayoutParams(params3));
                editlayout.setLayoutParams(new LinearLayout.LayoutParams(params4));
                ll03.setLayoutParams(new LinearLayout.LayoutParams(params5));
                llVerse.setLayoutParams(new LinearLayout.LayoutParams(params6));
                ll04.setLayoutParams(new LinearLayout.LayoutParams(params7));
            }
        }
        flashStyle = !flashStyle;
        if (!flashStyle){
            toggleEditLayout();
        }
    }

    public void toggleEditLayout(){
        Resources r = getResources();
        int orient = r.getConfiguration().orientation;
        int px320 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 316, r.getDisplayMetrics());
        int px480 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 476, r.getDisplayMetrics());
        int px100 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, r.getDisplayMetrics());
        int px200 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 232, r.getDisplayMetrics());
        int px36 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, r.getDisplayMetrics());
        int px67 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 63, r.getDisplayMetrics());
        int px157 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 153, r.getDisplayMetrics());
        ViewGroup.LayoutParams params1 = llVerse.getLayoutParams();
        ViewGroup.LayoutParams params2 = editlayout.getLayoutParams();
        if (orient == 2){
            if (showEditText){
                if (!flashStyle){
                    scoreoutput.setTextColor(textColor);
                    if (params2.height == 0){
                    	params2.height = params1.height*67/224;
                    	params1.height = params1.height*157/224;
                    	editlayout.setLayoutParams(new LinearLayout.LayoutParams(params2));
                        llVerse.setLayoutParams(new LinearLayout.LayoutParams(params1));
                    }
                }

            }else{
                if (!flashStyle){
                	params1.height = params1.height + params2.height;
                	params2.height = 0;
                    scoreoutput.setTextColor(bgColor);
                    editlayout.setLayoutParams(new LinearLayout.LayoutParams(params2));
                    llVerse.setLayoutParams(new LinearLayout.LayoutParams(params1));
                }
            }
        }else{
            if (showEditText){
                if (!flashStyle){
                    scoreoutput.setTextColor(textColor);
                    if (params2.height == 0){
                    	params2.height = params1.height*100/336;
                    	params1.height = params1.height*236/336;
                    	editlayout.setLayoutParams(new LinearLayout.LayoutParams(params2));
                        llVerse.setLayoutParams(new LinearLayout.LayoutParams(params1));
                    }
                }

            }else{
                if (!flashStyle){
                	params1.height = params1.height + params2.height;
                	params2.height = 0;                	
                    scoreoutput.setTextColor(bgColor);
                    editlayout.setLayoutParams(new LinearLayout.LayoutParams(params2));
                    llVerse.setLayoutParams(new LinearLayout.LayoutParams(params1));
                }
            }
        }
    }

    public void alertExec(){
        if (chapterScope){
            //remove chapter
            for (int i = 1; i <= verseCount; i++){
            myDbHelper.removeVerse(book, chapter, i, version);
            }
            Toast.makeText(NT_MemTest.this, "Chapter removed.", Toast.LENGTH_SHORT).show();
            returntext.setText("Chapter removed.");
        }else{
            //remove verse
            myDbHelper.removeVerse(book, chapter, verse, version);
            Toast.makeText(NT_MemTest.this, "Verse removed.", Toast.LENGTH_SHORT).show();
            returntext.setText("Verse removed.");
        }
        //update verses list so it will not choose those verses again
        myDbHelper.weightVerses(version);
    }

    public void getNextVerse(){
        prevBook = book;
        prevChapter = chapter;
        prevVerse = verse;
        if (auto.equals("Increment")){
            nxtVerse();
        }
        else if (auto.equals("Random")){
            nxtRandom();
        }
        else if (auto.equals("MyVerses")){
            if (version.equals("KJVS")){
                nxtVerse();
            }
            else {
                nxtMyVerse();
            }
        }
        //run checkverse if reverse flash card
        if (reverseMode){
            setVerseText(preview);
            vf.showNext();
            if (preview){
                hintDisplayed = true;
            }else{
                hintDisplayed = false;
            }
        }else{
            hintDisplayed = false;
        }
        updCurrState();
    }

    public void setStrongDefinition(String strongx){
        String definition = "";
        definition = myDbHelper.QueryStrong(strongx);
        strongtext.setText(definition);
    }
    public void setVerseText(boolean displayHint){
        verseText = "";
        boolean verseSaved = myDbHelper.checkVerse(book, chapter, verse, version, false);
        if (verseSaved){
            verseText = myDbHelper.QueryVerse(book, chapter, verse, version, false);
            if (displayHint){
                VerseCompare cVerse = new VerseCompare("", verseText, preview, hintLength);
                cVerse.fiveWords(verseText);
                returntext.setText(cVerse.getFiveWords());
            }else{
                returntext.setText(verseText);
            }
        }else{
        	verseSaved = myDbHelper.checkVerse(book, chapter, verse, version, true);
        	if (verseSaved){
        		verseText = myDbHelper.QueryVerse(book, chapter, verse, version, true);
                if (displayHint){
                    VerseCompare cVerse = new VerseCompare("", verseText, preview, hintLength);
                    cVerse.fiveWords(verseText);
                    returntext.setText(cVerse.getFiveWords());
                }else{
                    returntext.setText(verseText);
                }
        	}else{
	            verseText = Module.check1(version, book, chapter, verse);
	            if (displayHint){
	                VerseCompare cVerse = new VerseCompare("", verseText, preview, hintLength);
	                cVerse.fiveWords(verseText);
	                returntext.setText(cVerse.getFiveWords());
	            }else{
	                returntext.setText(verseText);
	            }
        	}
        }
        
        double factorX = 1.15;
        if (flashStyle){
            factorX = 2.36;
        }else if (!showEditText){
            factorX = 1.62;
        }
        int textSizeDIP = (int)Math.sqrt(factorX*105000/(verseText.length()+48)); //18 = 8pt font
        returntext.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
        if (version.equals("KJVS")){
            returnverse.setText(verseText);
            returnverse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeDIP);
            updStrong();
        }
    }

    public void updPrefs(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        hintLength = sharedPrefs.getInt("hint_length", 5);
        textColor = sharedPrefs.getInt("color1", -99999);
        bgColor = sharedPrefs.getInt("color2", 0);
        showEditText = sharedPrefs.getBoolean("show_edittext", false);
        reverseMode = sharedPrefs.getBoolean("reverseMode", true);
        addOT = sharedPrefs.getBoolean("add_OT",false);
        darkMode = sharedPrefs.getBoolean("darkMode", false);
        if (darkMode) {
            bgColor = Color.BLACK;
            textColor = Color.WHITE;
        }
        volControl = sharedPrefs.getBoolean("volControl", false);
        addSig = sharedPrefs.getBoolean("add_sig",true);
        screenOn = sharedPrefs.getBoolean("screen_on",false);
        if (screenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        spinner = (Spinner) findViewById(R.id.sBook);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.book_list, android.R.layout.simple_spinner_item);
        if (addOT){
            adapter = ArrayAdapter.createFromResource(this, R.array.book_list_all, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new BookOnItemSelectedListener());
        myDbHelper.getSave();
        auto = myDbHelper.saveAuto;
        preview = myDbHelper.saveHint;
        checkbox.setChecked(preview);
        getSaveVerse();
        toggleEditLayout();
        updColors();
    }

    public void updColors(){
        ll00.setBackgroundColor(bgColor);
        llVerse.setBackgroundColor(bgColor);
        tvBlank01.setBackgroundColor(bgColor);
        if (textColor != defTextColor){
            returntext.setTextColor(textColor);
            returnverse.setTextColor(textColor);
            if (showEditText){
                scoreoutput.setTextColor(textColor);
            }else{
                scoreoutput.setTextColor(bgColor);
            }
            checkbox.setTextColor(textColor);
        }
    }

    public void getPrevVerse(){
    	if (auto.equals("Increment")){
            prvVerse();
        }
        else {
        	book = prevBook;
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(book);
            spinner.setSelection(spinnerPosition);
            updBook();
            chapter = prevChapter;
            spinner2.setSelection(chapter-1);
            updChapter();
            verse = prevVerse;
            spinner3.setSelection(verse - 1);
        }        
        if (reverseMode){
            checkVerse();
        }
        //run checkverse if reverse flash card
        if (reverseMode){
            setVerseText(preview);
            vf.showNext();
            if (preview){
                hintDisplayed = true;
            }else{
                hintDisplayed = false;
            }
        }else{
            hintDisplayed = false;
        }
    }

    public void getSaveVerse(){
        book = myDbHelper.saveBook;
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = myAdap.getPosition(book);
        spinner.setSelection(spinnerPosition);
        updBook();
        chapter = myDbHelper.saveChapter;
        spinner2.setSelection(chapter-1);
        updChapter();
        verse = myDbHelper.saveVerse;
        spinner3.setSelection(verse - 1);
        version = myDbHelper.saveVersion;
        myAdap = (ArrayAdapter)  spinner5.getAdapter();
        spinnerPosition = myAdap.getPosition(version);
        spinner5.setSelection(spinnerPosition);
        if (reverseMode){
            checkVerse();
        }
        //run checkverse if reverse flash card
        if (reverseMode){
            setVerseText(preview);
            vf.showNext();
            if (preview){
                hintDisplayed = true;
            }else{
                hintDisplayed = false;
            }
        }else{
            hintDisplayed = false;
        }

        if (version.equals("KJVS")){
            btnAddVerse.setVisibility(View.GONE);
            btnAddChapter.setVisibility(View.GONE);
        }
        else {
            btnAddVerse.setVisibility(View.VISIBLE);
            btnAddChapter.setVisibility(View.VISIBLE);
        }
    }

    public String dispStats(){
        String strStat = "";
        int numVerses = myDbHelper.verseCount(version);
        strStat = "# of Verses saved: " + numVerses;
        int numEVerses = myDbHelper.verseScoreCount(version,1);
        int numDVerses = myDbHelper.verseScoreCount(version,2);
        int numCVerses = myDbHelper.verseScoreCount(version,3);
        int numBVerses = myDbHelper.verseScoreCount(version,4);
        int numAVerses = myDbHelper.verseScoreCount(version,5);
        int numFVerses = myDbHelper.verseScoreCount(version,0);
        boolean gpaflag = true;
        if (numVerses==0){
        	gpaflag = false;
        }
        double gpacalc = 0;
        if (gpaflag){
        	gpacalc = ((double) (numAVerses*4+numBVerses*3+numCVerses*2+numDVerses))/((double) numVerses);
        	gpacalc = ((double) Math.round(gpacalc*100))/((double) 100);
        }
        strStat = strStat + '\n' + "Score   Count";
        strStat = strStat + '\n' + "GPA:   " + gpacalc;
        strStat = strStat + '\n' + "A:          " + numAVerses;
        strStat = strStat + ";   " + "D:          " + numDVerses;
        strStat = strStat + '\n' + "B:          " + numBVerses;
        strStat = strStat + ";   " + "E:          " + numEVerses;
        strStat = strStat + '\n' + "C:          " + numCVerses;
        strStat = strStat + ";   " + "F:          " + numFVerses;
        return strStat;
    }
    
    public int sumArray(int[] v_array){
    	int sum = 0;
    	for (int i = 0; i < v_array.length; i ++){
    		sum += v_array[i];
    	}
    	return sum;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (volControl) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    if (action == KeyEvent.ACTION_UP) {
                        //TODO
                        getPrevVerse();
                    }
                    return true;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if (action == KeyEvent.ACTION_DOWN) {
                        //TODO
                        getNextVerse();
                    }
                    return true;
                default:
                    return super.dispatchKeyEvent(event);
            }
        }
        else {
            return super.dispatchKeyEvent(event);
        }
    }
    
 // Inner class that performs progress calculations on a second thread.  Implement
    // the thread by subclassing Thread and overriding its run() method.  Also provide
    // a setState(state) method to stop the thread gracefully.
    
    private class ProgressThread extends Thread {	
        
        // Class constants defining state of the thread
        final static int DONE = 0;
        final static int RUNNING = 1;
        
        Handler mHandler;
        int mState;
        int total;
        int countAdd = 0;
        int i = 1;
        int j = 1;
        boolean verseDict;
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
            total = verseCount;
            if (storeBook)
            	total = sumArray(chap_length);            
            myDbHelper.beginTrans();
            int a = 1;
            int c = 1;
            while (mState == RUNNING) {
                // The method Thread.sleep throws an InterruptedException if Thread.interrupt() 
                // were to be issued while thread is sleeping; the exception must be caught.
                try {
                    // Control speed of update (but precision of delay not guaranteed)
                    Thread.sleep(25);
                    
                    if (storeBook){
                    	if (a < chap_length.length){
	                    	verseDict = myDbHelper.checkVerse(book, a, c, version, true);
	                    	if (verseDict == false){
	                    		String vText = Module.check1(version, book, a, c);
	                            myDbHelper.addVerse(book, a, c, version, vText, true);
	                            countAdd += 1;
	                    	}
	                    	if (c == chap_length[a]){
	                    		a++;
	                    		c = 1;
	                    	}
	                    	else{
	                    		c++;
	                    	}
                    	}
                    }
                    else{
                    	if (a <= verseCount){
	                        verseDict = myDbHelper.checkVerse(book, chapter, a, version, false);
	                        if (verseDict == false){
	                        	verseDict = myDbHelper.checkVerse(book, chapter, a, version, true);
	                        	String vText = null;
	                        	if (verseDict){
	                        		vText = myDbHelper.QueryVerse(book, chapter, a, version, true);
	                        	}else{
	                        		vText = Module.check1(version, book, chapter, a);
	                        	}
	                            myDbHelper.addVerse(book, chapter, a, version, vText, false);
	                            countAdd += 1;
	                        }
	                        a++;
                    	}
                    }
                    
                } catch (InterruptedException e) {
                    //Log.e("ERROR", "Thread was Interrupted");
                }
                
                // Send message (with current value of  total as data) to Handler on UI thread
                // so that it can update the progress bar.
                
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                b.putInt("countAdd",countAdd);
                msg.setData(b);
                mHandler.sendMessage(msg);
                
                total--;    // Count down
            }
            myDbHelper.finishTrans();
            myDbHelper.commitTrans();
        }
        
        // Set current state of thread (use state=ProgressThread.DONE to stop thread)
        public void setState(int state) {
            mState = state;
        }
    }
}