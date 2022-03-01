package simulator.model;

public abstract class NewRoadEvent extends Event {
	protected int length,co2limit, maxSpeed;
	protected String id, srcJun, destJunc;
	protected Weather weather;
	
	
	

	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, 
			int co2Limit, int maxSpeed, Weather weather)
	{ 	
		super(time);
		this.id=id;
		this.length=length;
		this.co2limit=co2Limit;
		this.maxSpeed=maxSpeed;
		this.srcJun=srcJun;
		this.destJunc=destJunc;
		this.weather=weather;
	}
	
	@Override
	public abstract void execute(RoadMap map) ;


	
	
}
