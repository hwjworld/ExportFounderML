package system.exportWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import system.exportStrategy.ExportArticleStrategy;
import system.exportStrategy.ExportNodeStrategy;
import system.exportStrategy.ExportStrategy;
import system.model.ArticleToken;
import system.model.StrategyToken;

public class ExportWorkManager {

	private static ExportStrategy<StrategyToken> strategy = null;
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50,
			1000 * 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000),
			new ThreadPoolExecutor.CallerRunsPolicy());
	public ExportWorkManager(ExportStrategy<StrategyToken> strategy) {
		ExportWorkManager.strategy = strategy;
	}
	/**
	 * 已经交给导出线程的token
	 */
	private static final HashMap<String, Boolean> workedToken = new HashMap<String, Boolean>();
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
			if(tokens.size()==0){
				System.out.println("完成导出程序");
				pause(60);
				
			}
			for (StrategyToken token : tokens) {
				if(addToWorkingToken(token.getID())){
					executor.execute(new ExportRunner(strategy, token));
					System.out.println("读取第[ "+ ++count +" ]个任务");
				}
			}

			tokens.clear();
			pause(5);
		}
		
	}
	
	public static boolean containInWorkedToken(String token){
		return workedToken.containsKey(token);
	}
	public static boolean addToWorkingToken(String token){
		if(workedToken.containsKey(token)){
			return false;
		}
		workedToken.put(token, true);
		return true;
	}
	public static void removeFromWrokintToken(String token){
		workedToken.remove(token);
	}
	

	protected static void pause(int num){
		try {
			Thread.sleep(num*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
