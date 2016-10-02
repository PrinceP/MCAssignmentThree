package com.iiitd.prince.graphgenerator;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleActivity extends AppCompatActivity {
    private XYPlot plot;

    private DataBaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_activity);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        List<Number> series1Numbers = new ArrayList<Number>();
        series1Numbers.add(0);
        dbManager = new DataBaseManager(this);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.fetch();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){

            String title = cursor.getString(cursor.getColumnIndex("valuex"));
            int i = Integer.parseInt(title);
            series1Numbers.add(i);
            cursor.moveToNext();
        }



        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(series1Numbers,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Data");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_labels);

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }

    public void  saveInternal(View view){
        String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
        String fileName = "/PLOT"+timeStamp+".png";

        plot.setDrawingCacheEnabled(true);
        int width = plot.getWidth();
        int height = plot.getHeight();
        plot.measure(width, height);
        Bitmap bmp = Bitmap.createBitmap(plot.getDrawingCache());
        plot.setDrawingCacheEnabled(false);
        FileOutputStream fos = null;
        Context context = getApplicationContext();
        File file = new File(context.getFilesDir(), fileName);
        Log.d("E",context.getFilesDir()+fileName);
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        try {
            fos.close();
            Toast.makeText(this, R.string.Success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void  saveExternal(View view){
        String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
        String fileName = "/PLOT"+timeStamp+".png";

        String baseFolder = "";
        // check if external storage is available
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFolder = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        }
        else {
            Toast.makeText(this, R.string.Error, Toast.LENGTH_SHORT).show();
        }



        plot.setDrawingCacheEnabled(true);
        int width = plot.getWidth();
        int height = plot.getHeight();
        plot.measure(width, height);
        Bitmap bmp = Bitmap.createBitmap(plot.getDrawingCache());
        plot.setDrawingCacheEnabled(false);
        FileOutputStream fos = null;
        Log.d("E", baseFolder+fileName);
        File file = new File(baseFolder + fileName);
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        try {
            fos.close();
            Toast.makeText(this, R.string.Success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
