package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	 private List<Road> inRoads;
     private List<List<Vehicle>> queues; // the i-th queue is of the i-th road in _inRoads
     private Map<Road, List<Vehicle>> queueByRoad; // this is just for fast searching of queues
     private Map<Junction, Road> outRoadByJunction; // to tell how to go to a junction
     private int greenLightIndex,  lastSwitchingTime;   // the index of the road (in _inRoads) that has geen light (-1 all red)
     private LightSwitchingStrategy lss;
     private DequeuingStrategy dqs;
     private int x,y  ;
	
	
	
     Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws Exception {
    	 super(id);
    	 if(lsStrategy != null && dqStrategy != null) {
    		this.lss = lsStrategy;
    		this.dqs = dqStrategy;
    	 }
    	 else {
    		 throw new Exception("LightSwitchingStrategy or DequeuingStrategy is null ");
			
		}
    	 
    	 if(xCoor >=0 && yCoor >=0) {
    		 this.x = xCoor;
    		 this.y = yCoor;
    	 }
    	 else {
    		 throw new Exception("xCoor or yCoor is negative");
    	 }
 
    	 
    	 }
     
     public List<Road> getInRoads() {
 		return inRoads;
 	}
     
     void addIncommingRoad(Road r) throws Exception {
    	 if(r.getDest().equals(this)) {
    		 inRoads.add(r);
    		 LinkedList queue = new LinkedList<Vehicle>();
    		 queues.add(queue);
    		 queueByRoad.put(r, queue);
    		 
    	 }
    	 else {
    		 throw new Exception("destination junction is not  equal to the current junction");
    	 }
     }
     
     void addOutGotingRoad(Road r) throws Exception {
    	 if(r.getSrc().equals(this) && r.getDest().getInRoads() == null) {
    		 outRoadByJunction.put(r.getDest(),r);
    		 
    		 
    	 }
    	 else {
    		 throw new Exception("Other road go junction or that road is not outgoing road");
    	 }
    	 
     }
     
     void enter(Vehicle v) {
    	int index_road = inRoads.indexOf(v.getRoad());
    	List<Vehicle> support_list = queues.get(index_road);
    	support_list.add(v);
    	queues.add(index_road, support_list);
    	queueByRoad.put(v.getRoad(), support_list);
    	
    	
     }
     
     Road roadTo(Junction j) {
		return outRoadByJunction.get(j);
    	 
     }
     
    



	@Override
	void advance(int time) {
		if(greenLightIndex != -1) {
			
		}
		
	}



	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
