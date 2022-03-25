package extra.controlPanel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.FlowView.FlowStrategy;
import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	Controller _controller;

	public ControlPanel(Controller controller) {
		_controller = controller;
		initGUI();
		controller.addObserver(this);
		// TODO Auto-generated constructor stub
	}

	private void initGUI() {

		new BorderLayout(5, 5);

		JToolBar toolBar = new JToolBar("Still draggable");
		this.add(toolBar, BorderLayout.PAGE_START);

		// Load Events File
		JButton load = new JButton();
		load.addActionListener(null);
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		toolBar.add(load);

		toolBar.addSeparator();
		
		// trying to implement the FileChooser
		JFileChooser fc = new JFileChooser();
		//int returnVal = fc.showOpenDialog(parent);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
//		}

		// Change Contamination Class
		JButton co2class = new JButton();
		co2class.addActionListener(null);
		co2class.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(co2class);

		JButton weather = new JButton();
		weather.addActionListener(null);
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(weather);

		toolBar.addSeparator();

		// Run
		JButton run = new JButton();
		run.addActionListener(null);
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		toolBar.add(run);

		// Stop
		JButton stop = new JButton();
		stop.addActionListener(null);
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(stop);

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
