package simulator.view;


import java.awt.FlowLayout;

import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.Border;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	protected Controller _ctrl;
	
	
	private JLabel txt;
	private JLabel Jtime;
	
	
	public StatusBar(Controller ctrl) {
		
		_ctrl=ctrl;
		initGui();
		_ctrl.addObserver(this);
		
		
	}
	
	

	private void initGui() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		txt = new JLabel();
		Jtime = new JLabel();
		this.add(new JLabel("Time : "));
		this.add(Jtime);
		this.add( txt );
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		
		
	
	}
	
	private void setMsg(String msg) {
		txt.setText(msg);
	}
	
	private void setTime(int time) {
		
			Jtime.setText(Integer.toString(time));
		
		
		
	}
	
	void addCompForBorder(Border border, String description,JPanel panel) {
		
		JLabel label = new JLabel(description, JLabel.LEFT);
		panel.add(label);
		panel.setBorder(border);
		
	}
	
	

	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setTime(time);
		setMsg("");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
	
		setTime(time);
		setMsg("");
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
		setMsg(e.toString());

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setTime(time);
		setMsg("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setTime(time);
		setMsg("");
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
