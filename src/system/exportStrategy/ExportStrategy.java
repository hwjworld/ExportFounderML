package system.exportStrategy;

import system.model.StrategyToken;


/**
 * @author Administrator
 * @version 1.0
 * @created 26-╬етб-2010 11:24:37
 */
public interface ExportStrategy<T extends StrategyToken> {

	/**
	 * export one XML file
	 */
	public void exportXML(T token);

}