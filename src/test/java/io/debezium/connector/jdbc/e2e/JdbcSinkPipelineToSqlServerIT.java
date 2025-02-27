/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.jdbc.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import io.debezium.connector.jdbc.junit.jupiter.SqlServerSinkDatabaseContextProvider;
import io.debezium.connector.jdbc.junit.jupiter.e2e.source.Source;
import io.debezium.connector.jdbc.junit.jupiter.e2e.source.SourceType;

/**
 * Implementation of the JDBC sink connector multi-source pipeline that writes to SQL Server.
 *
 * @author Chris Cranford
 */
@Tag("all")
@Tag("e2e")
@Tag("e2e-sqlserver")
@ExtendWith(SqlServerSinkDatabaseContextProvider.class)
public class JdbcSinkPipelineToSqlServerIT extends AbstractJdbcSinkPipelineIT {

    @Override
    protected String getBooleanType() {
        return "BIT";
    }

    @Override
    protected String getBitsDataType() {
        return "VARBINARY";
    }

    @Override
    protected String getInt8Type() {
        return "SMALLINT";
    }

    @Override
    protected String getInt16Type() {
        return "SMALLINT";
    }

    @Override
    protected String getInt32Type() {
        return "INT";
    }

    @Override
    protected String getInt64Type() {
        return "BIGINT";
    }

    @Override
    protected String getVariableScaleDecimalType() {
        return "FLOAT";
    }

    @Override
    protected String getDecimalType() {
        return "DECIMAL";
    }

    @Override
    protected String getFloat32Type() {
        return "REAL";
    }

    @Override
    protected String getFloat64Type() {
        return "FLOAT";
    }

    @Override
    protected String getCharType(Source source, boolean key, boolean nationalized) {
        if (source.getType().is(SourceType.MYSQL)) {
            // always emits utf8 indicator when nationalized columns used
            if (source.getOptions().isColumnTypePropagated() && !key) {
                return nationalized ? "NCHAR" : "CHAR";
            }
            return nationalized ? "NVARCHAR" : "VARCHAR";
        }
        else {
            if (source.getOptions().isColumnTypePropagated() && !key) {
                // Debezium does not propagate column type details for keys.
                return !nationalized ? "CHAR" : "NCHAR";
            }
            return "VARCHAR";
        }
    }

    @Override
    protected String getStringType(Source source, boolean key, boolean nationalized, boolean maxLength) {
        if (source.getType().is(SourceType.MYSQL)) {
            return nationalized ? "NVARCHAR" : "VARCHAR";
        }
        else if (source.getOptions().isColumnTypePropagated() && !key && nationalized) {
            return "NVARCHAR";
        }
        return "VARCHAR";
    }

    @Override
    protected String getTextType(boolean nationalized) {
        return "VARCHAR";
    }

    @Override
    protected String getBinaryType(Source source, String sourceDataType) {
        return "VARBINARY";
    }

    @Override
    protected String getJsonType(Source source) {
        return getStringType(source, false, false);
    }

    @Override
    protected String getXmlType(Source source) {
        if (source.getType() == SourceType.POSTGRES || source.getOptions().isColumnTypePropagated()) {
            return "XML";
        }
        return "VARCHAR";
    }

    @Override
    protected String getUuidType(Source source, boolean key) {
        return getStringType(source, key, false);
    }

    @Override
    protected String getEnumType(Source source, boolean key) {
        return getStringType(source, key, false);
    }

    @Override
    protected String getSetType(Source source, boolean key) {
        return getStringType(source, key, false);
    }

    @Override
    protected String getYearType() {
        return getInt32Type();
    }

    @Override
    protected String getDateType() {
        return "DATE";
    }

    @Override
    protected String getTimeType(Source source, boolean key, int precision) {
        return "DATETIME2";
    }

    @Override
    protected String getTimeWithTimezoneType() {
        return "DATETIME";
    }

    @Override
    protected String getTimestampType(Source source, boolean key, int precision) {
        return "DATETIME2";
    }

    @Override
    protected String getTimestampWithTimezoneType(Source source, boolean key, int precision) {
        return "DATETIMEOFFSET";
    }

    @Override
    protected String getIntervalType(Source source, boolean numeric) {
        return numeric ? getInt64Type() : getStringType(source, false, false);
    }
}
