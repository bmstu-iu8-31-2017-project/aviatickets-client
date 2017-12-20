package pollyalt.avia;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import java.util.Calendar;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Операция(Activity) для создания главного окна
 */
public class MainActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText From;
    private EditText Where;
    private EditText WhenT;
    private ImageButton Look;
    private String When = new String();
    private int Year, Month, Day;

    /**
     * Метод для инициализации ключевых компонентов операции и обработки данных
     * Вызывается при создании окна
     * @param savedInstanceState Сохраненные данные для восстановления предыдущего состояния окна
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       From = (EditText) findViewById(R.id.FromText);
       Where = (EditText) findViewById(R.id.WhereText);
       WhenT = (EditText)findViewById(R.id.WhenText);
       Look = (ImageButton) findViewById(R.id.LookBut);

       Look.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(From.getText().toString().isEmpty() || Where.getText().toString().isEmpty() || When.isEmpty())
                AlertD("Wrong format");
                else
                {
                    String[] req = new String[]
                        {From.getText().toString(), Where.getText().toString(), When};
                try {
                    Handle thr = new Handle();
                    thr.execute(StringToRequest(req));
                    String get = thr.get();
                    if (!get.equals("0"))
                        launchActivity(get);
                } catch (Exception e) {
                    AlertD("Error");
                }
            }
            }
        });

    }

    /**
     * Метод вывода окна Календаря и получения выбранной даты
     * @param view Компонента, которая вызывает метод
     */
    public void setDate(View view) {
        if(When.isEmpty())
            myCalendar = Calendar.getInstance();
        else
            myCalendar.set(Year, Month, Day);
        OnDateSetListener date = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Year = year;
                Month = monthOfYear;
                Day = dayOfMonth;
                When = Integer.toString(Year) +  "-" + Integer.toString(Month + 1) + "-"
                        + Integer.toString(Day);
                WhenT.setText(When);
            }
        };
        new DatePickerDialog(MainActivity.this, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Метод запуска окна Результата
     * @param extra Строка полученных данных с сервера
     */
    private void launchActivity(String extra) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("out", extra);
        startActivity(intent);
    }

    /**
     * Класс служит для создания асинхронного потока для подключения к серверу
     */
    private class Handle extends AsyncTask<String, Void, String> {
        /**
         * Метод служит для создания подключения
         * @param arg Строка параметров для запроса на сервер
         * @return Строка полученных данных с сервера
         */
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
               AlertD("No connection");
            }
            super.onPostExecute(result);
        }
    }

    /**
     * Метод для преобразования строки в запрос
     * @param params Запрашиваемые параметры, введенные пользователем
     * @return Строка для отправления запроса на сервер
     */
    private String StringToRequest(String[] params) {
        String str = "/?from=" + params[0] + "&where=" + params[1] + "&when=" + params[2];
        return str;
    }

    /**
     * Метод для создания окна сообщений об ошибках
     * @param msg Строка - сообщение, которое будет выведено в окне
     */
    private void AlertD(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}





