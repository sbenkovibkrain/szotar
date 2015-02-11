package hu.sbenkovi.szotar_proba;

import hu.sbenkovi.szotar_proba.db.DictionaryDBAdapter;
import hu.sbenkovi.szotar_proba.db.Message;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestionerActivity extends Activity {

	private DictionaryDBAdapter dictionaryAdapter;
	private Map<String, String> words;
	private Button btnNext;
	private TextView tvqWord;
	private EditText etaWord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questioner);
		btnNext = (Button) findViewById(R.id.btnNext);
		tvqWord = (TextView) findViewById(R.id.tvqWord);
		etaWord = (EditText) findViewById(R.id.etaWord);
		dictionaryAdapter = new DictionaryDBAdapter(this);
		words = dictionaryAdapter.getAllData();
		getNextWord(null);
	}

	public void getNextWord(View v) {
		if(words.isEmpty()) {
			Message.message(this, "Jelenleg nincsenek szavak az adatbazisban.");
			return;
		}

	}

	public void check(View v) {

	}
}
