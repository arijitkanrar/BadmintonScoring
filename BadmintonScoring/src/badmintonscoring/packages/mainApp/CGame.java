package badmintonscoring.packages.mainApp;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class CGame implements Parcelable {

	EGameType gameType = EGameType.SINGLES;

	private String t1P1Name;
	private String t1P2Name;
	private String t2P1Name;
	private String t2P2Name;

	private Integer t1Score;
	private Integer t2Score;
	private int serverTeam;
	private int t1LCPlayer;
	private int t1RCPlayer;
	private int t2LCPlayer;
	private int t2RCPlayer;

	private boolean flNoSets;

	public CGame(EGameType gt, String t1p1n, String t1p2n, String t2p1n,
			String t2p2n, int t1S, int t2S, boolean flNS) {
		this.gameType = gt;
		this.t1P1Name = t1p1n;
		this.t1P2Name = t1p2n;
		this.t2P1Name = t2p1n;
		this.t2P2Name = t2p2n;
		this.t1Score = t1S;
		this.t2Score = t2S;
		this.flNoSets = flNS;
		this.serverTeam = 1;
		this.t1RCPlayer = 1;
		this.t2RCPlayer = 3;
		if (gt == EGameType.DOUBLES) {
			this.t1LCPlayer = 2;
			this.t2LCPlayer = 4;
		} else {
			t1LCPlayer = 0;
			t2LCPlayer = 0;
		}
	};

	public CGame(Parcel in) {

		// read the Game type
		try {
			gameType = EGameType.valueOf(in.readString());
		} catch (IllegalArgumentException e) {
			gameType = null;
		}

		// read the player names
		t1P1Name = in.readString();
		t1P2Name = in.readString();
		t2P1Name = in.readString();
		t2P2Name = in.readString();

		t1Score = in.readInt();
		t2Score = in.readInt();

		flNoSets = (in.readInt() == 1);

		serverTeam = 1;

		t1RCPlayer = 1;
		t2RCPlayer = 3;
		if (gameType == EGameType.DOUBLES) {
			t1LCPlayer = 2;
			t2LCPlayer = 4;
		} else {
			t1LCPlayer = 0;
			t2LCPlayer = 0;
		}

	}

	public int getServerTeam() {
		return serverTeam;
	}

	public void setServerTeam(int serverTeam) {
		this.serverTeam = serverTeam;
	}

	public Integer getT1Score() {
		return t1Score;
	}

	public void setT1Score(int t1Score) {
		this.t1Score = t1Score;
	}

	public Integer getT2Score() {
		return t2Score;
	}

	public void setT2Score(int t2Score) {
		this.t2Score = t2Score;
	}

	public EGameType getGameType() {
		return gameType;
	}

	public void setGameType(EGameType gameType) {
		this.gameType = gameType;
	}

	public String getT1P1Name() {
		return t1P1Name;
	}

	public void setT1P1Name(String t1p1Name) {
		t1P1Name = t1p1Name;
	}

	public String getT1P2Name() {
		return t1P2Name;
	}

	public void setT1P2Name(String t1p2Name) {
		t1P2Name = t1p2Name;
	}

	public String getT2P1Name() {
		return t2P1Name;
	}

	public void setT2P1Name(String t2p1Name) {
		t2P1Name = t2p1Name;
	}

	public String getT2P2Name() {
		return t2P2Name;
	}

	public void setT2P2Name(String t2p2Name) {
		t2P2Name = t2p2Name;
	}

	public boolean isFlNoSets() {
		return flNoSets;
	}

	public void setFlNoSets(boolean flNoSets) {
		this.flNoSets = flNoSets;
	}

	public int getT1LCPlayer() {
		return t1LCPlayer;
	}

	public void setT1LCPlayer(int t1lcPlayer) {
		t1LCPlayer = t1lcPlayer;
	}

	public int getT1RCPlayer() {
		return t1RCPlayer;
	}

	public void setT1RCPlayer(int t1rcPlayer) {
		t1RCPlayer = t1rcPlayer;
	}

	public int getT2LCPlayer() {
		return t2LCPlayer;
	}

	public void setT2LCPlayer(int t2lcPlayer) {
		t2LCPlayer = t2lcPlayer;
	}

	public int getT2RCPlayer() {
		return t2RCPlayer;
	}

	public void setT2RCPlayer(int t2rcPlayer) {
		t2RCPlayer = t2rcPlayer;
	}

	public void incrementT1Score() {
		this.t1Score++;
		adjustCourtPosition(1);
		serverTeam = 1;
	}

	public void incrementT2Score() {
		this.t2Score++;
		adjustCourtPosition(2);
		serverTeam = 2;
	}

	private void adjustCourtPosition(int team) {
		if (serverTeam == team) {
			if (gameType == EGameType.DOUBLES) {
				// the serving team scored a point. The have to swap their
				// courts if its a doubles game

				if (team == 1) {
					if (t1RCPlayer == 1) {
						t1RCPlayer = 2;
						t1LCPlayer = 1;
					} else {
						t1RCPlayer = 1;
						t1LCPlayer = 2;
					}
				} else {
					if (t2RCPlayer == 3) {
						t2RCPlayer = 4;
						t2LCPlayer = 3;
					} else {
						t2RCPlayer = 3;
						t2LCPlayer = 4;
					}
				}
			} else {
				// in a singles game both the server and opponent have to change
				// position
				if (t1RCPlayer == 1) {
					t1RCPlayer = 0;
					t1LCPlayer = 1;
				} else {
					t1RCPlayer = 1;
					t1LCPlayer = 0;
				}

				if (t2RCPlayer == 3) {
					t2RCPlayer = 0;
					t2LCPlayer = 3;
				} else {
					t2RCPlayer = 3;
					t2LCPlayer = 0;
				}
			}
		}
	}

	public String getT1LCPlayerName() {

		String retName;
		retName = "";

		switch (t1LCPlayer) {
		case 0:
			return "";
		case 1:
			retName = t1P1Name;
			break;
		case 2:
			retName = t1P2Name;
			break;
		}

		if (serverTeam == 1) {
			if (gameType == EGameType.SINGLES) {
				retName = retName + " (Serving)";
			} else {
				if (t1Score % 2 != 0) {
					retName = retName + " (Serving)";
				}
			}
		}

		return retName;
	}

	public String getT1RCPlayerName() {

		String retName;
		retName = "";

		switch (t1RCPlayer) {
		case 0:
			return "";
		case 1:
			retName = t1P1Name;
			break;
		case 2:
			retName = t1P2Name;
			break;
		}

		if (serverTeam == 1) {

			if (gameType == EGameType.SINGLES) {
				retName = retName + " (Serving)";

			} else {
				if (t1Score % 2 == 0) {
					retName = retName + " (Serving)";
				}
			}
		}

		return retName;
	}

	public String getT2LCPlayerName() {

		String retName;
		retName = "";

		switch (t2LCPlayer) {
		case 0:
			return "";
		case 3:
			retName = t2P1Name;
			break;
		case 4:
			retName = t2P2Name;
			break;
		}

		if (serverTeam == 2) {

			if (gameType == EGameType.SINGLES) {
				retName = retName + " (Serving)";

			} else {
				if (t2Score % 2 != 0) {
					retName = retName + " (Serving)";
				}
			}
		}

		return retName;
	}

	public String getT2RCPlayerName() {

		String retName;
		retName = "";

		switch (t2RCPlayer) {
		case 0:
			return "";
		case 3:
			retName = t2P1Name;
			break;
		case 4:
			retName = t2P2Name;
			break;
		}

		if (serverTeam == 2) {

			if (gameType == EGameType.SINGLES) {

				retName = retName + " (Serving)";

			} else {
				if (t2Score % 2 == 0) {
					retName = retName + " (Serving)";
				}
			}
		}

		return retName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		// TODO Auto-generated method stub
		out.writeString((gameType) == null ? "" : gameType.name());
		out.writeString(t1P1Name);
		out.writeString(t1P2Name);
		out.writeString(t2P1Name);
		out.writeString(t2P2Name);
		out.writeInt(t1Score);
		out.writeInt(t2Score);
		out.writeInt(flNoSets ? 1 : 0);

	}

	public static Parcelable.Creator<CGame> CREATOR = new Creator<CGame>() {

		@Override
		public CGame[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CGame createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CGame(source);
		}
	};

};
