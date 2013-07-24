package system.exportStrategy;

public class StrategyFactory {
	public static ExportStrategy getExportStrategy(String typename) {
		ExportStrategy strategy = null;
		try {
			//strategy = (ExportStrategy) Class.forName("system."+typename+"."+typename).newInstance();
			strategy = (ExportStrategy) Class.forName(typename+"."+typename).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return strategy;
	}
	
}
