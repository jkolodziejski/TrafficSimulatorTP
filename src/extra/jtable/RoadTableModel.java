package extra.jtable;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] _colNames = { "ID", "length","weather","Max speed","Current speed","total CO2","CO2 limit" };
	private Controller _ctrl ;
	Object[][] rowData;
	private RoadMap _roadMap;

	public RoadTableModel(Controller ctrl) {
		_ctrl=ctrl;
		_ctrl.addObserver(this);
		rowData = new Object[getRowCount()][_colNames.length];
		for (int i = 0; i < getRowCount(); i++) {
			rowData[i][0]=_roadMap.getRoads().get(i).getId();
			rowData[i][1]=_roadMap.getRoads().get(i).getLength();
			rowData[i][2]=_roadMap.getRoads().get(i).getWeather();
			rowData[i][3]=_roadMap.getRoads().get(i).getMaxSpeed();
			rowData[i][4]=_roadMap.getRoads().get(i).getSpeedLimit();
			rowData[i][5]=_roadMap.getRoads().get(i).getTotalCO2();
			rowData[i][6]=_roadMap.getRoads().get(i).getContLimit();
		}
		
		// TODO Auto-generated constructor stub
	}
	
	public void setRoadMap(RoadMap roadMap) {
		_roadMap=roadMap;
		update();
	}
	
	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();	
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _roadMap.getRoads().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _roadMap.getRoads().get(rowIndex).getId();
			break;
		case 1:
			s = _roadMap.getRoads().get(rowIndex).getLength();
			break;
		case 2:
		s = _roadMap.getRoads().get(rowIndex).getWeather();
			break;
		case 3:
			s = _roadMap.getRoads().get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roadMap.getRoads().get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = _roadMap.getRoads().get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = _roadMap.getRoads().get(rowIndex).getContLimit();
			break;
	
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setRoadMap(map);
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setRoadMap(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setRoadMap(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setRoadMap(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setRoadMap(map);
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
