package de.logerbyte.bc2t2ics.readWriteFile;

import com.sun.istack.internal.Nullable;
import de.logerbyte.bc2t2ics.ical.IcalConstants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Bc2File {
    public static final String REGEX_DATE = "\\d\\d.\\d\\d.\\d\\d\\d\\d";
    public static final String OUT_PATH = "bc2t_test_file" + File.separator + "out.ical";

    private String pathName;

    public static void createIcalFile(Bc2TaskJson[] jsonFile) {
        createNewFile(OUT_PATH);

        Path path = Paths.get(OUT_PATH);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonFile.length; i++) {
            Bc2TaskJson content = jsonFile[i];
            long startTime = content.getDtstart();
            long endTime = content.getDtstart();
            String summary = content.getTitle();
            String description = content.getDescription();

            sb.append(IcalConstants.createVcalHeader());
            sb.append(IcalConstants.createEvent(startTime, endTime, summary, description));

            if (i == jsonFile.length - 1) {
                sb.append(IcalConstants.createEnd());
            }
        }

        try {
            Files.write(path, sb.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNewFile(String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getPathName() {
        return pathName;
    }

    void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public @Nullable String readFileContent(String pathName) {
        try {
            return new String(Files. readAllBytes(Paths.get(pathName)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] splitContent(String content, String regex) {
        return content.split(regex);
    }
}
