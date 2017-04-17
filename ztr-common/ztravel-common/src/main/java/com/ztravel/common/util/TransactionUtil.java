/**
 *
 */
package com.ztravel.common.util;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author zuoning.shen
 *
 */
public class TransactionUtil {
    public static void afterCommit(TransactionSynchronizationAdapter adapter) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(adapter);
        } else {
            adapter.afterCommit();
        }
    }
}
