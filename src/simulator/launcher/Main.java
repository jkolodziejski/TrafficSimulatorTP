package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.MainWindow;


public class Main {

	enum ExeMode{GUI, BATCH};
	private static ExeMode _mode ;
	
	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static Integer _timeLimit;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			ConsoleOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			TicksSimulator(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();
		
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Console").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator???s main loop (default\n"
				+ "value is 10).").build());


		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode == ExeMode.BATCH) {
			throw new ParseException("An events file is missing");
		}
		else {
			_inFile=null;
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if(_mode != ExeMode.GUI) {
		_outFile = line.getOptionValue("o");
		}
	}
	
	private static void TicksSimulator(CommandLine line) throws ParseException {
		if(line.getOptionValue("t") != null) {
			_timeLimit = Integer.parseInt(line.getOptionValue("t"));
		}else {
			_timeLimit=_timeLimitDefaultValue;
		}
	}
	
	private static void ConsoleOption(CommandLine line) throws ParseException {
		
		if(line.getOptionValue("m") == null || line.getOptionValue("m") == "gui") {
			_mode = ExeMode.GUI;
			
		}else {
			_mode=ExeMode.BATCH;
		}
	}
		

	private static void initFactories() {

		// TODO complete this method to initialize _eventsFactory
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		 lsbs.add( new RoundRobinStrategyBuilder() );
		 lsbs.add( new MostCrowdedStrategyBuilder() );
		 Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		 
		 List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		 dqbs.add( new MoveFirstStrategyBuilder() );
		 dqbs.add( new MoveAllStrategyBuilder() );
		 Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		  
		  List<Builder<Event>> ebs = new ArrayList<>();
		  ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory));
		  ebs.add( new NewCityRoadEventBuilder() );
		  ebs.add( new NewInterCityRoadEventBuilder() );
		  ebs.add(new NewVehicleEventBuilder());
		  ebs.add(new SetWeatherEventBuilder());
		  ebs.add(new SetContClassEventBuilder());
		  
		  Factory<Event> eventsFactory = new BuilderBasedFactory<>(ebs);
		  
		  _eventsFactory=eventsFactory;
		  
		  
	}
	
	private static void startGUIMode() throws IOException {
		 TrafficSimulator trafficSimulator = new TrafficSimulator();
		 Controller controller = new Controller(trafficSimulator, _eventsFactory);
		 if(_inFile != null) {
			 InputStream _in = new FileInputStream(_inFile);
			 controller.loadEvents(_in);
		 }
	
		SwingUtilities.invokeLater(() -> new MainWindow(controller));
		
		
	}

	private static void startBatchMode() throws IOException {
		
		 InputStream _in = new FileInputStream(_inFile);
		 OutputStream out;
		 if(_outFile != null) {
			  out = new FileOutputStream(_outFile);
			 
		 }
		 else {
			out = System.out;
		 }
		 TrafficSimulator trafficSimulator = new TrafficSimulator();
		 Controller controller = new Controller(trafficSimulator, _eventsFactory);
		 controller.loadEvents(_in);
		 _in.close();
		 controller.run(_timeLimit, out);
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		
		switch (_mode) {
		case GUI:
			startGUIMode();
			break;
		case BATCH :
			startBatchMode();
		default:
			break;
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help



	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
