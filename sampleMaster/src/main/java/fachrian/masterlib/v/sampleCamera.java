package fachrian.masterlib.v;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import fachrian.fachrian_library.lib.CameraMaster;
import fachrian.masterlib.R;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class sampleCamera extends AppCompatActivity {

    CameraMaster cameraController;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = cameraController.getBitmapFromUri(requestCode, resultCode);
        if (bitmap != null) {

            final ImageView foto = (ImageView) findViewById(R.id.cast_notification_id);
            String base64 = cameraController.getImageStringFromBitmap(bitmap);
            foto.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Foto Harus disertakan!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraController = new CameraMaster(this);

    }
}
