import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class TrainEnv extends Environment {

    // common literals
    public static final Literal od = Literal.parseLiteral("open(doors)");
    public static final Literal cd = Literal.parseLiteral("close(doors)");
    public static final Literal up = Literal.parseLiteral("unboard(passenger)");
    public static final Literal bp = Literal.parseLiteral("board(passenger)");
    public static final Literal as = Literal.parseLiteral("at(train,station)");
	public static final Literal de = Literal.parseLiteral("depart(station)");

    static Logger logger = Logger.getLogger(TrainEnv.class.getName());

    TrainModel model; // the model of the grid

    @Override
    public void init(String[] args) {
        model = new TrainModel();

        if (args.length == 1 && args[0].equals("gui")) {
            TrainView view  = new TrainView(model);
            model.setView(view);
        }

        updatePercepts();
    }

    /* creates the agents percepts based on the TrainModel */
    void updatePercepts() {
        // clear the percepts of the agents
        clearPercepts("train");
		clearPercepts("signal1");
		clearPercepts("signal2");
		clearPercepts("signal3");
		clearPercepts("lone_signal");

        // get train location
        Location lTrain = model.getAgPos(0);

        // when the train is at a station, add this percept to its mind
        if (lTrain.equals(model.lStation1) || lTrain.equals(model.lStation2) || lTrain.equals(model.lStation2a) || lTrain.equals(model.lStation3)) {
            addPercept("train", as);
        }
		
		// also add it to the relative station signal's percepts
		if (lTrain.equals(model.lStation1)) {
			addPercept("signal1", as);
		}
		
		else if (lTrain.equals(model.lStation2) || lTrain.equals(model.lStation2a)) {
			addPercept("signal2", as);
		}		

		else if (lTrain.equals(model.lStation3)) {
			addPercept("signal3", as);
		}	
		
        // add waiting passengers "status" to the train's percepts
        if (model.doorsOpen) {
			switch (model.station) {
			case 1:
				addPercept("train", Literal.parseLiteral("waiting_pass(passenger,"+model.waitingPassengers1+")"));
				break;
			case 2:
				addPercept("train", Literal.parseLiteral("waiting_pass(passenger,"+model.waitingPassengers2+")"));
				break;
			case 3:
				addPercept("train", Literal.parseLiteral("waiting_pass(passenger,"+model.waitingPassengers3+")"));
				break;	
			}
        }		

        // add onboard passengers "status" to the percepts
        if (!model.doorsOpen) {
			addPercept("train", Literal.parseLiteral("onboard_pass(passenger,"+model.onboardPassengers+")"));
        }

		// when the train is departing, add this percept to the relative station's signal
		if (model.departing) {
			if (lTrain.equals(model.lStation1)) {
				addPercept("signal1", Literal.parseLiteral("done(operations,station)"));
			}
			
			if (lTrain.equals(model.lStation2) || lTrain.equals(model.lStation2a)) {
				addPercept("signal2", Literal.parseLiteral("done(operations,station)"));
			}		

			if (lTrain.equals(model.lStation3)) {
				addPercept("signal3", Literal.parseLiteral("done(operations,station)"));
			}
		}			
    }

	// function which executes actions coming from agent plans
    @Override
    public boolean executeAction(String ag, Structure action) {
        
		// print this action to jason log
		System.out.println("["+ag+"] doing: "+action);
		boolean result = false;
        
		// recognise action
		// open doors at station
		if (action.equals(od)) {
            result = model.openDoors();
		
		// close doors at station
        } else if (action.equals(cd)) {
            result = model.closeDoors();

		// move to next station
        } else if (action.getFunctor().equals("move_to")) {
            Location dest = null;
			if (model.station == 1) {
				dest = model.lStation1;
			} else if (model.station == 2) {
				dest = model.lStation2;
			} else if (model.station == 3) {
				dest = model.lStation3;
			}

            try {
                result = model.moveTowards(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }

		// drop passengers at station
        } else if (action.equals(up)) {
            result = model.unboard();

		// pick up passengers at station
        } else if (action.equals(bp)) {
            result = model.board();
			
		// depart from station
		} else if (action.equals(de)) {
            result = model.depart();
		
		// turn signal red
		} else if (action.getFunctor().equals("turn_red")) {	
			String l = action.getTerm(0).toString();
			int s = 1;
			if (l.equals("signal2")) {
				s = 2;
			} else if (l.equals("signal3")) {
				s = 3;
			} else if (l.equals("lone")) {
				s = 4;
			}
			
			try {
                result = model.turnRed(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
		
		// turn signal green
		}  else if (action.getFunctor().equals("turn_green")) {	
			String l = action.getTerm(0).toString();
			int s = 1;
			if (l.equals("signal2")) {
				s = 2;
			} else if (l.equals("signal3")) {
				s = 3;
			} else if (l.equals("lone")) {
				s = 4;
			}
			
			try {
                result = model.turnGreen(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
		
		// turn signal yellow
		}  else if (action.getFunctor().equals("turn_yellow")) {	
			String l = action.getTerm(0).toString();
			int s = 1;
			if (l.equals("signal2")) {
				s = 2;
			} else if (l.equals("signal3")) {
				s = 3;
			} else if (l.equals("lone")) {
				s = 4;
			}
			
			try {
                result = model.turnYellow(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
		
		// switch position of junction points
		} else if (action.getFunctor().equals("switch")) {	
			String l = action.getTerm(0).toString();
			int s = 1;
			if (l.equals("normal")) {
				s = 0;
			}
			
			try {
                result = model.switchPoints(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
		
		}
		
		else {
            logger.info("Failed to execute action "+action);
        }

        if (result) {
            updatePercepts();
            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }
        return result;
    }
}
