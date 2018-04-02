package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var08PlayActivity extends AppCompatActivity {

    Button checkButton;
    Button backButton;
    EditText yourAnswertext;
    TextView viewRiddle;
    TextView viewAnswer;
    IntentFilter intentFilter = new IntentFilter();
    MyBcReceiver myBcReceiver = new MyBcReceiver();
    boolean intentStarted = false;

    class MyBcReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA), Toast.LENGTH_LONG).show();
            }
        }

    class OnClickLsn implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.check_button:
                    String yourAnswer = yourAnswertext.getText().toString();
                    String correctAnswer = viewAnswer.getText().toString();

                    if (yourAnswer.equals(correctAnswer)) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "InCorrect", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.back_button:
                    setResult(RESULT_CANCELED, null);
                    finish();
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_play);

        OnClickLsn onClickLsn = new OnClickLsn();

        checkButton = findViewById(R.id.check_button);
        backButton = findViewById(R.id.back_button);

        yourAnswertext = findViewById(R.id.attemp_text);
        viewRiddle = findViewById(R.id.riddle_play_text);
        viewAnswer = findViewById(R.id.correct_answer_text);

        checkButton.setOnClickListener(onClickLsn);
        backButton.setOnClickListener(onClickLsn);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras().containsKey(Constants.RIDDLE_TAB)) {
                viewRiddle.setText(intent.getStringExtra(Constants.RIDDLE_TAB));
            }

            if (intent.getExtras().containsKey(Constants.ANSWER_TAB)) {
                viewAnswer.setText(intent.getStringExtra(Constants.ANSWER_TAB));
            }
        }
        intentFilter.addAction(Constants.actionTypes[0]);

        if (!intentStarted) {
            Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var08Service.class);
            serviceIntent.putExtra(Constants.ANSWER_TAB, viewAnswer.getText().toString());
            startService(serviceIntent);
            intentStarted = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBcReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(myBcReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var08Service.class);
        stopService(intent);
        intentStarted = false;
        super.onDestroy();
    }
}
