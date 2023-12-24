package br.com.bagnascojhoel.kwik_ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class ApplicationServiceConfiguration {

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new AbstractPlatformTransactionManager() {

            @Override
            protected Object doGetTransaction() throws TransactionException {
                return new FakeTransaction();
            }

            @Override
            protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
                ((FakeTransaction) transaction).began = true;
                ((FakeTransaction) transaction).isReadOnly = definition.isReadOnly();
            }

            @Override
            protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
                ((FakeTransaction) status.getTransaction()).committed = true;
            }

            @Override
            protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
                ((FakeTransaction) status.getTransaction()).rollBacked = true;
            }
        };
    }

    private static final class FakeTransaction {
        boolean began;
        boolean committed;
        boolean rollBacked;
        boolean isReadOnly;
    }

}
