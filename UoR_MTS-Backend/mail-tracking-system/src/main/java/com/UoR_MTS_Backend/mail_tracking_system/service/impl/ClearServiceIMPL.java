package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import ch.qos.logback.classic.Logger;
import com.UoR_MTS_Backend.mail_tracking_system.service.ClearService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class ClearServiceIMPL implements ClearService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String clearDatabase() {
        return null;
    }

}
