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

	private int t1Score;
	private int t2Score;

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
		
		flNoSets = (in.readInt()==1);

	}

	public int getT1Score() {
		return t1Score;
	}

	public void setT1Score(int t1Score) {
		this.t1Score = t1Score;
	}

	public int getT2Score() {
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
