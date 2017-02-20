package fachrian.fachrian_library.lib;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class CameraMaster {
    int CAMERA_PIC_REQUEST = 2;
    int TAKE_PHOTO_CODE = 0;
    int CAMERA_PHOTO = 111;
    Context context;
    Uri temp_image;

    public CameraMaster(Context context) {
        this.context = context;
    }

    public void cameraOpenUri() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";

        File f = new File(Environment.getExternalStorageDirectory(), imageFileName);
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        temp_image = Uri.fromFile(f);
        ((Activity) context).startActivityForResult(chooserIntent, CAMERA_PHOTO);

    }

    public Bitmap getBitmapFromUri(int requestCode, int resultCode) {
        if (requestCode == CAMERA_PHOTO && resultCode == ((Activity) context).RESULT_OK) {
            if (temp_image != null) {
                context.getContentResolver().notifyChange(temp_image, null);

                Bitmap reducedSizeBitmap = getBitmap(temp_image.getPath());
                if (reducedSizeBitmap != null) {
                    return reducedSizeBitmap;
                }
            }
        }
        LogMaster.display(context, "Picture not taken");
        return null;
    }

//    private String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }


    public void cameraOpen() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }


    public Uri getUri(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            return getImageUri(context, bitmap);
        }
        LogMaster.display(context, "Picture not taken");
        return null;
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = context.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = context.getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }
//    public Bitmap getBitmap(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
//            return (Bitmap) data.getExtras().get("data");
//        }
//        LogController.display(context, "Picture not taken");
//        return null;
//
//    }

    public Bitmap getBitmapImageFromUri(Activity activity, Uri uri) {
        activity.getContentResolver().notifyChange(uri, null);
        ContentResolver cr = activity.getContentResolver();
        try {
            return MediaStore.Images.Media.getBitmap(cr, uri);
        } catch (Exception e) {
            return null;
        }
    }

    public String getBase64(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = context.getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                return getImageStringFromBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String getImageStringFromBitmap(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);//quality 1-100
        byte[] inputData = stream.toByteArray();
        return Base64.encodeToString(inputData, Base64.DEFAULT);
    }

    private String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }

}
