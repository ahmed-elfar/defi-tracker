package com.xyvo.defi.standalone;


import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;


final class ImportExportRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImportExportRunner.class);

    private final String url;
    private final String user;
    private final String password;
    private final String dumpScriptPath;

    private static final int MAX_ATTEMPTS = 30;
    private static final long WAIT_TIME = 200;
    private final String userPath;
    private final String dataPath;

    private static ImportExportRunner instance;

    private ImportExportRunner(Environment env) {
        url  = env.getProperty("spring.datasource.url");
        user = env.getProperty("spring.datasource.username");
        password = env.getProperty("spring.datasource.password");
        dumpScriptPath = env.getProperty("dump.script.path");
        userPath = dumpScriptPath + File.separator + "user.sql";
        dataPath = dumpScriptPath + File.separator + "data.sql";
    }

    static void create(Environment env) {
        if(instance == null){
            synchronized (ImportExportRunner.class) {
                if(instance == null) {
                    instance = new ImportExportRunner(env);
                }
            }
        }
    }

    static ImportExportRunner getInstance() {
        if(instance == null) {
            throw new NullPointerException("Standalone mode is disabled.");
        }
        return instance;
    }

    void runExportScript() {
        LOG.info("Shutdown preprocess.");
        LOG.info("Exporting tables");
        try {
            Script.process(url, user, password, userPath,
                    "SIMPLE COLUMNS NOPASSWORDS NOSETTINGS",
                    getUserTable());
            Script.process(url, user, password, dataPath,
                    "SIMPLE COLUMNS NOPASSWORDS NOSETTINGS",
                    getTables());
            File dataSql = new File(dataPath);
            int attempts = 0;
            while (!dataSql.exists() && attempts < MAX_ATTEMPTS) {
                attempts++;
                Thread.sleep(WAIT_TIME);
            }
            if (!dataSql.exists()) {
                throw new RuntimeException("Export process took too long.");
            }
            List<String> userInserts = getInsertOnly(userPath);
            List<String> allInserts = getInsertOnly(dataPath);
            userInserts.addAll(allInserts);
            Files.write(Paths.get(dataPath), userInserts, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            Files.deleteIfExists(Paths.get(userPath));
        } catch (Throwable t) {
            LOG.error("Failed to export tables. ", t);
            return;
        }
        LOG.info("Exported tables successfully.");
    }

    private List<String> getInsertOnly(String dataPath) throws IOException {
        Path scriptPath = Paths.get(dataPath);
        List<String> queries = Files.readAllLines(scriptPath);
        queries = queries.stream().filter(query -> {
            String q = query.trim();
            return (q.startsWith("--") || q.startsWith("INSERT"));
        })/*.map(query -> query + System.lineSeparator())*/.collect(Collectors.toList());
        return queries;
    }

    private String getUserTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("table ");
        sb.append("DOMAIN.USER");
        return sb.toString();
    }

    private String getTables() {
        StringBuilder sb = new StringBuilder();
        sb.append("table ");

        //sb.append("NETWORK.NETWORK, ");
        //sb.append("NETWORK.DEX, ");

        //sb.append("DOMAIN.USER, ");
        sb.append("DOMAIN.SETTINGS, ");
        sb.append("DOMAIN.ADDRESS, ");

        sb.append("TRANSACTIONS.TRANSACTION");
        return sb.toString();
    }

    void runImportScript(DataSource dataSource) {
        LOG.info("Importing tables");
        File dumpFile = new File(dataPath);
        if (!dumpFile.exists()) {
            LOG.warn("No such file. Failed to run import sql script @{}", dataPath);
            return;
        }

        try(Connection connection = dataSource.getConnection(); Reader reader = new FileReader(dataPath);){
            RunScript.execute(connection, reader);
        } catch (Throwable t) {
            LOG.error("Failed to import tables.", t);
            throw new RuntimeException("Failed to import tables.");
        }
        LOG.info("Imported tables successfully.");
    }
}
