package pollyalt.avia;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.CheckBox;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

import java.util.Calendar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity  {

    Calendar myCalendar = Calendar.getInstance();
    EditText From;
    EditText Where;
    EditText WhenT;
    ImageButton Look;
    CheckBox Month;
    String When = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        From = (EditText) findViewById(R.id.FromText);
        Where = (EditText) findViewById(R.id.WhereText);
        WhenT = (EditText)findViewById(R.id.WhenText);
        Month = (CheckBox) findViewById(R.id.month);
        Look = (ImageButton) findViewById(R.id.LookBut);

        Look.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Month.isChecked())
                {
                    When = When.substring(0, When.lastIndexOf('-'));
                    When += '%';
                }
                String [] req = new String[]
                        {From.getText().toString(), Where.getText().toString(), When};
                Handle thr = new Handle();
                try
                {
                    thr.execute(getPostDataString(req));
                }
                catch(Exception e)
                {
                    //
                }
                try{
                    String get = thr.get();
                    if(get != "0")
                        launchActivity(get);
                }
                catch (Exception e)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setMessage("Error");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

    }


    public void setDate(View view)
    {
        OnDateSetListener date = new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                When = Integer.toString(year) +  "-" + Integer.toString(monthOfYear + 1) + "-"
                        + Integer.toString(dayOfMonth);
                WhenT.setText(When);
            }
        };
        new DatePickerDialog(MainActivity.this, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    private void launchActivity(String extra) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("out", extra);
        startActivity(intent);
    }


    private class Handle extends AsyncTask<String, Void, String> {
       @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg) {

           Connect con = new Connect();
            String ret;
            try {
                ret = con.ConnectToServer(arg);
            }
            catch(Exception e) {
                ret = "0";
            }

            return ret;
        }


        @Override
        protected void onPostExecute(String result) {
            if(result == "0")
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setMessage("No connection");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            super.onPostExecute(result);
        }
    }


    private String getPostDataString(String[] params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for(String entry : params){
            result.append("&");
            result.append(URLEncoder.encode(entry, "UTF-8"));
        }

        return result.toString();
    }
}





