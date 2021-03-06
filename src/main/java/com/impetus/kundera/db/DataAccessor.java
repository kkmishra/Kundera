/*******************************************************************************
 * * Copyright 2011 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera.db;

import java.util.List;
import java.util.Map;

import com.impetus.kundera.metadata.EntityMetadata;
import com.impetus.kundera.proxy.EnhancedEntity;

/**
 * Interface to define contract between @Entity and Cassandra data units.
 *
 * @author animesh.kumar
 * @since 0.1
 */
public interface DataAccessor
{

    /**
     * Write an entity to Cassandra DB.
     *
     * @param e
     *            EnhancedEntity
     * @param m
     *            Metadata
     * @throws Exception
     *             the exception
     */
    void write(EnhancedEntity e, EntityMetadata m) throws Exception;

    /**
     * Read an entity of type clazz with primaryKey id from Cassandra DB.
     *
     * @param <E>
     *            Generic type of Entity
     * @param clazz
     *            Entity class
     * @param m
     *            Metadata
     * @param id
     *            Primary Key
     * @return the entity or null if no such entity
     * @throws Exception
     *             the exception
     */
    <E> E read(Class<E> clazz, EntityMetadata m, String id) throws Exception;

    /**
     * Read.
     *
     * @param <E>
     *            the element type
     * @param clazz
     *            the clazz
     * @param m
     *            the m
     * @param col
     *            the col
     * @return the list
     * @throws Exception
     *             the exception
     */
    <E> List<E> read(Class<E> clazz, EntityMetadata m, Map<String, String> col) throws Exception;

    /**
     * Reads a list of entities of type clazz with primaryKeys ids from
     * Cassandra DB.
     *
     * @param <E>
     *            Generic type of Entity
     * @param clazz
     *            Entity class
     * @param m
     *            Metadata
     * @param ids
     *            Primary Keys
     * @return the list
     * @throws Exception
     *             the exception
     */
    <E> List<E> read(Class<E> clazz, EntityMetadata m, String... ids) throws Exception;

    /**
     * Delete an entity from Cassandra DB.
     *
     * @param e
     *            EnhancedEntity
     * @param m
     *            Metadata
     * @throws Exception
     *             the exception
     */
    void delete(EnhancedEntity e, EntityMetadata m) throws Exception;
}
