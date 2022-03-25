package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if(cs.isEmpty()) {
			throw new IllegalArgumentException("List of Pair<String, Integer> is null");
		}
		else {
		this.cs=cs;
		}
	}

	@Override
	public void execute(RoadMap map)  {
		for( Pair<String,Integer> p_cs : cs ) {
			Vehicle vehicle = map.getVehicle(p_cs.getFirst());
			if(vehicle == null) {
				throw new IllegalArgumentException("this Vehicle does not exist in map");
			}else {
				
				vehicle.setContClass(p_cs.getSecond());
			}
		
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
