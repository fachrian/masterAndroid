package fachrian.fachrian_library.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import fachrian.fachrian_library.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class GlideMaster {
    public static int CROP_CIRCLE = 1;
    public static int CENTER_CROP = 2;
    public static int CROP_SQUARE = 3;

    public static Transformation<Bitmap> transformation(Context context, int stat) {

        if (stat == CROP_CIRCLE) {
            return new CropCircleTransformation(context);
        } else if (stat == CENTER_CROP) {
            return new CenterCrop(context);
        } else if (stat == CROP_SQUARE) {
            return new CropSquareTransformation(context);
        } else {
            return null;
        }

    }

    public static void setImage(Context context, ImageView imageView, String URL, Transformation<Bitmap> transformationt) {
        try {
            Activity activity = (Activity) context;
            if (!activity.isDestroyed()) {
                Glide.with(activity)
                        .load(URL)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                        .override(size, size)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .bitmapTransform(transformationt)
//                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
