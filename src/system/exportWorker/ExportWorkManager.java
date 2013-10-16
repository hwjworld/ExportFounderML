package system.exportWorker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ArrayUtils;

import system.exportStrategy.ExportArticleStrategy;
import system.exportStrategy.ExportNodeStrategy;
import system.exportStrategy.ExportStrategy;
import system.model.ArticleToken;
import system.model.StrategyToken;

public class ExportWorkManager {

	private static ExportStrategy<StrategyToken> strategy = null;
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 55,
			1000 * 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000),
			new ThreadPoolExecutor.CallerRunsPolicy());
	public ExportWorkManager(ExportStrategy<StrategyToken> strategy) {
		ExportWorkManager.strategy = strategy;
	}
	private static int count = 0;
	
	public void startWork() {
		if(strategy == null) return;
		List<StrategyToken> tokens = new ArrayList<StrategyToken>();
		while(true){

			if(strategy instanceof ExportNodeStrategy){
				List<ArticleToken> tmplist = ((ExportNodeStrategy) strategy).getNodeTokens();
				if(tmplist != null){
					tokens.addAll(tmplist);
				}
			}
			if(strategy instanceof ExportArticleStrategy){
				List<ArticleToken> tmplist = ((ExportArticleStrategy) strategy).getArticleTokens();
				if(tmplist != null){
					tokens.addAll(tmplist);
				}
			}
			Iterator<StrategyToken> it = tokens.iterator();
			while(it.hasNext()){
				count++;
				executor.execute(new ExportRunner(strategy, it.next()));
				System.out.println("count: "+count);
			}
		}
		
	}
}
