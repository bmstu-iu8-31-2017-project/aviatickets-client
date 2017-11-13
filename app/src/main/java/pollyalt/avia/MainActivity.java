package pollyalt.avia;


import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Map;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton Look = (ImageButton) findViewById(R.id.LookBut);

        Look.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText From = (EditText) findViewById(R.id.FromText);
                EditText Where = (EditText) findViewById(R.id.WhereText);
                EditText When = (EditText) findViewById(R.id.WhenText);
                String Froms = From.getText().toString();
                String Wheres = Where.getText().toString();
                String Whens = When.getText().toString();
                String [] req = new String[]{Froms, Wheres, Whens};
                Handle thr = new Handle();
                try
                {
                    thr.execute(getPostDataString(req));
                }
                catch(Exception e)
                {
                    //
                }
                //  pb.setVisibility(ProgressBar.VISIBLE);
                // pb.setVisibility(ProgressBar.INVISIBLE);
                try{
                    if(thr.get() != "0")
                        launchActivity(thr.get());
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
             //  String str = "Moscow&Pregue&24.01&12:20&R3444|Vena&London&7.9&13:19&YO90|" +
              //         "Moscow&Pregue&24.01&12:20&R3444|Vena&London&7.9&13:19&YO90|";
             //  launchActivity(str);
            }
        });

    }



    private void launchActivity(String extra) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("out", extra);
        startActivity(intent);
    }


    private class Handle extends AsyncTask<String, Void, String> {
   //     ProgressBar pb = (ProgressBar) findViewById(R.id.progress);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
     //       pb.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected String doInBackground(String... arg) {

           Connect con = new Connect();
            String ret = new String();
            try {
                ret = con.ConnectToServer(arg);
            }
            catch(Exception e) {
                ret = "0";
            }

            return ret;
        }

   //     @Override
   //     protected void onProgressUpdate(Integer... values) {
  //          pb.setProgress(values[0]);
   //     }

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
        boolean first = true;
        for(String entry : params){
            result.append("&");
            result.append(URLEncoder.encode(entry, "UTF-8"));
        }

        return result.toString();
    }
}





