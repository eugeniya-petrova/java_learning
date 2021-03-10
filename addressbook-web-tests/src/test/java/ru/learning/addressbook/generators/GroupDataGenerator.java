package ru.learning.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.learning.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groupSet = generateGroups(count);
        if (format.equals("csv")) {
            saveAsCsv(groupSet, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(groupSet, new File(file));
        } else {
            System.out.println("unrecognized format" + format);
        }

    }

    private void saveAsCsv(List<GroupData> groupSet, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (GroupData group : groupSet) {
                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }

    private void saveAsJson(List<GroupData> groupSet, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groupSet);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groupSet = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            groupSet.add(new GroupData().withName(String.format("test %s", i))
                    .withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
        }
        return groupSet;
    }
}