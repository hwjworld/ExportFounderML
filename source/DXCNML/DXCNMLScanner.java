package DXCNML;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

import com.founder.enp.dataportal.util.FileUtils;
import com.founder.enp.dataportal.util.StringUtils;

public class DXCNMLScanner {

	private static final DXCNMLFilenameFilter dxfilter = new DXCNMLFilenameFilter();

	private final static String dxcnml_config = "DXCNML.ecfg";
	private static String dxcnml_dir = null;
	private static Properties props = null;

	/**
	 * 返回扫描到的第一个cnml.xml文件
	 * 
	 * @return
	 */
	public static String scanFirstCNMLFile() {

		if (dxcnml_dir == null) {
			dxcnml_dir = getConfigPropertie("dxcnml_dir");
		}
		String[] files = FileUtils.listFiles(dxcnml_dir, dxfilter);
		if (files != null && files.length > 0
				&& !StringUtils.isNull(files[0], true)) {
			return files[0];
		}
		return null;
	}

	private static String getConfigPropertie(String key) {
		if (props == null)
			openConfig();
		return props.getProperty(key);
	}

	private static Properties openConfig() {
		try {
			props = Resources.getResourceAsProperties(dxcnml_config);
		} catch (IOException e) {
			System.err.println("相关配置不正确，请检查 " + dxcnml_config);
			System.exit(1);
		}
		return props;
	}
	
	public static String getDXCNML_DIR() {
		return dxcnml_dir;
	}
}

class DXCNMLFilenameFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		boolean bl = false;
		File currFile = new File(dir, name);
		if (currFile.isFile()) {
			if (!StringUtils.isNull(name) && name.endsWith(".cnml.xml")) {
				bl = true;
			}
		} else {
			bl = true;
		}
		return bl;
	}
}