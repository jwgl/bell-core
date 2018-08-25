package cn.edu.bnuz.bell.report

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ZipService {

    byte[] zip(File[] files) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zipFile = new ZipOutputStream(baos)
        List<String> missingFiles = []
        files.each { file ->
            if (file.exists()) {
                zipFile.putNextEntry(new ZipEntry(file.name))
                file.withInputStream { input -> zipFile << input }
                zipFile.closeEntry()
            } else {
                missingFiles << file.name
            }
        }

        if (missingFiles) {
            zipFile.putNextEntry((new ZipEntry('missing-file.txt')))
            def writer = zipFile.newPrintWriter()
            missingFiles.forEach { writer.println(it) }
            writer.flush()
            zipFile.closeEntry()
        }

        zipFile.finish()

        return baos.toByteArray()
    }
}
