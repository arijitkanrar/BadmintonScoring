package badmintonscoring.packages.mainApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnNewGame;
	private Intent intNewGame;
	private TextView txtT1LCPName;
	private TextView txtT1RCPName;
	private TextView txtT2LCPName;
	private TextView txtT2RCPName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnNewGame = (Button) findViewById(R.id.newGameButton);
		txtT1LCPName = (TextView)findViewById(R.id.txtT1LCPName);
		txtT1RCPName = (TextView)findViewById(R.id.txtT1RCPName);
		txtT2LCPName = (TextView)findViewById(R.id.txtT2LCPName);
		txtT2RCPName = (TextView)findViewById(R.id.txtT2RCPName);

		btnNewGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				intNewGame = new Intent(getApplicationContext(),
						NewGameSettings.class);
				startActivityForResult(intNewGame, 1);

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.i("Status", "Result code was " + resultCode);

		if (resultCode == RESULT_OK) {

			CGame newCreatedGame = (CGame) data
					.getParcelableExtra("NEW_GAME_DETAILS");

			if (newCreatedGame.getGameType() == EGameType.SINGLES) {
				
				txtT1RCPName.setText(newCreatedGame.getT1P1Name());
				txtT1LCPName.setVisibility(View.INVISIBLE);
				
				txtT2RCPName.setText(newCreatedGame.getT2P1Name());
				txtT2LCPName.setVisibility(View.INVISIBLE);
				
				Toast.makeText(
						getApplicationContext(),
						"New singles game created between "
								+ newCreatedGame.getT1P1Name()
								+ " and "
								+ newCreatedGame.getT2P1Name()
								+ " and "
								+ (newCreatedGame.isFlNoSets() ? " no sets"
										: " 3 sets") + ".", Toast.LENGTH_LONG)
						.show();
			} else {
				
				txtT1RCPName.setText(newCreatedGame.getT1P1Name());
				txtT1LCPName.setText(newCreatedGame.getT1P2Name());
				txtT2RCPName.setText(newCreatedGame.getT2P1Name());
				txtT2LCPName.setText(newCreatedGame.getT2P2Name());
				
				Toast.makeText(
						getApplicationContext(),
						"New singles game created between "
								+ newCreatedGame.getT1P1Name()
								+ ", "
								+ newCreatedGame.getT1P2Name()
								+ " and "
								+ newCreatedGame.getT2P1Name()
								+ ", "
								+ newCreatedGame.getT2P2Name()
								+ " and "
								+ (newCreatedGame.isFlNoSets() ? " no sets"
										: " 3 sets") + ".", Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "New game cancelled",
					Toast.LENGTH_SHORT).show();
		}

	}

}
