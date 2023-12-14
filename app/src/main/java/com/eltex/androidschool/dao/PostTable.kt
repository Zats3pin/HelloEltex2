package com.eltex.androidschool.dao

import kotlinx.serialization.SerialName

object PostTable {
    const val TABLE_NAME = "Posts"
    const val ID = "id"
    const val CONTENT = "context"
    const val PIBLISHED = "published"
    const val AUTHOR = "author"
    const val LIKED_BY_ME = "likeByMe"
    const val LINK = "link"
    const val STATUS = "status"
    const val TIME_STATUS = "timeStatus"
    const val PARTICIPATED_BY_ME = "participatedByMe"

    val allColunns = arrayOf(
        ID,
        CONTENT,
        PIBLISHED,
        AUTHOR,
        LIKED_BY_ME,
        LINK,
        STATUS,
        TIME_STATUS,
        PARTICIPATED_BY_ME
    )
}

 //       val allColunns = arrayOf(TABLE_NAME,ID,CONTENT,PIBLISHED,AUTHOR,LIKED_BY_ME,LINK,STATUS,TIME_STATUS,PARTICIPATED_BY_ME)
//}

