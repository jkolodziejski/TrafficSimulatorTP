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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	protected String vehicles[];
	protected JComboBox<String> roadlist;
	protected JComboBox<Weather> weatherlist;
	protected Controller _ctrl;
	protected int _status;
	protected RoadMap _roadMap;
	protected int _time;
	
	public ChangeWeatherDialog(Controller ctrl, RoadMap roadMap , int time) {
		_ctrl=ctrl;
		_roadMap = roadMap;
		_time = time;
		
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
		
		mainPanel.add(new JLabel("Schedule an event to change the Weather of a road after a given number of simulation"
				+ "ticks from now."),BorderLayout.PAGE_START);
		
		vehicles = new String[_roadMap.getRoads().size()];
		for (int i = 0; i < vehicles.length; i++)
			vehicles[i] = _roadMap.getRoads().get(i).getId();
		roadlist = new JComboBox<String>(vehicles);
		viewPanel.add(new JLabel(" Road: "));
		viewPanel.add(roadlist);
		viewPanel.add(new JLabel(" Weather: "));
		
		weatherlist = new JComboBox<Weather>(Weather.values());
		viewPanel.add(weatherlist);
		
		viewPanel.add(new JLabel("Ticks: ") );
		
		viewPanel.add(_ticksSpinner);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton OKButton = new JButton("OK");
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ChangeWeatherDialog.this.setVisible(false);
				
				Pair<String, Weather> c = new Pair<>(roadlist.getSelectedItem().toString(),Weather.valueOf(weatherlist.getSelectedItem().toString()));
				List<Pair<String, Weather>> cs = new ArrayList<>();
				cs.add(c);
				
				SetWeatherEvent newEvent = new SetWeatherEvent(_time+((int) _ticksSpinner.getValue()), cs); 
				try {
				_ctrl.addEvent(newEvent);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error run",e1.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
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
