package system.controller;

import org.dom4j.DocumentException;

import system.exportStrategy.ExportStrategy;
import system.exportStrategy.StrategyFactory;
import system.exportWorker.ExportWorkManager;
import system.repository.Config;

public class Application {

	public Application() throws DocumentException {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// LogHandler handler = new LogHandler();
		// ExportStrategy esProxy = (ExportStrategy)
		// handler.bind(StrategyFactory
		// .getExportStrategy(Config.getConfig().getConfigProperty(
		// "sourceDB")));

		// esProxy.exportXML();
		ExportStrategy exportStrategy = StrategyFactory.getExportStrategy(
				Config.getConfig().getConfigProperty("sourceDB"));
		
		ExportWorkManager worker = new ExportWorkManager(exportStrategy);
		worker.startWork();
//		exportStrategy.exportXML();
		
	}

}
