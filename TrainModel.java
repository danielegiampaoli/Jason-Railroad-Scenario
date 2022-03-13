import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.concurrent.ThreadLocalRandom;

/** class that implements the Model of Train Line application */
public class TrainModel extends GridWorldModel {

    // constants for the grid objects
    public static final int STATION1 = 16;
    public static final int SIGNAL1  = 32;
	
	public static final int STATION2 = 64;
    public static final int SIGNAL2  = 128;
	
	public static final int STATION3 = 256;
    public static final int SIGNAL3  = 512;
	
	public static final int LONESIGNAL = 1024;
	
	public static final int JUNCTION = 2048;

    // the grid size
    public static final int GSize = 15;
	
	// min and max number of random passengers on the train or waiting
	public static final int min = 0;
	public static final int max = 30;

    boolean doorsOpen = false; // whether the train doors are open
    int waitingPassengers1 = 20; // how many passengers are waiting at the station
	int waitingPassengers2 = 16;
	int waitingPassengers3 = 22;
	int onboardPassengers = 20; // how many passengers are on the train initially

    Location lStation1 = new Location(2,8);
    Location lSignal1  = new Location(1,9);
	
	Location lStation2 = new Location(11,6);
	Location lStation2a = new Location(9,6);
    Location lSignal2  = new Location(12,5);
	
	Location lStation3 = new Location(6,2);
    Location lSignal3  = new Location(5,1);
	
	Location lLoneSignal = new Location(6,12);
	
	Location lJunction = new Location(11,11);

	int station = 1; // first station on the track is station 1
	int signal1 = 0; // initial colours for signals, 0 is green, 1 is yellow and 2 is red.
	int signal2 = 0;
	int signal3 = 0;
	int loneSignal = 0;
	int points = 0; // 0 is normal, 1 is reverse

    public TrainModel() {
        // create a 14x15 grid with one mobile agent
        super(GSize-1, GSize, 1);

        // initial location of train (column 2, line 4)
        // ag code 0 means the train
        setAgPos(0, 2, 4);

        // locations of stations, signals and junction
        add(STATION1, lStation1);
		add(STATION2, lStation2);
		add(STATION3, lStation3);
        add(SIGNAL1, lSignal1);
		add(SIGNAL2, lSignal2);
		add(SIGNAL3, lSignal3);
		add(LONESIGNAL, lLoneSignal);
		add(JUNCTION, lJunction);
    }

	// open doors
    boolean openDoors() {
        if (!doorsOpen) {
            doorsOpen = true;
            return true;
        } else {
            return false;
        }
    }

	// close doors
    boolean closeDoors() {
        if (doorsOpen) {
            doorsOpen = false;
            return true;
        } else {
            return false;
        }
    }
	
	String direction = "down";
	boolean changeTrack = false; // true if points position is reverse, so train will change track
	
	// move train
    boolean moveTowards(Location dest) {
		
		departing = false; // triggers addition of done(operations,station) belief
        Location r1 = getAgPos(0); // get train location
		
		// stop train at signal on the track
		if (r1.equals(lLoneSignal) && loneSignal != 0){
			System.out.println("[train] doing: stop_at(signal)"); // print on console
			return true;
		}
		
		// change track if points are in reverse position
		if (r1.x == 11 && r1.y == 11 && points == 1) {
			System.out.println("[train] doing: switch(track)");
			changeTrack = true;
		}
		
		// the train can only move if it is not at a station, or
		// if at a station, if the signal is green
		if (!r1.equals(lStation1) || !r1.equals(lStation2) || !r1.equals(lStation3) ||
			(r1.equals(lStation1) && signal1 == 0) || (r1.equals(lStation2) && signal2 == 0) ||
			(r1.equals(lStation3) && signal3 == 0) || !r1.equals(lLoneSignal)) {
				
			// move the train in the grid*/
			if (r1.x == 2 && r1.y == 2){
				direction = "down";
				r1.y++;
			}
			else if (r1.x == 2 && r1.y < 12 && direction == "down"){
				r1.y++;
			}
			else if (r1.x == 2 && r1.y == 12){
				r1.x++;
				direction = "right";
			}
			else if (r1.x < 11 && r1.y == 12 && direction == "right"){
				r1.x++;
			}
			else if (r1.x == 11 && r1.y == 12){
				r1.y--;
				direction = "up";
			}
			else if (r1.x == 11 && r1.y == 9 && changeTrack == true){
				r1.x--;
				direction = "left";
			}
			else if (r1.y == 9 && r1.x > 9 && changeTrack == true){
				r1.x--;
			}
			else if (r1.y == 9 && r1.x == 9 && changeTrack == true){
				r1.y--;
				direction = "up";
			}
			else if (r1.y > 5 && r1.x == 9 && changeTrack == true){
				r1.y--;
			}
			else if (r1.y == 5 && r1.x == 9 && changeTrack == true){
				r1.x++;
				direction = "right";
			}
			else if (r1.y == 5 && r1.x < 11 && changeTrack == true){
				r1.x++;
			}
			else if (r1.y == 5 && r1.x == 11 && changeTrack == true){
				r1.y--;
				direction = "up";
				changeTrack = false;
			}
			else if (r1.x == 11 && r1.y > 2 && direction == "up"){
				r1.y--;
			}
			else if (r1.x == 11 && r1.y == 2){
				r1.x--;
				direction = "left";
			}
			else if (r1.x > 2 && r1.y == 2 && direction == "left"){
				r1.x--;
			}
			setAgPos(0, r1);				
		}
		
        return true;
    }
	
