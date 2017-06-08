package tim.content

import java.text.SimpleDateFormat
import java.util.Date

data class Probe(val name: String, val location: String, var nextCheck: Date) {
    internal var dt = SimpleDateFormat("yyyy-MM-dd")
    val nextCheckString: String
        get() = dt.format(nextCheck)
}
