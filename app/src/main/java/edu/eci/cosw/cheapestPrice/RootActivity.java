package edu.eci.cosw.cheapestPrice;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 2105533 on 5/8/17.
 */

public class RootActivity extends AppCompatActivity {
    int onStartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_left_to_right,
                    R.anim.anim_slide_right_to_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_left_to_right,
                    R.anim.anim_slide_right_to_left);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }
}
