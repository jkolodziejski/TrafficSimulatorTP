package extra.controlPanel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	protected Controller _ctrl;
	
	
	
	public StatusBar(Controller ctrl) {
	
		_ctrl=ctrl;
		initGui();
		_ctrl.addObserver(this);
	}

	private void initGui() {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		addCompForBorder(loweredetched, "Time :",this );
		
		addCompForBorder(loweredetched,Integer.toString(_ctrl.getTraffic_simulator().get_time()) ,this);
		
		
	
	}
	
	void addCompForBorder(Border border, String description,JPanel panel) {
		
		JLabel label = new JLabel(description, JLabel.LEFT);
		panel.add(label);
		panel.setBorder(border);
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
