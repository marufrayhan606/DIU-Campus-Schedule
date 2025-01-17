import com.om.diucampusschedule.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

suspend fun readTasksFromCsv(file: File): List<Task> {
    return withContext(Dispatchers.IO) {
        val tasks = mutableListOf<Task>()
        if (file.exists()) {
            FileReader(file).use { reader ->
                val csvParser = CSVParser(reader, CSVFormat.DEFAULT.withHeader())
                for (csvRecord in csvParser) {
                    val task = Task(
                        id = csvRecord.get("id").toInt(),
                        title = csvRecord.get("title"),
                        description = csvRecord.get("description"),
                        date = csvRecord.get("date"),
                        time = csvRecord.get("time"),
                        isCompleted = csvRecord.get("isCompleted").toBoolean()
                    )
                    tasks.add(task)
                }
            }
        }
        tasks
    }
}

suspend fun writeTasksToCsv(file: File, tasks: List<Task>) {
    withContext(Dispatchers.IO) {
        FileWriter(file).use { writer ->
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "title", "description", "date", "time", "isCompleted"))
            for (task in tasks) {
                csvPrinter.printRecord(
                    task.id,
                    task.title,
                    task.description,
                    task.date,
                    task.time,
                    task.isCompleted
                )
            }
            csvPrinter.flush()
        }
    }
}
