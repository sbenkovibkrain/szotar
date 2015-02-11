package hu.sbenkovi.szotar_proba;

import hu.sbenkovi.szotar_proba.db.DictionaryDBAdapter;
import hu.sbenkovi.szotar_proba.db.Message;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewWordActivity extends Activity implements OnItemSelectedListener {

	private DictionaryDBAdapter dictionaryAdapter;

	private EditText etEngWord;
	private EditText etHunWord;
	private Spinner spDifficulty;
	private Button btnSubmitNewWord;

	private int currentDiffSel = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newword);

		dictionaryAdapter = new DictionaryDBAdapter(this);

		etEngWord = (EditText) findViewById(R.id.etEngWord);
		etHunWord = (EditText) findViewById(R.id.etHunWord);
		spDifficulty = (Spinner) findViewById(R.id.spDifficulty);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diffValues,
		    android.R.layout.simple_spinner_dropdown_item);
		spDifficulty.setAdapter(adapter);
		spDifficulty.setOnItemSelectedListener(this);
		btnSubmitNewWord = (Button) findViewById(R.id.btnSubmitNewWord);

		btnSubmitNewWord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addUser(v);
			}
		});
	}

	public void addUser(View view) {
		String engWord = etEngWord.getText().toString();
		String hunWord = etHunWord.getText().toString();
		long id = dictionaryAdapter.insertData(engWord, hunWord, currentDiffSel);
		if(id < 0) {
			Message.message(this, "Unsuccessful");
		} else {
			Message.message(this, "Successfully inserted a row");
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		TextView textView = (TextView) view;
		try {
			currentDiffSel = Integer.parseInt(textView.getText().toString());
		} catch(NumberFormatException e) {
			Message.message(this, "" + e);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
