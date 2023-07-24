package com.littlepay.tripservice.util;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvHelper class is a utility for reading and writing CSV files.
 */
@Component
@Slf4j
public class CsvHelper {

    /**
     * Reads data from a CSV file and converts it into a list of objects of the specified type.
     *
     * @param filePath The path of the CSV file.
     * @param clazz The Class type of the target objects.
     * @return A list of objects after conversion.
     */
    public <T> List<T> readCsvToList(String filePath, Class<T> clazz) {
        log.info("Start reading from file {}", filePath);
        List<T> result = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withFilter(new TrimFilter())
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            result = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("End reading from file {}, got {} records", filePath, result.size());

        return result;
    }

    /**
     * Writes a list of objects into a CSV file along with the specified header.
     *
     * @param data The list of objects to be written into the CSV file.
     * @param filePath The path of the CSV file.
     * @param header The header of the CSV file.
     */
    public <T> void writeListToCSV(List<T> data, String filePath, String header) {
        log.info("Start writing to file {}", filePath);
        try (Writer writer = new FileWriter(filePath)) {

            writer.append(header).append("\n");

            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(data);

            writer.flush();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
        log.info("End writing to file {}", filePath);
    }

    /**
     * Appends a single object to the CSV file along with the specified header.
     * If the file is empty, the header will be written first.
     *
     * @param data The single object to be appended to the CSV file.
     * @param filePath The path of the CSV file.
     * @param header The header of the CSV file.
     */
    public <T> void appendToCSV(T data, String filePath, String header) {
        log.info("Start appending record to file {}", filePath);

        File file = new File(filePath);
        boolean isFileEmpty = (file.length() == 0);

        try (Writer writer = new FileWriter(filePath, true)) {

            if (isFileEmpty) {
                writeHeader(writer, header);
            }

            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(data);

            writer.flush();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
        log.info("End appending record to file {}", filePath);
    }

    /**
     * Writes the header of the CSV file.
     *
     * @param writer The file writer.
     * @param header The header of the CSV file.
     * @throws IOException IOException that occurs while writing to the file.
     */
    private void writeHeader(Writer writer, String header) throws IOException {
        writer.append(header).append(System.lineSeparator());
    }

}
