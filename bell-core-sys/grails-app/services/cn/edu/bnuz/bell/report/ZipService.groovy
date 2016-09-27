package cn.edu.bnuz.bell.report

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ZipService {

    byte[] zip(File[] files) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zipFile = new ZipOutputStream(baos)
        files.each { file ->
            if(file.exists()) {
                zipFile.putNextEntry(new ZipEntry(file.name))
                file.withInputStream { input -> zipFile << input }
                zipFile.closeEntry()
            } else {
                log.debug("File ${file.name} does not exists.")
            }
        }
        zipFile.finish()

        return baos.toByteArray()
    }
}
