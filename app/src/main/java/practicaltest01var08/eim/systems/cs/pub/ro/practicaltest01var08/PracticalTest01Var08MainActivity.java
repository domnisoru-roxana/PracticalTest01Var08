package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {

    Button playButton;
    EditText riddle_text;
    EditText answer_text;


    class OnCLkList implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.play_button:
                    String riddle = riddle_text.getText().toString();
                    String answer = answer_text.getText().toString();
                    if (riddle != null && riddle.length() > 0 && answer != null && answer.length() > 0) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var08PlayActivity.class);
                        intent.putExtra(Constants.RIDDLE_TAB, riddle);
                        intent.putExtra(Constants.ANSWER_TAB, answer);
                        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    }
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_main);

        playButton = findViewById(R.id.play_button);
        riddle_text = findViewById(R.id.riddle_edit_text);
        answer_text = findViewById(R.id.answer_edit_text);

        OnCLkList lsn = new OnCLkList();
        playButton.setOnClickListener(lsn);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.RIDDLE_TAB)) {
                riddle_text.setText(savedInstanceState.getString(Constants.RIDDLE_TAB));
            } else {
                riddle_text.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey(Constants.ANSWER_TAB)) {
                riddle_text.setText(savedInstanceState.getString(Constants.ANSWER_TAB));
            } else {
                answer_text.setText(String.valueOf(0));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.RIDDLE_TAB, riddle_text.getText().toString());
        savedInstanceState.putString(Constants.ANSWER_TAB, answer_text.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.RIDDLE_TAB)) {
            riddle_text.setText(savedInstanceState.getString(Constants.RIDDLE_TAB));
        }
        if (savedInstanceState.containsKey(Constants.ANSWER_TAB)) {
            answer_text.setText(savedInstanceState.getString(Constants.ANSWER_TAB));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
