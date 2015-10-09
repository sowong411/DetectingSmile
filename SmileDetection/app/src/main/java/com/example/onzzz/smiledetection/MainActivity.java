package com.example.onzzz.smiledetection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.widget.TextView;

import java.io.InputStream;

import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

public class MainActivity extends Activity {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream stream = getResources().openRawResource(R.raw.image);
        Bitmap bitmap = BitmapFactory .decodeStream(stream);
        FaceDetector detector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).setClassificationType(FaceDetector.ALL_CLASSIFICATIONS).setLandmarkType(FaceDetector.ALL_LANDMARKS).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        TextView faceCount = (TextView) findViewById(R.id.face_count);
        faceCount.setText(faces.size() + "detected faces!");

        UserView overlay = (UserView) findViewById(R.id.userView);
        overlay.setContent(bitmap, faces);

        detector.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
