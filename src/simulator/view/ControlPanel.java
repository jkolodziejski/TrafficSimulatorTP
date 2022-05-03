package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;


	Controller _controller;
	private JFileChooser fc;
	private List<JButton> buttons;
	private boolean _stopped=false;
	ChangeWeatherDialog changeWeatherDialog;
	ChangeCO2ClassDialog changeCO2ClassDialog;
	private RoadMap _roadMap;
	private int _time;

	public ControlPanel(Controller controller) {
		buttons = new ArrayList<>();
		this._controller = controller;
		controller.addObserver(this);
		initGUI();
		// TODO Auto-generated constructor stub
	}

	private void initGUI() {

		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.add(createJToolBar());

		
		fc = new JFileChooser();
		// trying to implement the FileChooser
		
		//int returnVal = fc.showOpenDialog(parent);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
//		}

	}
	
	
	public JToolBar createJToolBar(){
		
		JSpinner _ticksSpinner = new JSpinner(new SpinnerNumberModel(10,1,999999,1));
		_ticksSpinner.setMaximumSize(new Dimension(80, 30));
		_ticksSpinner.setMinimumSize(new Dimension(200, 40));
		_ticksSpinner.setPreferredSize(new Dimension(80, 30));
		
		JToolBar toolBar = new JToolBar();
		
		
		JLabel label = new JLabel(" Ticks: ", JLabel.LEFT);
		

		JButton load = new JButton();
		buttons.add(load);
		load.addActionListener((e)->{
			try {
				_controller.reset();
				loadFile();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error controller reset", e1.getMessage(), JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});
		load.setToolTipText("Load a file");
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		toolBar.add(load);
		
		toolBar.addSeparator();

		// Change Contamination Class
		JButton co2class = new JButton();
		buttons.add(co2class);
		co2class.addActionListener((e)->{
			
			 try {
				 	
				 	changeCO2ClassDialog = new ChangeCO2ClassDialog(_controller,_roadMap, _time);
				 	
			       changeCO2ClassDialog.open();
			    } catch (Exception ex) {
			    	JOptionPane.showMessageDialog(null, "Erros Change CO2 Dialog",ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			    }
		});
		co2class.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(co2class);

		JButton weather = new JButton();
		buttons.add(weather);
		weather.addActionListener((e)->{
			
			 try {
				 changeWeatherDialog = new ChangeWeatherDialog(_controller, _roadMap, _time);
				
			      changeWeatherDialog.open();
			    } catch (Exception ex) {
			    	JOptionPane.showMessageDialog(null, "Error Change Weather Dialog", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			    }
		});
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(weather);

		toolBar.addSeparator();

		// Run
		JButton run = new JButton();
		buttons.add(run);
		run.addActionListener((e)->{
			_stopped=false;
			run_sim((int) _ticksSpinner.getValue());
			
			
			
		});
		
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		toolBar.add(run);

		// Stop
		JButton stop = new JButton();
		
		stop.addActionListener((e)->{
			stop();
		});
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(stop);
		
		
		toolBar.add(label);
		
		toolBar.add(_ticksSpinner);
		
		
		// Exit
		
		JButton exit = new JButton();
		buttons.add(exit);
		exit.setAlignmentX(LEFT_ALIGNMENT);
		exit.addActionListener((e)->{
			 int result = JOptionPane.showConfirmDialog(null, "Exit?", "Confirm Exit",
                     JOptionPane.OK_CANCEL_OPTION);
             if (result == JOptionPane.OK_OPTION)
                 System.exit(0);
		});
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		toolBar.add(exit);

		return toolBar;
	}
	

	
	private void loadFile() throws FileNotFoundException {
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			InputStream _in = new FileInputStream(file);
			_controller.loadEvents(_in);
			
		}
	}
	
	
	private void run_sim(int n) {
		System.out.println();
		if (n > 0 && !_stopped) {
			try {
				enableToolBar(false);
				_controller.run_gui(1);;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error run",e.getMessage(), JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				return; 
        }
        SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			enableToolBar(true);
			_stopped = true;
		}
		
		
	}
	
	
	private void enableToolBar(Boolean _status) {
		if(_status!= true) {
			for(JButton button : buttons) {
				button.setEnabled(false);
			}
		}
		else {
			for(JButton button : buttons) {
				button.setEnabled(true);
			}
		}
	}
	
	private void stop() {
		enableToolBar(false);
	      _stopped = true;
	      
	     
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_time=time;
		_roadMap = map;
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time=time;
		_roadMap = map;
		

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_time=time;
		_roadMap = map;
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_time=time;
		_roadMap = map;
		

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_time=time;
		_roadMap = map;
		
		 
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	
	

}
