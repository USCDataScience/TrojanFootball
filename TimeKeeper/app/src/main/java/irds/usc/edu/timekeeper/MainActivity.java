package irds.usc.edu.timekeeper;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    List<TimeRange> timeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void updateArray() {
        lv = (ListView) findViewById(R.id.timeGrid);

        ArrayAdapter<TimeRange> arrayAdapter = new ArrayAdapter<TimeRange>(
                this,
                android.R.layout.simple_list_item_1,
                timeList);

        lv.setAdapter(arrayAdapter);

    }

    public void addBegin(View v) {
        timeList.add(new TimeRange( new Date() ) );
        updateArray();
    }

    public void addEnd(View v) {
        //Get last element
        TimeRange timeRange = timeList.get(timeList.size() - 1);
        if ( timeRange != null && timeRange.getEnd() == null ){
            // if there is atleast one entry AND
            //if start is there but no end, update end
            timeRange.setEnd(new Date());
        }else{
            //if end is there add new record
            timeRange = new TimeRange( new Date() );
            timeRange.setEnd( new Date() );
            timeList.add( timeRange );
        }

        updateArray();
    }

    public void saveData(View v) {
        StringBuffer sb = new StringBuffer("");

        for (TimeRange timeRange : timeList){
            sb.append(timeRange.fileString());
            sb.append("\n");
        }

        writeToFile(sb.toString());
    }

    public void writeToFile(String data)
    {
        String path =
                Environment.getExternalStorageDirectory() + File.separator  + "TimeKeeper";
        // Create the folder.
        File folder = new File(path);
        folder.mkdirs();

        // Create the file.
        File file = new File(folder, "irds_time_football.txt");

        // Save your stream, don't forget to flush() it before closing it.

        CharSequence text = "Something unexpected happen!";
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();

            text = "Saved at " + file.getAbsolutePath();

        }
        catch (Exception e)
        {
            text = "Saved at " + file.getAbsolutePath();
        }

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
