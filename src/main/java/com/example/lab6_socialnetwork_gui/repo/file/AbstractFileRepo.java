package com.example.lab6_socialnetwork_gui.repo.file;

import com.example.lab6_socialnetwork_gui.domain.Entity;
import com.example.lab6_socialnetwork_gui.repo.memory.MemoryRepo;
import com.example.lab6_socialnetwork_gui.validators.Validator;
import com.example.lab6_socialnetwork_gui.validators.ValidatorException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo<ID, T extends Entity<ID>> extends MemoryRepo<ID, T> {
    protected final String fileName;

    public AbstractFileRepo(String fileName, Validator<T> validator) {
        super(validator);
        this.fileName = fileName;
    }

    protected void loadData() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> attrs = Arrays.asList(line.split(";"));
                try {
                    T entity = extractEntity(attrs);
                    super.save(entity);
                } catch (ValidatorException ve) {
                    System.out.println(ve.getMessage());
                    System.exit(1);
                }
            }
        } catch (IOException e){
            System.out.println("The file " + fileName + " can't be found\n");
            System.exit(1);
        }
    }

    protected void writeToFile() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (T e : entities) {
                bw.write(createEntityAsString(e));
                bw.newLine();
            }
        } catch (IOException e){
            throw new IOException("The file " + fileName + " can't be found\n");
        }
    }

    public abstract T extractEntity(List<String> attrs);

    protected abstract String createEntityAsString(T entity);

}
