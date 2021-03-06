/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_FILE_NAME = "irds_time_football";
    public static final String TIME_KEEPER_DATA_DIR = "TimeKeeper";
    private List<TimeRecord> timeList = new ArrayList<>();
    private String fileSuffix;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileSuffix = new SimpleDateFormat("yyMMddhhmmss").format(new Date());
        // change  digits to alphabets. Saw an issue with file with numbers
        StringBuilder builder = new StringBuilder("");

        for (char c : fileSuffix.toCharArray()){
            // 0-> A, 1->B
            builder.append( (char)(c + 17)  );
        }

        fileSuffix = builder.toString() + ".csv";

        String path =
                Environment.getExternalStorageDirectory() + File.separator  + TIME_KEEPER_DATA_DIR;
        // Create the folder.
        File folder = new File(path);
        folder.mkdirs();

        // Create the file.
        file = new File(folder, DATA_FILE_NAME + fileSuffix);
        boolean created;
        try
        {
            created = file.createNewFile();
        }
        catch (Exception e){
            created = false;
        }
        try
        {
            if (!created && !file.exists()){
                //Try cretaing it in app directory
                file = new File(getApplicationContext().getExternalFilesDir(null), DATA_FILE_NAME + fileSuffix);
                file.createNewFile();

            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Can't create new files " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        saveData(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData(null);
    }

    @Override
    public void finish() {
        super.finish();
        saveData(null);
    }

    private void updateArray() {
        ListView lv = (ListView) findViewById(R.id.timeGrid);

        ArrayAdapter<TimeRecord> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                timeList);

        lv.setAdapter(arrayAdapter);


    }

    /**
     * Records a new time entry
     */
    public void recordTime(View v) {
        timeList.add(new TimeRecord( new Date() ) );
        updateArray();

        if (timeList.size() % 3 == 0){
            saveData(null);
        }
    }

    /**
     * Saves all time in file
     */
    public void saveData(View v) {
        StringBuilder sb = new StringBuilder("");

        for (TimeRecord timeRecord : timeList){
            sb.append(timeRecord.fileString());
            sb.append("\n");
        }

        writeToFile(sb.toString());
    }

    /**
     * Finds external SD and writes data to a CSV file
     */
    public void writeToFile(String data)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text ;

        try
        {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();

            text = "Saved at " + file.getAbsolutePath();

        }
        catch (Exception e)
        {
            text = "Error while saving- " + e.getMessage();
        }


        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
