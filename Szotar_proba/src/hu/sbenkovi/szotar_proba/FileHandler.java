package hu.sbenkovi.szotar_proba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

public class FileHandler {

	private static final String FILE_NAME = "words.txt";
	private static final String ENG_PREFIX = "E:";
	private static final String HUN_PREFIX = "H:";

	public static void writeNewWord(String engWord, String hunWord, ContextWrapper wrapper) {
		BufferedWriter bufferWriter = null;
		try {
			FileOutputStream fos = wrapper.openFileOutput(FILE_NAME, Context.MODE_APPEND);
			bufferWriter = new BufferedWriter(new OutputStreamWriter(fos));
			bufferWriter.append(ENG_PREFIX + engWord + "\n" + HUN_PREFIX + hunWord + "\n\n");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferWriter.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void readFile(ContextWrapper wrapper) {
		BufferedReader bufferReader = null;
		StringBuilder result = new StringBuilder();
		try {
			FileInputStream fis = wrapper.openFileInput(FILE_NAME);
			bufferReader = new BufferedReader(new InputStreamReader(fis));
			String line;
			while((line = bufferReader.readLine()) != null) {
				result.append(line + "\r\n");
			}
			Toast.makeText(wrapper, result, Toast.LENGTH_SHORT).show();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferReader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
}
