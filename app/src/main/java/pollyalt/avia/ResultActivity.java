package pollyalt.avia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.app.ListActivity;

/**
 * Класс для создания окна вывода полученных рейсов с сервера
 */
public class ResultActivity extends ListActivity{

    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    /**
     * Метод для создания окна и вывода данных
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String text = intent.getStringExtra("out");

        list = Parse(text);

        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        Button Back = (Button) findViewById(R.id.GetBack);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    /**
     * Метод разделяет полученные данные
     * @param str Полученная строка с сервера
     * @return ArrayList данных
     */
    ArrayList<String> Parse(String str)
    {
        ArrayList<String> list = new ArrayList<>();
        while(!str.isEmpty())
        {
            int pos = str.indexOf("|");
            if(pos != -1)
            {
                list.add(str.substring(0, pos).replace(" ", "").replace("&", " - "));
                if (pos != str.length() - 1)
                    str = str.substring(pos + 1);
                else str = "";
            }
            else str = "";
        }
        if(list.isEmpty())
        {
            list.add("Sorry! No such flights on this period:(");
        }
        else {
            list.add(0, "Flight num - Date - Time  - Price \nDeparture - Destination");
            list.add(1, "\n");
        }
        return list;
    }
}
