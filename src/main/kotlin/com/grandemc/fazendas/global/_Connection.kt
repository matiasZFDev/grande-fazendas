package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

fun PreparedStatement.setUUID(pos: Int, uuid: UUID) {
    setBytes(pos, uuid.getBytes())
}

fun ResultSet.getUUID(column: String): UUID {
    return getBytes(column).toUUID()
}