package pt.isel.tds.storage

/**
 * Serializer of data to/from text.
 *
 */
interface Serializer<Data> {
    /**
     * Serializes the data to text.
     * @param data the data to serialize.
     * @return the text representation of the data.
     */
    fun serialize(data: Data): String
    /**
     * Deserializes the data from text.
     * @param text the text representation of the data.
     * @return the data.
     * @throws IllegalArgumentException if the text is not a valid representation of the data.
     */
    fun deserialize(text: String): Data
}