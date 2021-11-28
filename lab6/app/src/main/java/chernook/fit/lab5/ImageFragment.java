package chernook.fit.lab5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ImageFragment extends Fragment {
    private View view;
    private Context context;
    private Bitmap startImage = null;
    private int ROLL_COUNT = 3;
    private Bitmap[] roll = new Bitmap[ROLL_COUNT];
    private int currentRollId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);
        loadImages();
        ImageView imageView = (ImageView)view.findViewById(R.id.fragment_goods_image);
        imageView.setImageBitmap(startImage);

        Animation less = AnimationUtils.loadAnimation(context, R.anim.less);
        less.setAnimationListener(new Animation.AnimationListener(){
            public void onAnimationStart(Animation animation){}
            public void onAnimationRepeat(Animation animation){}
            public void onAnimationEnd(Animation animation){
                imageClick();
            }
        });
        imageView.setOnClickListener(view -> imageView.startAnimation(less));

        return view;
    }

    void setStartImage(Bitmap image) {
        startImage = image;
    }

    private void loadImages() {
        Drawable image1 = getResources().getDrawable( R.drawable.airpods);
        Drawable image2 = getResources().getDrawable( R.drawable.airpods2);
        Drawable image3 = getResources().getDrawable( R.drawable.airpods3);

        roll[0] = ((BitmapDrawable)image1).getBitmap();
        roll[1] = ((BitmapDrawable)image2).getBitmap();
        roll[2] = ((BitmapDrawable)image3).getBitmap();
    }

    private void imageClick() {
        currentRollId = (currentRollId + 1) % ROLL_COUNT;
        ImageView imageView = (ImageView)view.findViewById(R.id.fragment_goods_image);
        imageView.setImageBitmap(roll[currentRollId]);
    }
}