package pt.isel.tds.storage

import java.nio.file.NoSuchFileException
import kotlin.io.path.*

/**
 * A storage that uses text files to store data.
 * @param baseDirectory the folder where the files are stored.
 * @param serializer the serializer to use to convert data to/from text.
 */
class TextFileStorage<Key,Data>(
    val baseDirectory: String,
    private val serializer: Serializer<Data>
): Storage<Key,Data> {
    /**
     * Create base directory if it does not exist.
     */
    init {
        with(Path(baseDirectory)) {
            if (!exists()) createDirectory()
            else check(isDirectory()) { "$name is not a directory" }
        }
    }
    /**
     * The path of the file for the given key.
     */
    private fun path(key: Key) = Path("$baseDirectory/$key.txt")

    /**
     * Creates a text file with the name key and write data as content.
     */
    override fun create(key: Key, data: Data) =
        path(key).let {
            check(!it.exists()) { "File $key already exists" }
            it.writeText(serializer.serialize(data))
        }

    /**
     * Reads the content of the text file with the name key and deserialize it to data.
     * @return the data or null if the file does not exist.
     */
    override fun read(key: Key): Data? =
        path(key).let {
            try { serializer.deserialize(it.readText()) }
            catch (e: NoSuchFileException) { null }
        }

    /**
     * Updates the content of the text file with the name key to data.
     */
    override fun update(key: Key, data: Data) =
        path(key).let {
            check(it.exists()) { "File $key does not exist" }
            it.writeText(serializer.serialize(data))
        }

    /**
     * Deletes the text file with the name key.
     */
    override fun delete(key: Key) =
        check(path(key).deleteIfExists()) { "File $key does not exist" }
}