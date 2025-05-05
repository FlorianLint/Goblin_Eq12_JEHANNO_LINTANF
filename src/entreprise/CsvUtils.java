package entreprise;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

public class CsvUtils {
    public static <T> List<T> readCsv(String path, Class<T> clazz) throws Exception {
        FileReader reader = new FileReader(path);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';') // adapte selon ton séparateur
                .build();

        return csvToBean.parse();
    }
}