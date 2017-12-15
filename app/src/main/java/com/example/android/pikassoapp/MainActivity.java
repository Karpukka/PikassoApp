package com.example.android.pikassoapp;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import com.example.android.pikassoapp.view.PikassoView;

public class MainActivity extends AppCompatActivity {

  PikassoView pikassoView;
  private AlertDialog.Builder currentAlertDialog;
  private ImageView widthImageView;
  private AlertDialog dialogLineWidth;
private  AlertDialog colorDialog;
  private SeekBar alphaSeekBar;
  private SeekBar redSeekBar;
  private SeekBar greenSeekBar;
  private SeekBar blueSeekBar;
private View colorView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    pikassoView = findViewById(R.id.view);

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.clearId:
        pikassoView.clear();
        break;
      case R.id.saveId:
pikassoView.saveImageToInternalSterage();
        break;
      case R.id.colorId:
        showColorDialog();
        break;
      case R.id.lineWidth:
        showLineWidthDialog();
        break;
      case R.id.eraseId:
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  void showColorDialog() {
    currentAlertDialog = new AlertDialog.Builder(this);
    View view = getLayoutInflater().inflate(R.layout.color_dialog, null);
    alphaSeekBar = view.findViewById(R.id.alphaSeekBar);
    redSeekBar = view.findViewById(R.id.redSeekBar);
    greenSeekBar = view.findViewById(R.id.greenSeekBar);
    blueSeekBar = view.findViewById(R.id.blueSeekBar);
    colorView=view.findViewById(R.id.colorView);

    alphaSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
    redSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
    greenSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
    blueSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);

    int color =pikassoView.getDrawingColor();
    alphaSeekBar.setProgress(Color.alpha(color));
    redSeekBar.setProgress(Color.red(color));
    greenSeekBar.setProgress(Color.green(color));
    blueSeekBar.setProgress(Color.blue(color));

    Button setColorButton = view.findViewById(R.id.setColorButton);
    setColorButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pikassoView.setDrawingColor(Color.argb(
            alphaSeekBar.getProgress(),
            redSeekBar.getProgress(),
            greenSeekBar.getProgress(),
            blueSeekBar.getProgress()
        ));
        colorDialog.dismiss();
      }
    });
currentAlertDialog.setView(view);
currentAlertDialog.setTitle("Choose color");
colorDialog= currentAlertDialog.create();
colorDialog.show();
  }

  void showLineWidthDialog() {
    currentAlertDialog = new Builder(this);
    View view = getLayoutInflater().inflate(R.layout.width_dialog, null);
    final SeekBar widthSeekBar = view.findViewById(R.id.widthDSeekBar);
    Button setLineWidthButton = view.findViewById(R.id.widthDialogButton);
    widthImageView = view.findViewById(R.id.imageViewId);
    setLineWidthButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pikassoView.setLineWidhh(widthSeekBar.getProgress());
        dialogLineWidth.dismiss();
        currentAlertDialog = null;
      }
    });
    widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChange);
    widthSeekBar.setProgress((pikassoView.getLineWidth()));
    currentAlertDialog.setView(view);
    dialogLineWidth = currentAlertDialog.create();
    dialogLineWidth.setTitle("Set Line Width");
    dialogLineWidth.show();
  }

  private SeekBar.OnSeekBarChangeListener colorSeekBarChanged = new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      pikassoView.setBackgroundColor(Color.argb(
          alphaSeekBar.getProgress(),
          redSeekBar.getProgress(),
          greenSeekBar.getProgress(),
          blueSeekBar.getProgress()
      ));
      colorView.setBackgroundColor(Color.argb(
          alphaSeekBar.getProgress(),
          redSeekBar.getProgress(),
          greenSeekBar.getProgress(),
          blueSeekBar.getProgress()
      ));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };
  private SeekBar.OnSeekBarChangeListener widthSeekBarChange = new SeekBar.OnSeekBarChangeListener() {
    Bitmap bitmap = Bitmap.createBitmap(400, 100, Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      Paint p = new Paint();
      p.setColor(pikassoView.getDrawingColor());
      p.setStrokeCap(Cap.ROUND);
      p.setStrokeWidth(progress);

      bitmap.eraseColor(Color.WHITE);
      canvas.drawLine(30, 50, 370, 50, p);
      widthImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };
}
