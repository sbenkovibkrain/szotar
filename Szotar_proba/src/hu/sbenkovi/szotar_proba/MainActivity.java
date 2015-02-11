package hu.sbenkovi.szotar_proba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	//test commit 2
	private Button btnStart;
	private Button btnNewWord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View view = findViewById(R.id.main_entire);
		view.setOnTouchListener(new OnSwipeTouchListener(this) {
			@Override
			public void onSwipeRight() {
				showNewWordActivity();
			}

			@Override
			public void onSwipeLeft() {
				showQuestionerActivity();
			}
		});
		btnStart = (Button) findViewById(R.id.btnStart);
		btnNewWord = (Button) findViewById(R.id.btnNewWord);

	}

	public void start(View v) {
		showQuestionerActivity();
	}

	public void addNewWord(View v) {
		showNewWordActivity();
	}

	private void showQuestionerActivity() {
		startActivity(new Intent(this, QuestionerActivity.class));
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	private void showNewWordActivity() {
		startActivity(new Intent(this, NewWordActivity.class));
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	private void alert() {
		if(getSharedPreferences("NODE", 0).getBoolean("SHOW", true)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Udv!").setTitle("Inditas").setCancelable(false)
			        .setPositiveButton("OK", new OnClickListener() {

				        @Override
				        public void onClick(DialogInterface dialog, int which) {
					        getSharedPreferences("NODE", 0).edit().putBoolean("SHOW", false).commit();
				        }
			        });
			//builder.setCancelable(true) ==>modal or not
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
