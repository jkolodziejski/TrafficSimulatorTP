package extra.jtable;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoadMap _roadMap;
	private Controller _ctrl;
	private String[] _colNames = { "#", "ID" , "Green" , "Queues"};
	Object[][] rowData;

	public JunctionsTableModel(Controller ctrl) {
		_ctrl=ctrl;
		_ctrl.addObserver(this);
		rowData = new Object[getRowCount()][_colNames.length];
		for (int i = 0; i < getRowCount(); i++) {
			rowData[i][0]=i;
			rowData[i][1]= _roadMap.getJunctions().get(i).getId();
			rowData[i][2]=_roadMap.getJunctions().get(i).getGreenLightIndex();
			rowData[i][3]=_roadMap.getJunctions().get(i).getQueues();
			
		}
		
	}
	

	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();	
	}
	
	public void setRoadMap(RoadMap roadMap) {
		_roadMap=roadMap;
		update();
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
		return _roadMap.getJunctions().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = rowIndex;
			break;
		case 1:
			s = _roadMap.getJunctions().get(rowIndex).getId();
			break;
		case 2:
			s = _roadMap.getJunctions().get(rowIndex).getGreenLightIndex();
			break;
		case 3:
		s = _roadMap.getJunctions().get(rowIndex).getQueues();
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
