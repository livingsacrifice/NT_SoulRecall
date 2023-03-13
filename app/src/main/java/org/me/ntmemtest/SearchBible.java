package org.me.ntmemtest;


//import org.me.ntmemtest.util.*;
import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.*;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.os.SystemClock;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//import android.widget.ArrayAdapter.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.ViewGroup;
//import android.view.KeyEvent;
//import android.net.*;
import java.io.*;
//import java.util.zip.*;
import java.util.*;
/**
 * Created by user on 2/13/15.
 */
public class SearchBible extends Activity {
    //private MyApplication application;
    private Button searchButton;
    private ListView searchList;
    private EditText searchText;
    private Spinner searchVersion;
    private Spinner searchParam;
    private CheckBox checkbox;
    //private Button downloadButton;
    String version;
    public DataBaseHelper myDbHelper;
    //public NT_MemTest main1;

    private String strSearch;
    private String strVersion;
    private String strParams;
    private boolean boolShow;
    @Override
    /*public boolean onKeyDown(int keyCode, KeyEvent event)  {

        if (keyCode == KeyEvent.KEYCODE_BACK)  //Override Keyback to do nothing in this case.
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);  //-->All others key will work as usual
    }*/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ToDo add your GUI initialization code here
        //application = (MyApplication) getApplication();

        myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbHelper.checkSearchSave();
        myDbHelper.getSearchSave();
        strSearch = myDbHelper.saveSearch;
        strVersion = myDbHelper.saveVersion2;
        //Toast.makeText(application, "Version saved is" + strVersion, Toast.LENGTH_SHORT).show();
        strParams = myDbHelper.saveParams;
        boolShow = myDbHelper.saveSearchShow;

        setContentView(R.layout.searchbible);
        searchButton = (Button) findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //add code here
                //Toast.makeText(application, "Test Message", Toast.LENGTH_SHORT).show();

