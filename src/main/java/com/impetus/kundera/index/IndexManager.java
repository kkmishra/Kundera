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
package com.impetus.kundera.index;

import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.impetus.kundera.Constants;
import com.impetus.kundera.ejb.EntityManagerImpl;
import com.impetus.kundera.loader.DBType;
import com.impetus.kundera.metadata.EntityMetadata;
import com.impetus.kundera.mongodb.query.MongoDBIndexer;
import com.impetus.kundera.property.PropertyAccessorHelper;

/**
 * Manager responsible to co-ordinate with an Indexer. It is bound with
 * EntityManager.
 *
 * @author animesh.kumar
 */
public class IndexManager
{

    /** The indexer. */
    private Indexer indexer;

    /**
     * The Constructor.
     *
     * @param manager
     *            the manager
     */
    @SuppressWarnings("deprecation")
    public IndexManager(EntityManagerImpl manager)
    {
        DBType dbType = manager.getClient().getType();
        if (dbType.MONGODB.equals(dbType))
        {
            indexer = new MongoDBIndexer(manager.getClient());
        }
        else
        {
            indexer = new KunderaIndexer(manager.getClient(), new StandardAnalyzer(Version.LUCENE_CURRENT));
        }

    }

    /**
     * Removes an object from Index.
     *
     * @param metadata
     *            the metadata
     * @param entity
     *            the entity
     * @param key
     *            the key
     */
    public final void remove(EntityMetadata metadata, Object entity, String key)
    {
        try
        {
            indexer.unindex(metadata, key);
        }
        catch (Exception e)
        {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Updates the index for an object.
     *
     * @param metadata
     *            the metadata
     * @param entity
     *            the entity
     */
    public final void update(EntityMetadata metadata, Object entity)
    {
        try
        {
            String id = PropertyAccessorHelper.getId(entity, metadata);
            indexer.unindex(metadata, id);
            indexer.index(metadata, entity);
        }
        catch (Exception e)
        {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Indexes an object.
     *
     * @param metadata
     *            the metadata
     * @param entity
     *            the entity
     */
    public final void write(EntityMetadata metadata, Object entity)
    {
        indexer.index(metadata, entity);
    }

    /**
     * Searches on the index. Note: Query must be in Indexer's understandable
     * format
     *
     * @param query
     *            the query
     * @return the list
     */
    public final Map<String, String> search(String query)
    {
        return search(query, Constants.INVALID, Constants.INVALID);
    }

    /**
     * Search.
     *
     * @param query
     *            the query
     * @param count
     *            the count
     * @return the list
     */
    public final Map<String, String> search(String query, int count)
    {
        return search(query, Constants.INVALID, count);
    }

    /**
     * Search.
     *
     * @param query
     *            the query
     * @param start
     *            the start
     * @param count
     *            the count
     * @return the list
     */
    public final Map<String, String> search(String query, int start, int count)
    {
        return indexer.search(query, start, count);
    }

}
