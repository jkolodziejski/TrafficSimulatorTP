package extra.jtable;

import java.util.List;
//import extra.jtable.*;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	
	private RoadMap _roadMap;
	private String[] _colNames = { "ID", "Status", "itinerary", "CO2 class", "Max speed" , "Speed" , "total CO2 " , "otal distance" };
	private Controller _ctrl ;
	Object[][] rowData;

	
	public VehiclesTableModel(Controller ctrl) {
		_ctrl=ctrl;
		_ctrl.addObserver(this);
		rowData = new Object[getRowCount()][_colNames.length];
		for (int i = 0; i < getRowCount(); i++) {
			rowData[i][0]=_roadMap.getVehicles().get(i).getId();
			rowData[i][1]=_roadMap.getVehicles().get(i).getStatus();
			rowData[i][2]=_roadMap.getVehicles().get(i).getItinerary();
			rowData[i][3]=_roadMap.getVehicles().get(i).getContClass();
			rowData[i][4]=_roadMap.getVehicles().get(i).getMaxSpeed();
			rowData[i][5]=_roadMap.getVehicles().get(i).getSpeed();
			rowData[i][6]=_roadMap.getVehicles().get(i).getTotalCO2();
			rowData[i][7]=_roadMap.getVehicles().get(i).getTotalTraveledDistance();
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

	//si no pongo esto no coge el nombre de las columnas
	//
	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// método obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _roadMap.getVehicles().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _roadMap.getVehicles().get(rowIndex).getId();
			break;
		case 1:
			VehicleStatus status =  _roadMap.getVehicles().get(rowIndex).getStatus();
			switch (status) {
			case PENDING:
				s="Pending";
				break;
			case TRAVELING:
				s=_roadMap.getVehicles().get(rowIndex).getRoad();
				s+=":"+_roadMap.getVehicles().get(rowIndex).getLocation();
				break;
			case WAITING:
				s="Waiting:"+_roadMap.getVehicles().get(rowIndex).getItinerary().get(_roadMap.getVehicles().get(rowIndex).getLast_seen_junction());
				break;
			case ARRIVED:
				s="Arrived";
				break;
			}
			
			break;
		case 2:
		s = _roadMap.getVehicles().get(rowIndex).getItinerary();
			break;
		case 3:
			s = _roadMap.getVehicles().get(rowIndex).getContClass();
			break;
		case 4:
			s = _roadMap.getVehicles().get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _roadMap.getVehicles().get(rowIndex).getSpeed();
			break;
		case 6:
			s = _roadMap.getVehicles().get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _roadMap.getVehicles().get(rowIndex).getTotalTraveledDistance();
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
