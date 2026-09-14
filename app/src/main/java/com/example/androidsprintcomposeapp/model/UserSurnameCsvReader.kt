package com.example.androidsprintcomposeapp.model

import android.content.Context
import com.example.androidsprintcomposeapp.data.UserSurnameTel

object UserSurnameCsvReader {
    fun userSurnameLoadCsv(context: Context): List<UserSurnameTel> {
        return context.assets.open("user_data.csv")
            .bufferedReader()
            .useLines { lines ->
                lines
                    .drop(1)
                    .map { line ->
                        val columns = line.split(";")

                        UserSurnameTel(
                            name = columns[0].trim(),
                            telephone = columns[1].trim(),
                            mobile = columns[2].trim(),
                            title = columns[3].trim(),
                            employeeId = columns[4].trim(),
                            office = columns[5].trim(),
                            department = columns[6].trim(),
                            email = columns[7].trim(),
                            username = columns[8].trim()
                        )
                    }
                    .toList()
            }
    }
}