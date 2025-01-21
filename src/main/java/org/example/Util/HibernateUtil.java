package org.example.Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    /**
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }


}
