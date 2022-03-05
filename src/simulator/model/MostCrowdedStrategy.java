package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int  timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;

	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(qs.isEmpty() ) {
			return -1;
		}
		else if(currGreen==-1) {
			int size =0;
			int count=0;
			int green=0;
			for (List<Vehicle> list : qs) {
					
				if (size<list.size()) {
					size=list.size();
					green=count;
				}	
				count++;
			}
			return green;
		}
		else if (currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		
		else {
			int size = 0;
			int green=0;
			for (int i=0;i<qs.size();i++) {
				int offset = (i+currGreen+1)%qs.size();
				if(qs.get(offset).size()>size) {
					size=qs.get(offset).size();
					green=offset;
				}
			}
			return green;
			
		}
			
				
	}
}


