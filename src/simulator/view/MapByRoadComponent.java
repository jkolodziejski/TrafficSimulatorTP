package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;
	
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private Controller _ctrl;
	private RoadMap _map;
	private Image _car ,_weather, _contlevel;

	public MapByRoadComponent(Controller ctrl) {
		_ctrl=ctrl;
		ctrl.addObserver(this);
		this.setPreferredSize(new Dimension(300, 200));
		initGUI();
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		if (_map == null || _map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No road yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			//updatePrefferedSize();
			drawRoads(g);
		}

		
	}
	
	
	private void drawRoads(Graphics g) {
		int i=0;
		for (Road r : _map.getRoads()) {
			

			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int y =(i+1)*50;
			int x2 = getWidth()-100;
			g.setColor(Color.BLACK);
			g.drawLine(x1,y,x2,y);
			g.drawString(r.getId(),x1-20,y+5);
			
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}
			g.setColor(arrowColor);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y-10);
			g.drawString(r.getSrc().getId(), x2, y-10);
			
			for (Vehicle v : r.getVehicles()) {
			
			int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation()/ (double)r.getLength()));
			int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
			g.setColor(new Color(0, vLabelColor, 0));

			// draw an image of a car (with circle as background) and it identifier
			g.drawImage(_car, x, y - 6, 12, 12, this);
			g.drawString(v.getId(), x, y - 6);
			}
			
			ChooseWeather(r);
			g.drawImage(_weather, x2+20, y - 16, 32, 32, this);
			
			ChooseCont(r);
			
			g.drawImage(_contlevel, x2+60, y - 16, 32, 32, this);
			
			
			i++;
		}

	}
	
	private void ChooseWeather(Road r) {
		int C = (int) Math.floor(Math.min((double)r.getTotalCO2()/(1.0 + (double)r.getContLimit()),1.0) / 0.19);
		
		switch (C) {
		case 0 :
			_contlevel=loadImage("cont_1.png");
			break;
		case 1:
			_contlevel=loadImage("cont_1.png");
			break;
		case 2:
			_contlevel=loadImage("cont_2.png");
			break;
		case 3:
			_contlevel=loadImage("cont_3.png");
			break;
		case 4:
			_contlevel=loadImage("cont_4.png");
			break;
		case 5:
			_contlevel=loadImage("cont_5.png");
			break;
		}
	}
	
	private void ChooseCont(Road r) {
		Weather weather = r.getWeather();
		switch (weather) {
		case RAINY :
			_weather=loadImage("rain.png");
			break;
		case CLOUDY:
			_weather=loadImage("cloud.png");
			break;
		case STORM:
			_weather=loadImage("storm.png");
			break;
		case SUNNY:
			_weather=loadImage("sun.png");
			break;
		case WINDY:
			_weather=loadImage("wind.png");
			break;
		}
	}
	
	
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}