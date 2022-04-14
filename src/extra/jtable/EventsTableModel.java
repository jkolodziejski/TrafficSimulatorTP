package extra.jtable;

import java.util.List;
//import extra.jtable.*;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	
	
	private List<Event> _events;
	private String[] _colNames = { "Time", "Description" };
	private Controller _ctrl ;
	Object[][] rowData;

	

	public EventsTableModel(Controller ctrl) {
		_ctrl=ctrl;
		ctrl.addObserver(this);
		setEventsList(_ctrl.getTraffic_simulator().get_events());
		rowData = new Object[getRowCount()][_colNames.length];
		for (int i = 0; i < getRowCount(); i++) {
			rowData[i][0]=_events.get(i).getTime();
			rowData[i][1]=_events.get(i).toString();
			
		}
	}

	public void update() {
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.
		
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();	
	}
	
	public void setEventsList(List<Event> events) {
		_events = events;
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
		return _events == null ? 0 : _events.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setEventsList(events);
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setEventsList(events);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setEventsList(events);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setEventsList(events);
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
