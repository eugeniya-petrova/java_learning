package ru.learning.addressbook.generators;

import ru.learning.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class GroupDataGenerator {

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<GroupData> groupSet = generateGroups(count);
        save(groupSet, file);
    }

    private static void save(List<GroupData> groupSet, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (GroupData group : groupSet) {
                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }

    private static List<GroupData> generateGroups(int count) {
        List<GroupData> groupSet = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            groupSet.add(new GroupData().withName(String.format("test %s", i))
                    .withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
        }
        return groupSet;
    }
}
