package org.dikhim.spring.rest.converter.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.dikhim.spring.rest.model.User;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class UserCsvConverter {
    public String exportList(List<User> users) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        StringWriter sw = new StringWriter();

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(sw).build();
        beanToCsv.write(users);

        sw.close();
        return sw.toString();
    }

    public List<User> importList(String csvUserList) {
        csvUserList = csvUserList.substring(csvUserList.indexOf('\n')+1);
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(User.class);
        String[] columns = new String[]{"age", "firstName", "id", "lastName"}; // the fields to bind to in your bean
        strat.setColumnMapping(columns);

        CsvToBean csv = new CsvToBean();

        csv.setMappingStrategy(strat);
        csv.setCsvReader(new CSVReader(new StringReader(csvUserList)));
        return csv.parse();
    }
}
