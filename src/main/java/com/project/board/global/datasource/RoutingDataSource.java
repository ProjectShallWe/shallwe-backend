package com.project.board.global.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.project.board.global.datasource.ReplicationRoutingConstants.MASTER;
import static com.project.board.global.datasource.ReplicationRoutingConstants.SLAVE;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            logger.info("Connect SLAVE");
            return SLAVE.type;
        } else {
            logger.info("Connect MASTER");
            return MASTER.type;
        }
    }
}
