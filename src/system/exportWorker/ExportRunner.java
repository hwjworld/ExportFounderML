package system.exportWorker;

import system.exportStrategy.ExportStrategy;
import system.model.StrategyToken;

public class ExportRunner implements Runnable{

	private ExportStrategy<StrategyToken> strategy = null;
	private StrategyToken token = null;
	public ExportRunner(ExportStrategy<StrategyToken> strategy, StrategyToken token) {
		this.strategy = strategy;
		this.token = token;
	}
	public void run() {
		strategy.exportXML(token);
	}

}
