package badmintonscoring.packages.mainApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class NewGameSettings extends Activity {

	private RadioGroup newGameType;
	private RadioButton gameTypeSingles;
	private RadioButton gameTypeDoubles;
	private EditText txtT1P1Name;
	private EditText txtT1P2Name;
	private EditText txtT2P1Name;
	private EditText txtT2P2Name;
	private Button btnStartGame;
	private CheckBox chkNoSets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game_activity);

		newGameType = (RadioGroup) findViewById(R.id.radGrpGameType);
		gameTypeSingles = (RadioButton) findViewById(R.id.radSingles);
		gameTypeDoubles = (RadioButton) findViewById(R.id.radDoubles);
		txtT1P1Name = (EditText) findViewById(R.id.txtPlayer1Name);
		txtT1P2Name = (EditText) findViewById(R.id.txtPlayer2Name);
		txtT2P1Name = (EditText) findViewById(R.id.txtT2Player1Name);
		txtT2P2Name = (EditText) findViewById(R.id.txtT2Player2Name);
		btnStartGame = (Button) findViewById(R.id.btnStartGame);
		chkNoSets = (CheckBox) findViewById(R.id.chkNoSets);

		newGameType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				handleGameTypeChange();
			}
		});

		btnStartGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// check if game type is selected 
				if (newGameType.getCheckedRadioButtonId()==-1){
					Toast.makeText(getApplicationContext(), "Please choose Singles or Doubles game", Toast.LENGTH_SHORT).show();
				}

				// create a new game class and send it back to the main activity
				CGame newGame = new CGame(
						gameTypeSingles.isChecked() ? EGameType.SINGLES
								: EGameType.DOUBLES, txtT1P1Name.getText().toString(),
						txtT1P2Name.getText().toString(), txtT2P1Name.getText().toString(),
						txtT2P2Name.getText().toString(), 0, 0, chkNoSets.isChecked());
				
				Intent resultIntent = new Intent();
				resultIntent.putExtra("NEW_GAME_DETAILS", newGame);
				setResult(RESULT_OK, resultIntent);
				finish();
				
			}
		});

	}

	private void handleGameTypeChange() {

		if (gameTypeSingles.isChecked() == true) {
			// singles game
			txtT1P2Name.setVisibility(View.GONE);
			txtT2P2Name.setVisibility(View.GONE);
			txtT1P1Name.setHint(R.string.hintPlayer1Str);
			txtT2P1Name.setHint(R.string.hintPlayer2Str);
		} else if (gameTypeDoubles.isChecked() == true) {
			// doubles game
			txtT1P2Name.setVisibility(View.VISIBLE);
			txtT2P2Name.setVisibility(View.VISIBLE);
			txtT1P1Name.setHint(R.string.hintTeam1Pl1Str);
			txtT1P2Name.setHint(R.string.hintTeam1Pl2Str);
			txtT2P1Name.setHint(R.string.hintTeam2Pl1Str);
			txtT2P2Name.setHint(R.string.hintTeam2Pl2Str);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		setResult(RESULT_CANCELED);
		finish();
		
	}
	
	

}
