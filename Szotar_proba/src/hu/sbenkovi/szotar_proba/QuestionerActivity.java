package hu.sbenkovi.szotar_proba;

import hu.sbenkovi.szotar_proba.db.DictionaryDBAdapter;
import hu.sbenkovi.szotar_proba.db.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestionerActivity extends Activity {

	private DictionaryDBAdapter dictionaryAdapter;
	private Button btnNext;
	private Button btnCheck;
	private TextView tvqWord;
	private EditText etaWord;
	private Map<String, String> words;
	private List<Map.Entry<String, String>> randomWords;
	private int location = 0;
	private int wordCount;
	private String currSol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questioner);
		btnCheck = (Button) findViewById(R.id.btnCheck);
		btnNext = (Button) findViewById(R.id.btnNext);
		tvqWord = (TextView) findViewById(R.id.tvqWord);
		etaWord = (EditText) findViewById(R.id.etaWord);
		dictionaryAdapter = new DictionaryDBAdapter(this);
		words = dictionaryAdapter.getAllWords();
		if(words.isEmpty()) {
			Message.message(this, "Jelenleg nincsenek szavak az adatbazisban.");
			btnNext.setEnabled(false);
			btnCheck.setEnabled(false);
			return;
		}
		randomWords = new ArrayList<Map.Entry<String, String>>(words.entrySet());
		wordCount = randomWords.size();
		Collections.shuffle(randomWords);
		getNextWord(btnNext);
	}

	public void getNextWord(View v) {
		if(location == wordCount) {
			//TODO kivanja ujrakezdeni etc. etc...
			Message.message(this, "Vege...");
			return;
		}
		Entry<String, String> wordPair = randomWords.get(location);
		tvqWord.setText(wordPair.getKey());
		currSol = wordPair.getValue().toUpperCase();
		location++;
	}

	public void check(View v) {
		String answer = etaWord.getText().toString().toUpperCase();
		if(answer.equals(currSol)) {
			Message.message(this, "Helyes megfejtes");
		} else {
			Message.message(this, "Helytelen megfejtes");
		}
	}
}
