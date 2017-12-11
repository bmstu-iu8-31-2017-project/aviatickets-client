package pollyalt.avia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Класс для создания окна вывода полученных рейсов с сервера
 */
public class ResultActivity extends Activity {

    private ArrayList<JSONObject> ListOb = new ArrayList<>();
    private ListAdapter Adapter;
    private ImageView CityImage;
    private TextView Cities;
    private TextView Countries;
    private ListView Output;
    private ViewGroup HeaderView;
    private Button Back;

    /**
     * Метод для создания окна и вывода данных
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String text = intent.getStringExtra("out");

        ListOb = Parse(text);

        if(!ListOb.isEmpty()) {
            Cities = (TextView) findViewById(R.id.cities);
            Countries = (TextView) findViewById(R.id.counties);

            Output = (ListView) findViewById(R.id.listv);
            HeaderView = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, Output, false);
            Output.addHeaderView(HeaderView);

            CityImage = (ImageView) findViewById(R.id.DestImage);

            Adapter = new ListAdapter(this, R.layout.list_row, R.id.flight, ListOb);
            Output.setAdapter(Adapter);

            try {
                SetImage(ListOb.get(0).getString("city_to"));
                Cities.setText(ListOb.get(0).getString("city_from") + " → " + ListOb.get(0).getString("city_to"));
                Countries.setText(ListOb.get(0).getString("country_from") + " → " + ListOb.get(0).getString("country_to"));
            } catch (Exception e) {
                finish();
            }
        }
        else {
            Countries = (TextView) findViewById(R.id.counties);
            Countries.setText(R.string.NoFlights);
            Countries.setTextColor(Color.BLACK);
        }
        Back = (Button) findViewById(R.id.GetBack);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Метод разделяет полученные данные
     *
     * @param str Полученная строка с сервера
     * @return ArrayList данных
     */
    private ArrayList<JSONObject> Parse(String str) {
        ArrayList<JSONObject> list = new ArrayList<>();
        str = str.replace(" ", "");
        try {
            JSONArray jArray = new JSONArray(str);
            for (int i = 0; i < jArray.length(); i++) {
                String tmp = jArray.getJSONObject(i).getString("date_flight");
                int t = tmp.indexOf('T');
                if (t > 0){
                    tmp = tmp.substring(0, tmp.indexOf('T'));
                    jArray.getJSONObject(i).put("date_flight", tmp);
                }
                list.add(jArray.getJSONObject(i));
            }
        }catch(Exception e) {
            //
        }
        return list;
        }

    private void SetImage(String str)
    {
        str = str.substring(1);
        int id = getResources().getIdentifier("pollyalt.avia:drawable/" + str, null, null);
        CityImage.setBackgroundResource(id);
    }

}

