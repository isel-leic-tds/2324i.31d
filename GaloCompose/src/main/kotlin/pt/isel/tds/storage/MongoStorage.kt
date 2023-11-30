package pt.isel.tds.storage

import com.mongodb.MongoWriteException

class MongoStorage<Key,Data>(
    driver: MongoDriver,
    collectionName: String,
    val serializer: Serializer<Data>
) : Storage<Key,Data> {

    data class Doc(val _id: String, val value: String)

    val col = driver.getCollection<Doc>(collectionName)

    private fun Doc(key: Key, data: Data) =
        Doc(key.toString(), serializer.serialize(data))

    override fun create(key: Key, data: Data) {
        try { col.insertDocument( Doc(key, data) ) }
        catch (e: MongoWriteException) {
            error("Key $key already exists")
        }
    }

    override fun read(key: Key): Data? =
        col.getDocument(key.toString())?.let { serializer.deserialize(it.value) }

    override fun update(key: Key, data: Data) {
        check(col.replaceDocument(key.toString(), Doc(key, data))) {
            "Key $key does not exist"
        }
    }

    override fun delete(key: Key) {
        check(col.deleteDocument(key.toString())) {
            "Key $key does not exist"
        }
    }
}