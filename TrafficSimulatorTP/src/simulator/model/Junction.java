package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
	
	
	
     public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)  {
    	 super(id);
    	 if(lsStrategy != null && dqStrategy != null) {
    		this.lss = lsStrategy;
    		this.dqs = dqStrategy;
    	 }
    	 else {
    		 throw new IllegalArgumentException("LightSwitchingStrategy or DequeuingStrategy is null ");
			
		}
    	 
    	 if(xCoor >=0 && yCoor >=0) {
    		 this.x = xCoor;
    		 this.y = yCoor;
    	 }
    	 else {
    		 throw new IllegalArgumentException("xCoor or yCoor is negative");
    	 }
    	 this.queues= new ArrayList<>();
    	 this.queueByRoad=new HashMap<>();
    	 this.outRoadByJunction= new HashMap<>();
    	 inRoads= new ArrayList<>() ;
    	 this.greenLightIndex=-1;
    	 this.lastSwitchingTime=0;
    
    	 }
     
   
    
    
     
     void addIncommingRoad(Road r)  {
    	 if(r.getDest().equals(this)) {
    		 inRoads.add(r);
    		 LinkedList<Vehicle> queue = new LinkedList<Vehicle>();
    		 queues.add(queue);
    		 queueByRoad.put(r, queue);
    		 
    	 }
    	 else {
    		 throw new IllegalArgumentException("destination junction is not  equal to the current junction");
    	 }
     }
     
     void addOutGotingRoad(Road r)  {
    	 if(r.getSrc().equals(this)) {
    		 outRoadByJunction.put(r.getDest(),r);
    		 
    		 
    	 }
    	 else {
    		 throw new IllegalArgumentException("Other road go junction or that road is not outgoing road");
    	 }
    	 
     }
     
     void enter(Vehicle v) {
    	 queues.get(inRoads.indexOf(v.getRoad())).add(v);
     }
     
     public Road roadTo(Junction j) {
		return outRoadByJunction.get(j);
    	 
     }
     
    

   

	@Override
	void advance(int time)  {
		if(greenLightIndex != -1) {
			
			
			List<Vehicle> supprotList1 = queues.get(greenLightIndex);
			List<Vehicle> supprotList=dqs.dequeue(supprotList1);
			
			for(Vehicle v : supprotList) {
				
				v.moveToNextRoad();
				supprotList1.remove(v);
				
			}
		}
		int status_light = lss.chooseNextGreen(inRoads, queues, greenLightIndex, lastSwitchingTime, time);
			 if (status_light != greenLightIndex) {
				 
				 greenLightIndex=status_light;
				 lastSwitchingTime = time;
			 }
			
			
		}
		
	
	  public List<Road> getInRoads() {
	 		return inRoads;
	 	}
	     
	    public int getGreenLightIndex() {
			return greenLightIndex;
		}
	    
	    @SuppressWarnings("unchecked")
		public List<List<Vehicle>> getQueues() {
	    	return  Collections.unmodifiableList(new ArrayList(queues));
		}


	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		if(getGreenLightIndex()==-1) {
			obj.put("green", "none");
		}
		else {
			
			obj.put("green", getInRoads().get(greenLightIndex).getId());
		}
		JSONArray array = new JSONArray();
		for (int i=0;i<getQueues().size();i++) {
			JSONObject road = new JSONObject();
			road.put("road", getInRoads().get(i).getId());
			JSONArray queuesArray = new JSONArray();
			for(Vehicle vehicle : getQueues().get(i)) {
				queuesArray.put(vehicle.getId());
			}
			road.put("vehicles", queuesArray);
			array.put(road);
		}
		obj.put("queues", array);
		
		
		return obj;
	}

	
}
