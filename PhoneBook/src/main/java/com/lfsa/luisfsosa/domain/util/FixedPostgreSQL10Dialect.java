/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.lfsa.luisfsosa.domain.util;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.Types;

/**
 * The Class FixedPostgreSQL10Dialect.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@SuppressWarnings("squid:S110")
public class FixedPostgreSQL10Dialect extends PostgreSQL10Dialect {

    /**
     * Instantiates a new fixed postgre SQL 10 dialect.
     */
    public FixedPostgreSQL10Dialect() {
        super();
        registerColumnType(Types.BLOB, "bytea");
    }

    /**
     * Remap sql type descriptor.
     *
     * @param sqlTypeDescriptor the sql type descriptor
     * @return the sql type descriptor
     */
    @Override
    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (sqlTypeDescriptor.getSqlType() == java.sql.Types.BLOB) {
            return BinaryTypeDescriptor.INSTANCE;
        }
        return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }
}