package system.exportStrategy;

import java.util.List;

import system.model.NodeToken;
import system.model.StrategyToken;

/**
 * export nodes strategy
 * @author hwj
 *
 */
public interface ExportNodeStrategy<T extends NodeToken> extends ExportStrategy<StrategyToken> {

	public List<T> getNodeTokens();
}
