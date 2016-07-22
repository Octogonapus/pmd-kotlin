package org.jetbrains.pmdkotlin

import java.io.File
import java.io.FileWriter

fun String.asKtFile(): File {
    val file = File.createTempFile("prefix", ".kt")
    FileWriter(file).use { it.write(this) }
    return file
}
