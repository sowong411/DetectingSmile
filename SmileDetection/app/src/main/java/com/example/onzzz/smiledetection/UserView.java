package com.example.onzzz.smiledetection;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.Color;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;



/**
 * Created by onzzz on 9/10/2015.
 */
public class UserView extends View{

    private Bitmap tempBitmap;
    private SparseArray<Face> tempFaces;

    public UserView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    void setContent(Bitmap bitmap, SparseArray<Face> faces){
        tempBitmap = bitmap;
        tempFaces = faces;
        invalidate();
    }

    private double drawBitmap(Canvas canvas){
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = tempBitmap.getWidth();
        double imageHeight = tempBitmap.getHeight();
        double scale = Math.min(viewWidth/imageWidth, viewHeight/imageHeight);
        Rect destBounds = new Rect(0, 0, (int)(imageWidth*scale), (int) (imageHeight * scale));
        canvas.drawBitmap(tempBitmap, null, destBounds, null);
        return scale;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if ((tempBitmap!=null) && (tempFaces!=null)){
            double scale = drawBitmap(canvas);
            drawRectangle(canvas, scale);
            detectSmile(canvas, scale);
            drawCircle(canvas, scale);
        }
    }

    private void drawRectangle(Canvas canvas, double scale){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        for (int i=0; i<tempFaces.size(); i++){
            Face face = tempFaces.valueAt(i);
            canvas.drawRect((float)(face.getPosition().x*scale), (float)(face.getPosition().y*scale), (float)((face.getPosition().x+face.getWidth())*scale), (float)((face.getPosition().y+face.getHeight())*scale), paint);
        }
    }

    private void detectSmile(Canvas canvas, double scale){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setTextSize(40.0f);
        for (int i=0; i<tempFaces.size(); i++){
            Face face = tempFaces.valueAt(i);
            float cx = (float)(face.getPosition().x*scale);
            float cy = (float)(face.getPosition().y * scale);
            canvas.drawText(String.valueOf(face.getIsSmilingProbability()), cx, cy+10.0f, paint);
        }
    }

    private void drawCircle(Canvas canvas, double scale){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (int i=0; i<tempFaces.size(); i++){
            Face face = tempFaces.valueAt(i);
            for (Landmark landmark: face.getLandmarks()){
                int cx = (int)(landmark.getPosition().x*scale);
                int cy = (int)(landmark.getPosition().y*scale);
                canvas.drawCircle(cx, cy, 10, paint);
            }
        }
    }







}
