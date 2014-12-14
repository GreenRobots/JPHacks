package im.ene.androooid.jphacks;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;

import im.ene.androooid.jphacks.callback.WearSensorCallback;
import im.ene.androooid.jphacks.utils.WearSensorUtil;

/**
 * Activity is called if user only wants video on phone
 */
public class VideoPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, WearSensorCallback {

    private static final String[] hardVideos =
            {"http://www.googledrive.com/host/0B5oyJCoT20suTzhqdkY4V29Hczg/bootyshaking.mp4",
                    "http://www.googledrive.com/host/0B5oyJCoT20suTzhqdkY4V29Hczg/victoriasecret.mp4",
                    "http://www.googledrive.com/host/0B5oyJCoT20suTzhqdkY4V29Hczg/video1.mp4"};
    private static final String easyVideo = "http://www.googledrive.com/host/0B5oyJCoT20suTzhqdkY4V29Hczg/bootyshaking.mp4";
    private static String videoToLoad;

    Random r;
    final int Low = 0;
    final int High = 2;

    private final String APIKEY = "AIzaSyBMP9t4pFD0xtux5nSnQBXwMRRqdEOE2CY";
    private YouTubePlayerView youTubePlayerView;

    private ImageView mImageHeart;

    private TextView mHeartBeat;

    private WearSensorUtil mWearSensorUtil;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtube_layout);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayerView);
        youTubePlayerView.initialize(APIKEY, this);

        mImageHeart = (ImageView) findViewById(R.id.image_heart);
        Animation heartFlashAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_flash);
        mImageHeart.startAnimation(heartFlashAnimation);

        mHeartBeat = (TextView) findViewById(R.id.text_heart_beat);

        mWearSensorUtil = new WearSensorUtil(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWearSensorUtil.setCallback(this);
        mWearSensorUtil.resume();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo("PqJNc9KVIZE");
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onHeartRateChanged(float heartRate) {
        Log.d("", "heart rate:" + heartRate);
        mHeartBeat.setText(Float.toString(heartRate));
        if (heartRate > 100) //TODO: change this number to be more suitable
        {

            final Dialog dialog = new Dialog(this);
            dialog.setTitle("We Noticed You have Heart rate > 100...");
            dialog.setContentView(R.layout.load_new_video_dialog);

            TextView textView = (TextView) dialog.findViewById(R.id.show_easy_or_hard_video_textView);
            textView.setText("Want to show easier video?");

            Button confirmButton = (Button) dialog.findViewById(R.id.confirm);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // use easyvideo
                    videoToLoad = easyVideo;
                    dialog.dismiss();
                    recreate();
                }
            });
            Button declineButton = (Button) dialog.findViewById(R.id.decline);
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (heartRate < 80) //TODO: change this number to be more suitable
        {

            final Dialog dialog = new Dialog(this);
            dialog.setTitle("We Noticed You have Heartrate < 80...");
            dialog.setContentView(R.layout.load_new_video_dialog);

            TextView textView = (TextView) dialog.findViewById(R.id.show_easy_or_hard_video_textView);
            textView.setText("Want to show harder video?");

            Button confirmButton = (Button) dialog.findViewById(R.id.confirm);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show one of the harder videos
                    int randomIndex = r.nextInt(High - Low) + Low;
                    videoToLoad = hardVideos[randomIndex];
                    dialog.dismiss();
                    recreate();
                }
            });
            Button declineButton = (Button) dialog.findViewById(R.id.decline);
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onStepDetected(int sumOfSteps) {

    }
}
