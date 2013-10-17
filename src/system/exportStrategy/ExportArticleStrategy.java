package system.exportStrategy;

import java.util.List;

import system.model.ArticleToken;
import system.model.StrategyToken;

/**
 * export articles strategy
 * @author hwj
 *
 */
public interface ExportArticleStrategy<T extends ArticleToken> extends ExportStrategy<StrategyToken>
{
	public List<T> getArticleTokens();
}
