package com.dr1.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        String jdbcUrl = option("gen.jdbcUrl", "GEN_JDBC_URL", defaultJdbcUrl());
        String username = option("gen.username", "GEN_USERNAME", "sa");
        String password = option("gen.password", "GEN_PASSWORD", "");
        String author = option("gen.author", "GEN_AUTHOR", "DR1");
        String parentPackage = option("gen.parentPackage", "GEN_PARENT_PACKAGE", "com.dr1");
        String moduleName = option("gen.module", "GEN_MODULE", "system");
        String outputDir = option("gen.outputDir", "GEN_OUTPUT_DIR", defaultOutputDir());
        String xmlDir = option("gen.xmlDir", "GEN_XML_DIR", outputDir + "/mapper");
        String[] tables = split(option("gen.tables", "GEN_TABLES", "sys_user"));
        String[] tablePrefixes = split(option("gen.tablePrefix", "GEN_TABLE_PREFIX", "sys_"));

        try {
            FastAutoGenerator.create(jdbcUrl, username, password)
                    .globalConfig(builder -> builder
                            .author(author)
                            .outputDir(outputDir)
                            .disableOpenDir()
                            .commentDate("yyyy-MM-dd"))
                    .packageConfig(builder -> builder
                            .parent(parentPackage)
                            .moduleName(moduleName)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlDir)))
                    .strategyConfig(builder -> builder
                            .addInclude(tables)
                            .addTablePrefix(tablePrefixes)
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList())
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        } finally {
            shutdownMysqlCleanupThread();
        }

        System.out.println("代码生成完成，输出目录：" + outputDir);
    }

    private static String option(String propertyKey, String envKey, String defaultValue) {
        String value = System.getProperty(propertyKey);
        if (value == null || value.isBlank()) {
            value = System.getenv(envKey);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static String[] split(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .toArray(String[]::new);
    }

    private static String defaultOutputDir() {
        return Paths.get(System.getProperty("user.dir"), "target", "generated-code").toString();
    }

    private static String defaultJdbcUrl() {
        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path script = baseDir.resolve("src/main/resources/generator-schema.sql");
        if (!Files.exists(script)) {
            script = baseDir.resolve("dr1-generator/src/main/resources/generator-schema.sql");
        }
        String scriptPath = script.toAbsolutePath().toString().replace("\\", "/");
        return "jdbc:h2:mem:dr1-generator;MODE=MySQL;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM '" + scriptPath + "'";
    }

    private static void shutdownMysqlCleanupThread() {
        try {
            Class<?> cleanupThread = Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread");
            cleanupThread.getMethod("checkedShutdown").invoke(null);
        } catch (ReflectiveOperationException ignored) {
            // MySQL driver is optional for the default H2 generator demo.
        }
    }
}
