package system.repository;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DaoConfig {

	private static String resource = Config.getConfig().getConfigProperty("sourceDB")+"/dao/Configuration.xml";

    private static SqlSessionFactory sessionFactory;

    static {
        try {
            sessionFactory = newDaoManager();
        } catch (Exception ex) {
            throw new RuntimeException("Description.  Cause: " + ex, ex);
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

	private static SqlSessionFactory newDaoManager() {
			try {
				Reader reader = Resources.getResourceAsReader(resource);
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			} catch (IOException e) {
			}
			return sessionFactory;
	}
}
