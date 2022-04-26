package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.html.CSS;

import simulator.control.Controller;
import simulator.factories.NewVehicleEventBuilder;
import simulator.misc.Pair;
import simulator.model.NewInterCityRoadEvent;import simulator.model.NewVehicleEvent;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimulator;
import simulator.model.Weather;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	
	protected String vehicles[];
	protected JComboBox<String> vehiclelist;
	protected JComboBox<Integer> co2classlist;
	protected Controller _ctrl;
	protected int _status;
	protected RoadMap _roadMap;
	protected int _time;
	
	public ChangeCO2ClassDialog (Controller ctrl , RoadMap roadMap , int time) {
		_roadMap = roadMap;
		_time = time;
		_ctrl=ctrl;
		initGui();
		
		
	}
	
	private void initGui() {
		setTitle("Change CO2 Class");
		
		
		JSpinner _ticksSpinner = new JSpinner(new SpinnerNumberModel(10,1,999999,1));
		_ticksSpinner.setMaximumSize(new Dimension(80, 30));
		_ticksSpinner.setMinimumSize(new Dimension(200, 40));
		_ticksSpinner.setPreferredSize(new Dimension(80, 30));
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel viewPanel = new JPanel();
		mainPanel.add(viewPanel, BorderLayout.CENTER);
		
		mainPanel.add(new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation"
				+ "ticks from now."),BorderLayout.PAGE_START);
		
		vehicles = new String[_roadMap.getVehicles().size()];
		for (int i = 0; i < vehicles.length; i++)
			vehicles[i] = _roadMap.getVehicles().get(i).getId();
		vehiclelist = new JComboBox<String>(vehicles);
		viewPanel.add(new JLabel(" Vehicle: "));
		viewPanel.add(vehiclelist);
		viewPanel.add(new JLabel(" C02 Class: "));
		co2classlist = new JComboBox<Integer>( new Integer[] { 0,1,2,3,4,5,6,7,8,9,10} );
		co2classlist.setSelectedIndex(0);
		viewPanel.add(co2classlist);
		
		viewPanel.add(new JLabel("Ticks: ") );
		viewPanel.add(_ticksSpinner);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton OKButton = new JButton("OK");
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ChangeCO2ClassDialog.this.setVisible(false);
				Pair<String, Integer> c = new Pair<>(vehiclelist.getSelectedItem().toString(), co2classlist.getSelectedIndex());
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(c);
				
				SetContClassEvent newEvent = new SetContClassEvent(_time+((int) _ticksSpinner.getValue()), cs); 
				_ctrl.addEvent(newEvent);
			}
		});
		buttonsPanel.add(OKButton);
		

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
		

	}
	
	public void set_roadMap(RoadMap _roadMap) {
		this._roadMap = _roadMap;
	}
	
	public void set_time(int _time) {
		this._time = _time;
	}
	
	
	public int open() {
		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}


}
