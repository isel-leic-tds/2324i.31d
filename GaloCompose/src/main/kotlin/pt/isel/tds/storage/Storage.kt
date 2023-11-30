package pt.isel.tds.storage

/**
 * CRUD operations on a storage.
 * @param K the type of the key.
 * @param D the type of the data.
 */
interface Storage<K,D> {
    /**
     * Creates a new entry in the storage.
     * @param key the key of the entry.
     * @param data the data to store.
     * @throws IllegalStateException if the key already exists.
     */
    fun create(key: K, data: D)
    /**
     * Reads an entry from the storage.
     * @param key the key of the entry.
     * @return the data stored or null if the key does not exist.
     */
    fun read(key: K): D?
    /**
     * Updates an entry in the storage.
     * @param key the key of the entry.
     * @param data the new data to store.
     * @throws IllegalStateException if the key does not exist.
     */
    fun update(key: K, data: D)
    /**
     * Deletes an entry from the storage.
     * @param key the key of the entry.
     * @throws IllegalStateException if the key does not exist.
     */
    fun delete(key: K)
}

// In the game of tic-tac-toe it will be: Storage<String,Game>