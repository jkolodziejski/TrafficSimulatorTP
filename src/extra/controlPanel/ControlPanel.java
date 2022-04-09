package extra.controlPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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


	private boolean _stopped=false;

	public ControlPanel(Controller controller) {
		this._controller = controller;
		initGUI();
		controller.addObserver(this);
		// TODO Auto-generated constructor stub
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());

		this.add(createJToolBar());
		
		
		fc = new JFileChooser();
		// trying to implement the FileChooser
		
		//int returnVal = fc.showOpenDialog(parent);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
//		}

	}
	
	
	public JToolBar createJToolBar() {
		JToolBar toolBar = new JToolBar();
		
		
		JLabel label = new JLabel(" Ticks: ", JLabel.LEFT);
		
		JTextField userText = new JTextField();
		userText.setHorizontalAlignment(JTextField.RIGHT);
		userText.setMaximumSize(new Dimension(50,100));

		JButton load = new JButton();
		load.addActionListener((e)->{
			try {
				_controller.reset();
				loadFile();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		load.setToolTipText("Load a file");
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		toolBar.add(load);
		
		toolBar.addSeparator();

		// Change Contamination Class
		JButton co2class = new JButton();
		
		co2class.addActionListener((e)->{
			
			 try {
				 ChangeCO2ClassDialog changeCO2ClassDialog = new ChangeCO2ClassDialog(_controller);
			       changeCO2ClassDialog.open();
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
		});
		co2class.setIcon(new ImageIcon("resources/icons/co2class.png"));
		toolBar.add(co2class);

		JButton weather = new JButton();
		
		weather.addActionListener((e)->{
			
			 try {
				 ChangeWeatherDialog changeWeatherDialog = new ChangeWeatherDialog(_controller);
			      changeWeatherDialog.open();
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
		});
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		toolBar.add(weather);

		toolBar.addSeparator();

		// Run
		JButton run = new JButton();
		run.addActionListener((e)->{
			_stopped=false;
			run_sim(Integer.parseInt(userText.getText()));
			
			
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
		
		toolBar.add(userText);
		
		// Stop
		JButton exit = new JButton();
		
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
				_controller.run(1,System.out);
			} catch (Exception e) {
              // TODO show error message
				_stopped = true;
				return; 
        }
        SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			//enableToolBar(true);
			_stopped = true;
		}
		
		
	}
	
	private void stop() {
	      _stopped = true;
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
