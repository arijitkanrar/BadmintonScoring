package badmintonscoring.packages.mainApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnNewGame;
	private Intent intNewGame;
	private TextView txtT1LCPName;
	private TextView txtT1RCPName;
	private TextView txtT2LCPName;
	private TextView txtT2RCPName;
	private Button btnT1point;
	private Button btnT2point;
	private TextView txtT1Score;
	private TextView txtT2Score;
	private PopupWindow pwindow;
	private TextView winnerTeam;
	private Button winNewGameCT;
	private Button winNewGameNT;
	private Button winDone;

	private CGame newCreatedGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("App Status", "MainActivity.onCreate called");

		btnNewGame = (Button) findViewById(R.id.newGameButton);
		txtT1LCPName = (TextView) findViewById(R.id.txtT1LCPName);
		txtT1RCPName = (TextView) findViewById(R.id.txtT1RCPName);
		txtT2LCPName = (TextView) findViewById(R.id.txtT2LCPName);
		txtT2RCPName = (TextView) findViewById(R.id.txtT2RCPName);
		txtT1Score = (TextView) findViewById(R.id.txtT1TeamScore);
		txtT2Score = (TextView) findViewById(R.id.txtT2TeamScore);
		btnT1point = (Button) findViewById(R.id.btnT1Point);
		btnT2point = (Button) findViewById(R.id.btnT2Point);

		btnNewGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				openNewGameScreen();

			}
		});

		btnT1point.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t1Scores();

			}
		});

		btnT2point.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t2Scores();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.menuNewGame:
			openNewGameScreen();
			break;
		case R.id.menuGameHistory:
			Toast.makeText(getApplicationContext(), "Game history feature coming soon!",
					Toast.LENGTH_SHORT).show();
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.i("Status", "Result code was " + resultCode);

		if (resultCode == RESULT_OK) {

			newCreatedGame = (CGame) data
					.getParcelableExtra("NEW_GAME_DETAILS");

			if (newCreatedGame.getGameType() == EGameType.SINGLES) {

				txtT1RCPName.setText(newCreatedGame.getT1P1Name());
				// txtT1LCPName.setVisibility(View.INVISIBLE);

				txtT2RCPName.setText(newCreatedGame.getT2P1Name());
				// txtT2LCPName.setVisibility(View.INVISIBLE);

				Toast.makeText(
						getApplicationContext(),
						"New singles game created between "
								+ newCreatedGame.getT1P1Name() + " and "
								+ newCreatedGame.getT2P1Name() + ".",
						Toast.LENGTH_LONG).show();
			} else {

				txtT1RCPName.setText(newCreatedGame.getT1P1Name());
				txtT1LCPName.setText(newCreatedGame.getT1P2Name());
				txtT2RCPName.setText(newCreatedGame.getT2P1Name());
				txtT2LCPName.setText(newCreatedGame.getT2P2Name());

				Toast.makeText(
						getApplicationContext(),
						"New singles game created between "
								+ newCreatedGame.getT1P1Name() + ", "
								+ newCreatedGame.getT1P2Name() + " and "
								+ newCreatedGame.getT2P1Name() + ", "
								+ newCreatedGame.getT2P2Name() + ".",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "New game cancelled",
					Toast.LENGTH_SHORT).show();
		}

		refreshDisplay();

	}

	private void openNewGameScreen() {

		intNewGame = new Intent(getApplicationContext(), NewGameSettings.class);
		startActivityForResult(intNewGame, 1);
	}

	private void refreshDisplay() {

		if (newCreatedGame != null) {

			// get the score and display
			txtT1Score.setText(newCreatedGame.getT1Score().toString());
			txtT2Score.setText(newCreatedGame.getT2Score().toString());

			txtT1RCPName.setText(newCreatedGame.getT1RCPlayerName());
			txtT1LCPName.setText(newCreatedGame.getT1LCPlayerName());
			txtT2RCPName.setText(newCreatedGame.getT2RCPlayerName());
			txtT2LCPName.setText(newCreatedGame.getT2LCPlayerName());
		} else {
			txtT1Score.setText("0");
			txtT2Score.setText("0");

			txtT1RCPName.setText("Team 1 Right court player");
			txtT1LCPName.setText("Team 1 Left court player");
			txtT2RCPName.setText("Team 2 Right court player");
			txtT2LCPName.setText("Team 2 Left court player");
		}

	}

	private void t1Scores() {
		if (newCreatedGame == null) {
			Toast.makeText(getApplicationContext(),
					"No game created! Click on New Game", Toast.LENGTH_SHORT)
					.show();
		} else {
			newCreatedGame.incrementT1Score();

			refreshDisplay();
			declareWinner(newCreatedGame.checkWinner());
		}
	}

	private void t2Scores() {
		if (newCreatedGame == null) {
			Toast.makeText(getApplicationContext(),
					"No game created! Click on New Game", Toast.LENGTH_SHORT)
					.show();
		} else {
			newCreatedGame.incrementT2Score();

			refreshDisplay();
			declareWinner(newCreatedGame.checkWinner());
		}
	}

	private void declareWinner(int gameResult) {

		Point size = new Point();

		if (gameResult != 0) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.winner_popup,
					(ViewGroup) findViewById(R.id.winner_popup_element));

			getWindowManager().getDefaultDisplay().getSize(size);

			winnerTeam = (TextView) layout.findViewById(R.id.txtWinner);

			pwindow = new PopupWindow(layout, size.x - 10, (size.y / 3) * 2);

			winDone = (Button) layout.findViewById(R.id.btnWinDone);
			winNewGameCT = (Button) layout.findViewById(R.id.btnWinNewGameCT);
			winNewGameNT = (Button) layout.findViewById(R.id.btnWinNewGameNT);

			winDone.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pwindow.dismiss();
					newCreatedGame = null;
					refreshDisplay();
				}
			});

			winNewGameCT.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// reset the current game
					newCreatedGame.resetGame();
					pwindow.dismiss();
					refreshDisplay();

				}
			});

			winNewGameNT.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					openNewGameScreen();
					pwindow.dismiss();
					refreshDisplay();

				}
			});

			if (gameResult == 1) {

				winnerTeam.setText("Team 1 Wins!!");

				Toast.makeText(getApplicationContext(), "Team 1 Wins!!",
						Toast.LENGTH_LONG).show();
			} else if (gameResult == 2) {

				winnerTeam.setText("Team 2 Wins!!");

				Toast.makeText(getApplicationContext(), "Team 2 Wins!!",
						Toast.LENGTH_LONG).show();
			}

			pwindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

		}

	}

}
