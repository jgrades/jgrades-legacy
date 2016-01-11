/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.creator;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public void zipFiles(String srcFolder, String destZipFile) throws CompressorException {
        try {
            zipFolder(srcFolder, destZipFile);
        } catch (Exception e) {
            throw new CompressorException("Zipping files failed", e);
        }
    }

    private void zipFolder(String srcFolder, String destZipFile) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream(destZipFile);
        ZipOutputStream zip = new ZipOutputStream(fileWriter);
        addFolderToZip(StringUtils.EMPTY, srcFolder, zip);
        zip.flush();
        zip.close();
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws IOException {
        File folder = new File(srcFile);
        if (folder.getName().endsWith(".bak.tmp")) {
            return;
        }

        if (flag) {
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
        } else {
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            } else {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
                in.close();
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
        File folder = new File(srcFolder);
        if (folder.list().length == 0) {
            addFileToZip(path, srcFolder, zip, true);
        } else {
            for (String fileName : folder.list()) {
                if (StringUtils.EMPTY.equals(path)) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }
}
