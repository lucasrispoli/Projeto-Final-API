package org.serratec.backend.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;



@Component
public class PostgreSQLAuditor {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void definirUsuario(String usuario) {

        entityManager
                .createNativeQuery("SELECT set_config('app.current_user', ?, true)")
                .setParameter(1, usuario)
                .getSingleResult();
    }
}
