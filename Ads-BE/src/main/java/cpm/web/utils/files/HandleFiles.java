package cpm.web.utils.files;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import cpm.web.dto.cookies.CookieDto;
import cpm.web.mapper.request.scan.ExtensionRequest;
import cpm.web.service.files.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HandleFiles {

    private static final String NAME_FILE_CSV = "data.csv";

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public void HandleFiles() {
        fileStorageService.createFiles(NAME_FILE_CSV);
    }

    public List<CookieDto> ReadCsv() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fileStorageService.loadFileAsResource(NAME_FILE_CSV).getFile()), ',');
        List<CookieDto> cookieDtoList = new ArrayList<>();

        // read line by line
        String[] record = null;

        while ((record = reader.readNext()) != null) {
            CookieDto dto = new CookieDto();
            dto.setUser_id(record[0]);
            dto.setCookie(record[1]);
            cookieDtoList.add(dto);
        }
        return cookieDtoList;
    }

    public boolean WriteCsv(ExtensionRequest cookieDto) throws Exception {
        try {
            if (!this.ReadCsv().stream().anyMatch(x-> x.getUser_id().equals(cookieDto.getSample1())
                    && x.getCookie().equals(cookieDto.getSample2()))) {
                CSVWriter csvOutput = new CSVWriter(new FileWriter(fileStorageService.loadFileAsResource(NAME_FILE_CSV).getFile(), true), ',');
                String[] lstCookies = new String[]
                        {cookieDto.getSample1(),
                                this.escapeSpecialCharacters(cookieDto.getSample2())};
                csvOutput.writeNext(lstCookies);
                csvOutput.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
