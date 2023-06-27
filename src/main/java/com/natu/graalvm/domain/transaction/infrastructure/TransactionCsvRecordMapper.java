package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class TransactionCsvRecordMapper {

    private static final int HASH_INDEX = 0;
    private static final int FROM_INDEX = 4;
    private static final int TO_INDEX = 5;
    private static final int BLOCK_NUMBER_INDEX = 1;
    private static final int METHOD_NAME_INDEX = 15;
    private static final int STATUS_INDEX = 13;

    public static Transaction map(CSVRecord csvRecord, boolean filterError, List<String> filterMethods) {
        if (filterError && !csvRecord.get(STATUS_INDEX).isBlank()) {
            return null;
        }
        if (filterMethods != null && !filterMethods.isEmpty()
                && !filterMethods.contains(csvRecord.get(METHOD_NAME_INDEX))) {
            return null;
        }
        return new Transaction(csvRecord.get(HASH_INDEX),
                csvRecord.get(FROM_INDEX),
                csvRecord.get(TO_INDEX),
                Long.parseLong(csvRecord.get(BLOCK_NUMBER_INDEX)), null, 0L);
    }
}
