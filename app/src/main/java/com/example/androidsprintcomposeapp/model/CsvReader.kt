package com.example.androidsprintcomposeapp.model

import android.content.Context
import com.example.androidsprintcomposeapp.data.ComputerUserCsv

object CsvReader {
    fun loadCsv(context: Context): List<ComputerUserCsv> {
        return context.assets.open("data.csv")
            .bufferedReader()
            .useLines { lines ->
                lines
                    .drop(1)
                    .map { line ->
                        val columns = line.split(";")

                        ComputerUserCsv(
                            title = columns[0].trim(),
                            user = columns[1].trim()
                        )
                    }
                    .toList()
            }
    }
}