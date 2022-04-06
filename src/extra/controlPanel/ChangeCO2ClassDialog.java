package extra.controlPanel;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	
	String names[];
	JComboBox<String> list;
	Controller _ctrl;
	
	public ChangeCO2ClassDialog (Controller ctrl) {
		
		_ctrl=ctrl;
		initGui();
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel(new BorderLayout(10,10));
		mainPanel.add(new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation"
				+ "ticks from now."),BorderLayout.PAGE_START);
		names = new String[_ctrl.getTraffic_simulator().get_roadMap().getVehicles().size()];
		for (int i = 0; i < names.length; i++)
			names[i] = _ctrl.getTraffic_simulator().get_roadMap().getVehicles().get(i).getId();
		JComboBox<String> list = new JComboBox<String>(names);
		mainPanel.add(list, BorderLayout.CENTER);
		list.setSelectedIndex(0);
		/*
		list.addActionListener(this);

		this.add(new JLabel("Select a dialog"));
		mainPanel.add(list);
		mainPanel.setOpaque(true);

		this.setContentPane(mainPanel);
		*/
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);

	}

}
