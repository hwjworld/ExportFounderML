package system.exportWorker;

import system.exportStrategy.ExportStrategy;

public class ExportWorkManager {

	private static ExportStrategy strategy = null;
	public ExportWorkManager(ExportStrategy strategy) {
		ExportWorkManager.strategy = strategy;
	}
	
	public void startWork() {
		
	}
}