	boolean departing = false;
	
	// set a marker to next station to let the train move
	boolean depart() {
		departing = true;
		if (station == 1) {
			station = 2;
		} else if (station == 2) {
			station = 3;
		} else if (station == 3) {
			station = 1;
		}
		return true;
	}
	
	// turn signal red
	boolean turnRed(int s) {
		if (s == 1) {
			signal1 = 2;
		}
		else if (s == 2) {
			signal2 = 2;
		}
		else if (s == 3) {
			signal3 = 2;
		}
		else if (s == 4) {
			loneSignal = 2;
		}		
		if (view != null) {
			view.update(lSignal1.x,lSignal1.y);
			view.update(lSignal2.x,lSignal2.y);
			view.update(lSignal3.x,lSignal3.y);
			view.update(lLoneSignal.x,lLoneSignal.y);
		}
		return true;
	}
	
	// turn signal green
	boolean turnGreen(int s) {
		if (s == 1) {
			signal1 = 0;
		}
		else if (s == 2) {
			signal2 = 0;
		}
		else if (s == 3) {
			signal3 = 0;
		}
		else if (s == 4) {
			loneSignal = 0;
		}		
		if (view != null) {
			view.update(lSignal1.x,lSignal1.y);
			view.update(lSignal2.x,lSignal2.y);
			view.update(lSignal3.x,lSignal3.y);
			view.update(lLoneSignal.x,lLoneSignal.y);
		}
		return true;
	}
	
	// turn yellow signal
	boolean turnYellow(int s) {
		if (s == 1) {
			signal1 = 1;
		}
		else if (s == 2) {
			signal2 = 1;
		}
		else if (s == 3) {
			signal3 = 1;
		}
		else if (s == 4) {
			loneSignal = 1;
		}		
		if (view != null) {
			view.update(lSignal1.x,lSignal1.y);
			view.update(lSignal2.x,lSignal2.y);
			view.update(lSignal3.x,lSignal3.y);
			view.update(lLoneSignal.x,lLoneSignal.y);
		}
		return true;
	}
	
	// switch junction points position
	boolean switchPoints(int s) {
		if (s == 1) {
			points = 1;
		}
		else if (s == 0) {
			points = 0;
		}
		if (view != null) {
			view.update(lJunction.x,lJunction.y);
		}
		return true;
	}			
	
	// board passengers
	boolean board() {
		switch (station) {
		case 1:
			while (doorsOpen && waitingPassengers1 > 0) {
				waitingPassengers3 = ThreadLocalRandom.current().nextInt(min, max+1);
				//waitingPassengers3 = 15;
				waitingPassengers1--;
				onboardPassengers++;
				if (view != null) {
					view.update(lStation1.x,lStation1.y);
				}
				try {
					Thread.sleep(150);
				} catch (Exception e) {}
			}
			break;
		case 2:
			while (doorsOpen && waitingPassengers2 > 0) {
				waitingPassengers1 = ThreadLocalRandom.current().nextInt(min, max+1);
				//waitingPassengers1 = 15;
				waitingPassengers2--;
				onboardPassengers++;
				if (view != null) {
					view.update(lStation2a.x,lStation2a.y);
					view.update(lStation2.x,lStation2.y);
				}
				try {
					Thread.sleep(150);
				} catch (Exception e) {}
			}
			break;
		case 3:
			while (doorsOpen && waitingPassengers3 > 0) {
				waitingPassengers2 = ThreadLocalRandom.current().nextInt(min, max+1);
				//waitingPassengers2 = 15;
				waitingPassengers3--;
				onboardPassengers++;
				if (view != null) {
					view.update(lStation3.x,lStation3.y);
				}
				try {
					Thread.sleep(150);
				} catch (Exception e) {}
			}
			break;
		}			
		return true;
	}

	// unboard passengers
    boolean unboard() {
        while (doorsOpen && onboardPassengers > 0) {
			onboardPassengers--;
            if (view != null) {
                view.update(lStation1.x,lStation1.y);
				view.update(lStation2.x,lStation2.y);
				view.update(lStation2a.x,lStation2a.y);
				view.update(lStation3.x,lStation3.y);
			}
			try {
                Thread.sleep(150);
            } catch (Exception e) {}
		}
		return true;
	}
}
