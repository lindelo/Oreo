package imy.oreo.nancy;

import android.content.Intent;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;


public class TourActivity extends AppIntro {

    @Override
    public void init(Bundle bundle) {

        addSlide(TourFragment.newInstance(R.layout.introslide1));
        addSlide(TourFragment.newInstance(R.layout.introslide2));
        addSlide(TourFragment.newInstance(R.layout.introslide3));
        addSlide(TourFragment.newInstance(R.layout.introslide4));
    }

    @Override
    public void onSkipPressed() {
        goToMainActivity();
    }

    @Override
    public void onDonePressed() {
        goToMainActivity();

    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