                //ProgressDialog dialog = new ProgressDialog(SearchBible.this);
                //dialog.setMessage("Searching bible...");
                //dialog.show();
                searchTheBible();
                //dialog.dismiss();
                //new ImportDatabaseTask().execute();
            }
        });
        searchList = (ListView) findViewById(R.id.querylist);

        searchText = (EditText) findViewById(R.id.editText);
        searchText.setText(strSearch);

        searchVersion = (Spinner) findViewById(R.id.sVersionTwo);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
                this, R.array.version_list2, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchVersion.setAdapter(adapter5);
        searchVersion.setOnItemSelectedListener(new VersionOnItemSelectedListener());
        //searchVersion.setSelection(0);
        int spinnerPosition = adapter5.getPosition(strVersion);
        searchVersion.setSelection(spinnerPosition);

        searchParam = (Spinner) findViewById(R.id.sSearch);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(
                this, R.array.search_opt, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchParam.setAdapter(adapter6);
        searchParam.setOnItemSelectedListener(new VersionOnItemSelectedListener());
        //searchParam.setSelection(2);
        spinnerPosition = adapter6.getPosition(strParams);
        searchParam.setSelection(spinnerPosition);

        checkbox = (CheckBox) findViewById(R.id.chText);
        //checkbox.setChecked(true);
        checkbox.setChecked(boolShow);
        checkbox.setText("");


    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("Text",searchText.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        searchText.setText(savedInstanceState.getString("TEXT"));
    }



    public void searchTheBible() {
        strSearch = searchText.getText().toString();
        if (strSearch.trim().length() > 0) {
            strVersion = searchVersion.getSelectedItem().toString();
            strParams = searchParam.getSelectedItem().toString();
            boolShow = checkbox.isChecked();
            myDbHelper.setSearchSave(strSearch,strVersion,strParams,boolShow);
            //final String[][] returnVerses = new ImportDatabaseTask().execute().get();
            //final String[][] returnVerses = myDbHelper.SearchVerses(strSearch, strParams, strVersion);
            new ExportDatabaseTable(this,searchList,1).execute();
            //myDbHelper.setSearchResults(strSearch, strParams, strVersion);
            //final String[][] returnVerses = myDbHelper.getSearchResults();
            //ArrayAdapter<String> adapter;
            //adapter = null;
            /*ArrayList<String> myStringArray1 = new ArrayList<String>();
            for (int i = 0; i < returnVerses[0].length; i++) {
                if (boolShow)
                {
                    myStringArray1.add(returnVerses[1][i] + " " + returnVerses[0][i]);
                }
                else
                {
                    myStringArray1.add(returnVerses[0][i]);
                }
            }
            if (boolShow) {
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, myStringArray1) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                        text1.setText(returnVerses[1][position]);
                        text1.setTextSize(14);
                        text2.setText(returnVerses[0][position]);
                        text2.setTextSize(12);
                        return view;
                    }
                };

                searchList.setAdapter(adapter);
            }
            else{
                ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray1);
                searchList.setAdapter(adapter2);
            }
            searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String text4 = "";
                    try {
                        TextView text3 = (TextView) view.findViewById(android.R.id.text2);
                        text4 = (String) text3.getText();
                    }
                    catch (Exception ex){
                        text4 = (String) searchList.getItemAtPosition(position);
                    }
                    String[] splitText = text4.split(" ");
                    String book;
                    String[] verseCombo;
                    String chapter;
                    String verse;
                    String version;
                    if (splitText.length == 5)
                    {
                        book = splitText[0] + " " + splitText[1];
                        verseCombo = splitText[2].split(":");
                        chapter = verseCombo[0];
                        verse = verseCombo[1];
                        version = splitText[4];
                    }
                    else
                    {
                        book = splitText[0];
                        verseCombo = splitText[1].split(":");
                        chapter = verseCombo[0];
                        verse = verseCombo[1];
                        version = splitText[3];
                    }
                    //Toast.makeText(SearchBible.this, text4.length() + "," + book + "," + chapter + "," + verse + "," + version, Toast.LENGTH_SHORT).show();
                    myDbHelper.getSave();
                    String auto = myDbHelper.saveAuto;
                    boolean hint = myDbHelper.saveHint;
                    myDbHelper.setSave(book, Integer.parseInt(chapter),Integer.parseInt(verse),version, auto, hint);
                    finish();
                    return;
                }
            });*/
        }

        /*

        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchVersion.setAdapter(adapter5);
        */
       // adapter = new CustomAdapter
        //searchList.
    }

    private class ExportDatabaseTable extends AsyncTask<Void, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(SearchBible.this);
        private final Context context;
        private int byGetOrPost = 0;
        private TextView sresult;
        ListView lv1;

        public ExportDatabaseTable(Context context,ListView lv1,int flag) {
            this.context = context;
            byGetOrPost = flag;
            this.lv1 = lv1;
        }

        // can use UI thread here
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Searching bible...");
            dialog.show();
        }

        // automatically done on worker thread (separate from UI thread)
        @Override
        protected Boolean doInBackground(final Void... args) {

            boolean testx = false;
            try{
                myDbHelper.setSearchResults(strSearch, strParams, strVersion);
                testx = true;
            } catch (Exception ex) {
                // LOG or output exception
                System.out.println(ex);
                testx = false;
            }
            finally {

            }
            return testx;
        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (success) {
                final String[][] returnVerses = myDbHelper.getSearchResults();
                //ArrayAdapter<String> adapter;
                //adapter = null;
                ArrayList<String> myStringArray1 = new ArrayList<String>();
                for (int i = 0; i < returnVerses[0].length; i++) {
                    if (boolShow)
                    {
                        myStringArray1.add(returnVerses[1][i] + " " + returnVerses[0][i]);
                    }
                    else
                    {
                        myStringArray1.add(returnVerses[0][i]);
                    }
                }
                if (boolShow) {
                    ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, myStringArray1) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                            text1.setText(returnVerses[1][position]);
                            text1.setTextSize(14);
                            text2.setText(returnVerses[0][position]);
                            text2.setTextSize(12);
                            return view;
                        }
                    };

                    searchList.setAdapter(adapter);
                }
                else{
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, myStringArray1);
                    searchList.setAdapter(adapter2);
                }
                searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (!strVersion.equals("Strong")) {
                            String text4 = "";
                            try {
                                TextView text3 = (TextView) view.findViewById(android.R.id.text2);
                                text4 = (String) text3.getText();
                            } catch (Exception ex) {
                                text4 = (String) searchList.getItemAtPosition(position);
                            }
                            String[] splitText = text4.split(" ");
                            String book;
                            String[] verseCombo;
                            String chapter;
                            String verse;
                            String version;
                            if (splitText.length == 5) {
                                book = splitText[0] + " " + splitText[1];
                                verseCombo = splitText[2].split(":");
                                chapter = verseCombo[0];
                                verse = verseCombo[1];
                                version = splitText[4];
                            } else {
                                book = splitText[0];
                                verseCombo = splitText[1].split(":");
                                chapter = verseCombo[0];
                                verse = verseCombo[1];
                                version = splitText[3];
                            }
                            //Toast.makeText(SearchBible.this, text4.length() + "," + book + "," + chapter + "," + verse + "," + version, Toast.LENGTH_SHORT).show();
                            myDbHelper.getSave();
                            String auto = myDbHelper.saveAuto;
                            boolean hint = myDbHelper.saveHint;
                            myDbHelper.setSave(book, Integer.parseInt(chapter), Integer.parseInt(verse), version, auto, hint);
                            finish();
                            return;
                        }
                    }
                });
            } else {
                Toast.makeText(SearchBible.this, "Search failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class VersionOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            version = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }



    }
