import android.provider.BaseColumns

object DbCreateTableClass {
    const val TABLE_NAME = "entry"
       const val COL_NAME_TITLE = "title"
    const val COL_NAME_CONTENT = "val"

    const val DB_VERSION = 6
    const val DB_NAME = "Values.db"



    const val CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME(${BaseColumns._ID} INTEGER PRIMARY KEY, $COL_NAME_TITLE TEXT," +
                "$COL_NAME_CONTENT TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}