package com.emp.qscan

import androidx.annotation.StringRes
import org.json.JSONException
import org.json.JSONObject

enum class Task(@param:StringRes val label: Int, @param:StringRes val hint: Int) {
    INSPECT(R.string.task_inspect, R.string.task_inspect_hint),
    MOVE(R.string.task_move, R.string.task_move_hint),
    SHIP(R.string.task_ship, R.string.task_ship_hint),
}

data class PackageInfo(
    val id: String,
    val date: String?,
    val task: Task,
    val location: String?,
    val note: String?,
)

private val ISO_DATE = Regex("""\d{4}-\d{2}-\d{2}""")

/** Phase 1 QR payload: {"v":1,"id":"…","task":"INSPECT|MOVE|SHIP","date":"YYYY-MM-DD","loc":"…","note":"…"}. Returns null when unusable. */
fun parsePackage(raw: String): PackageInfo? {
    val json = try {
        JSONObject(raw.trim())
    } catch (_: JSONException) {
        return null
    }
    if (json.optInt("v") != 1) return null
    val id = json.text("id") ?: return null
    val task = Task.entries.firstOrNull { it.name.equals(json.optString("task"), ignoreCase = true) } ?: return null
    val date = json.text("date")?.also { if (!ISO_DATE.matches(it)) return null }
    return PackageInfo(id, date, task, json.text("loc"), json.text("note"))
}

private fun JSONObject.text(key: String): String? = if (isNull(key)) null else optString(key).trim().ifEmpty { null }
