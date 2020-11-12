package de.telran.processor.service;

import de.telran.processor.entity.ImageDescriptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    public List<ImageDescriptor> readImageDescriptors(String fileName) {
        List<String> stringList = getStringFromFile(fileName);
        return stringList.stream().map(this::convertToObject).collect(Collectors.toList());
    }

    private ImageDescriptor convertToObject(String str) {
        String[] part = str.split(";");
        return new ImageDescriptor(part[0], part[1]);
    }

    private List<String> getStringFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            result = br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}