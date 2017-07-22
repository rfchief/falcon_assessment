package io.falcon.assessment.util;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class FileReader {

    private static final String DEFAULT_TEXT_ENCODING = "UTF-8";

    public File createFileWith(String fileName) {
        Preconditions.checkArgument(isValid(fileName), "File name is not valid.");
        return new File(fileName);
    }

    private boolean isValid(String fileName) {
        return StringUtils.isNotEmpty(StringUtils.deleteWhitespace(fileName));
    }

    public LineIterator createLineIterator(File file, String textEncoding) throws IOException {
        return FileUtils.lineIterator(file, textEncoding);
    }

    public LineIterator createLineIterator(String fileName) throws IOException {
        return createLineIterator(createFileWith(fileName), DEFAULT_TEXT_ENCODING);
    }

    public String getFromStringFile(String filePath) {
        String body = null;
        try {
            body = FileUtils.readFileToString(createFileWith(filePath), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

}
