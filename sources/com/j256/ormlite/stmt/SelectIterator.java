package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class SelectIterator<T, ID> implements CloseableIterator<T> {
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) SelectIterator.class);
    private boolean alreadyMoved;
    private final Dao<T, ID> classDao;
    private boolean closed;
    private final CompiledStatement compiledStmt;
    private final DatabaseConnection connection;
    private final ConnectionSource connectionSource;
    private final Class<?> dataClass;
    private boolean first = true;
    private T last;
    private final DatabaseResults results;
    private int rowC;
    private final GenericRowMapper<T> rowMapper;
    private final String statement;

    public SelectIterator(Class<?> dataClass2, Dao<T, ID> classDao2, GenericRowMapper<T> rowMapper2, ConnectionSource connectionSource2, DatabaseConnection connection2, CompiledStatement compiledStmt2, String statement2, ObjectCache objectCache) throws SQLException {
        this.dataClass = dataClass2;
        this.classDao = classDao2;
        this.rowMapper = rowMapper2;
        this.connectionSource = connectionSource2;
        this.connection = connection2;
        this.compiledStmt = compiledStmt2;
        this.results = compiledStmt2.runQuery(objectCache);
        this.statement = statement2;
        if (statement2 != null) {
            logger.debug("starting iterator @{} for '{}'", (Object) Integer.valueOf(hashCode()), (Object) statement2);
        }
    }

    public boolean hasNextThrow() throws SQLException {
        boolean result;
        if (this.closed) {
            return false;
        }
        if (this.alreadyMoved) {
            return true;
        }
        if (this.first) {
            this.first = false;
            result = this.results.first();
        } else {
            result = this.results.next();
        }
        if (!result) {
            close();
        }
        this.alreadyMoved = true;
        return result;
    }

    public boolean hasNext() {
        try {
            return hasNextThrow();
        } catch (SQLException e) {
            this.last = null;
            closeQuietly();
            throw new IllegalStateException("Errors getting more results of " + this.dataClass, e);
        }
    }

    public T first() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.first()) {
            return getCurrent();
        }
        return null;
    }

    public T previous() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.previous()) {
            return getCurrent();
        }
        return null;
    }

    public T current() throws SQLException {
        if (this.closed) {
            return null;
        }
        if (this.first) {
            return first();
        }
        return getCurrent();
    }

    public T nextThrow() throws SQLException {
        boolean hasResult;
        if (this.closed) {
            return null;
        }
        if (!this.alreadyMoved) {
            if (this.first) {
                this.first = false;
                hasResult = this.results.first();
            } else {
                hasResult = this.results.next();
            }
            if (!hasResult) {
                this.first = false;
                return null;
            }
        }
        this.first = false;
        return getCurrent();
    }

    public T next() {
        SQLException sqlException = null;
        try {
            T result = nextThrow();
            if (result != null) {
                return result;
            }
        } catch (SQLException e) {
            sqlException = e;
        }
        this.last = null;
        closeQuietly();
        throw new IllegalStateException("Could not get next result for " + this.dataClass, sqlException);
    }

    public T moveRelative(int offset) throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.moveRelative(offset)) {
            return getCurrent();
        }
        return null;
    }

    public void removeThrow() throws SQLException {
        if (this.last == null) {
            throw new IllegalStateException("No last " + this.dataClass + " object to remove. Must be called after a call to next.");
        } else if (this.classDao == null) {
            throw new IllegalStateException("Cannot remove " + this.dataClass + " object because classDao not initialized");
        } else {
            try {
                this.classDao.delete(this.last);
            } finally {
                this.last = null;
            }
        }
    }

    public void remove() {
        try {
            removeThrow();
        } catch (SQLException e) {
            closeQuietly();
            throw new IllegalStateException("Could not delete " + this.dataClass + " object " + this.last, e);
        }
    }

    public void close() throws SQLException {
        if (!this.closed) {
            this.compiledStmt.close();
            this.closed = true;
            this.last = null;
            if (this.statement != null) {
                logger.debug("closed iterator @{} after {} rows", (Object) Integer.valueOf(hashCode()), (Object) Integer.valueOf(this.rowC));
            }
            this.connectionSource.releaseConnection(this.connection);
        }
    }

    public void closeQuietly() {
        try {
            close();
        } catch (SQLException e) {
        }
    }

    public DatabaseResults getRawResults() {
        return this.results;
    }

    public void moveToNext() {
        this.last = null;
        this.first = false;
        this.alreadyMoved = false;
    }

    private T getCurrent() throws SQLException {
        this.last = this.rowMapper.mapRow(this.results);
        this.alreadyMoved = false;
        this.rowC++;
        return this.last;
    }
}
