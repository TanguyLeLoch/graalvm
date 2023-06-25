package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.application.TransactionService;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileScanner {

    protected final TransactionService transactionService;

    private final String folder;

    private final FileListener fileListener;

    public FileScanner(TransactionService transactionService, @Value("${tx-eater.folder}") String folder, @Value("${tx-eater.polling-interval}") long pollingInterval) throws Exception {
        this.transactionService = transactionService;
        this.folder = folder;
        FileAlterationObserver observer = new FileAlterationObserver(folder + "/new");

        this.fileListener = new FileListener();
        observer.addListener(this.fileListener);

        FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInterval);
        monitor.addObserver(observer);
        monitor.start();
    }

    public FileListener getFileListener() {
        return fileListener;
    }

    public class FileListener extends FileAlterationListenerAdaptor {

        @Override
        public void onFileCreate(File file) {
            processFile(file);
            markAsProcessed(file);
        }

        public void processFile(File file) {
            System.out.println("Processing file: " + file.getAbsolutePath());
            List<Transaction> transactions = new ArrayList<>();

            try (Reader reader = new FileReader(file);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {


                for (CSVRecord csvRecord : csvParser) {
                    if (csvRecord.getRecordNumber() == 1) {
                        continue;
                    }
                    Transaction transaction = TransactionCsvRecordMapper.map(csvRecord, true, List.of("Approve"));
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileScanner.this.transactionService.ingestTransactionsFromCsv(transactions);
        }

        private void markAsProcessed(File file) {
            try {
                File processedFolder = new File(folder + "/processed");
                FileUtils.moveFileToDirectory(file, processedFolder, true);
                System.out.println("Marked file as processed: " + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}