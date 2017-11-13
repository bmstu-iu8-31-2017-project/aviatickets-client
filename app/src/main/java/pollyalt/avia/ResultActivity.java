package pollyalt.avia;




import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.app.ListActivity;



public class ResultActivity /*extends AppCompatActivity*/ extends ListActivity{

    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String text = intent.getStringExtra("out");

        list = Parse(text);

      //  ListView Output = (ListView)findViewById(R.id.output);

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

    ArrayList<String> Parse(String str)
    {
        ArrayList<String> list = new ArrayList<>();
        while(!str.isEmpty())
        {
            int pos = str.indexOf("|");
            list.add(str.substring(0, pos - 1).replace('&', ' '));
            if(pos != str.length() - 1)
                str = str.substring(pos + 1);
            else str = "";
        }
        return list;
    }





}
