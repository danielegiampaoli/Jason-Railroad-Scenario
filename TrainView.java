import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/** class that implements the View of Train Robot application */
public class TrainView extends GridWorldView {

    TrainModel tmodel;

    public TrainView(TrainModel model) {
        super(model, "Train Line", 700);
        tmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 16); // change default font
        setVisible(true);
        repaint();
    }

    /** draw application objects */
	// draw stations, signals and junction
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        switch (object) {
        case TrainModel.STATION1:
			drawStation(g, x-1, y, 1);
			drawStation(g, x-1, y-1, 0);
			drawStation(g, x-1, y-2, 0);
			break;
        case TrainModel.STATION2:
			drawStation(g, x+1, y, 2);
			drawStation(g, x-1, y, 0);
			drawStation(g, x+1, y+1, 0);
			drawStation(g, x+1, y+2, 0);
			drawStation(g, x-1, y+1, 0);
			drawStation(g, x-1, y+2, 0);
			break;
        case TrainModel.STATION3:
			drawStation(g, x, y-1, 3);
			drawStation(g, x+1, y-1, 0);
			drawStation(g, x+2, y-1, 0);
			break;
		case TrainModel.SIGNAL1:
			if (tmodel.signal1 == 0) {
				super.drawAgent(g, x, y, Color.green, -1);
			} else if (tmodel.signal1 == 1) {
				super.drawAgent(g, x, y, Color.yellow, -1);
			} else if (tmodel.signal1 == 2) {
				super.drawAgent(g, x, y, Color.red, -1);
			}
            break;
		case TrainModel.SIGNAL2:
			if (tmodel.signal2 == 0) {
				super.drawAgent(g, x, y, Color.green, -1);
			} else if (tmodel.signal2 == 1) {
				super.drawAgent(g, x, y, Color.yellow, -1);
			} else if (tmodel.signal2 == 2) {
				super.drawAgent(g, x, y, Color.red, -1);
			}
            break;
		case TrainModel.SIGNAL3:
			if (tmodel.signal3 == 0) {
				super.drawAgent(g, x, y, Color.green, -1);
			} else if (tmodel.signal3 == 1) {
				super.drawAgent(g, x, y, Color.yellow, -1);
			} else if (tmodel.signal3 == 2) {
				super.drawAgent(g, x, y, Color.red, -1);
			}
            break;
		case TrainModel.LONESIGNAL:
			if (tmodel.loneSignal == 0) {
				super.drawAgent(g, x, y+1, Color.green, -1);
			} else if (tmodel.loneSignal == 1) {
				super.drawAgent(g, x, y+1, Color.yellow, -1);
			} else if (tmodel.loneSignal == 2) {
				super.drawAgent(g, x, y+1, Color.red, -1);
			}
            break;
		case TrainModel.JUNCTION:
			if (tmodel.points == 0) {
				drawSwitch(g, x+1, y, 0);
			} else if (tmodel.points == 1) {
				drawSwitch(g, x+1, y, 1);
			}
            break;
        }
        //repaint();
    }
	
	// define how to draw station
	public void drawStation(Graphics g, int x, int y, int label) {
		super.drawObstacle(g, x, y);
		g.setColor(Color.yellow); // writes S in yellow
		switch (label) {
		case 1:
			drawString(g, x, y, defaultFont, "S ("+tmodel.waitingPassengers1+")");
			break;
		case 2:
			drawString(g, x, y, defaultFont, "S ("+tmodel.waitingPassengers2+")");
			break;
		case 3:
			drawString(g, x, y, defaultFont, "S ("+tmodel.waitingPassengers3+")");
			break;
		case 0:
			break;
		}
	}
	
	// define how to draw the junction
	public void drawSwitch(Graphics g, int x, int y, int label) {
		super.drawObstacle(g, x, y);
		g.setColor(Color.white); // writes switch state in white
		switch (label) {
		case 0:
			drawString(g, x, y, defaultFont, "N");
			break;
		case 1:
			drawString(g, x, y, defaultFont, "R");
			break;
		}
	}	

	// define how to draw the train
    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        Location lTrain = tmodel.getAgPos(0);
		c = Color.gray;
		super.drawAgent(g, lTrain.x, lTrain.y, c, -1);
		g.setColor(Color.orange);
		super.drawString(g, lTrain.x, lTrain.y, defaultFont, "T ("+tmodel.onboardPassengers+")");
		//repaint();
	}
}
