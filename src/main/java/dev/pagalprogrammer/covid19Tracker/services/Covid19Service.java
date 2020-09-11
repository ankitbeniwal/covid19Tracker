package dev.pagalprogrammer.covid19Tracker.services;

import dev.pagalprogrammer.covid19Tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class Covid19Service {

    private static String covid19URL = "https://raw.githubusercontent.com/datasets/covid-19/master/data/countries-aggregated.csv";
    private HashMap<String, LocationStats> allStats = new HashMap<String, LocationStats>();
    private Set<String> allCountries = new HashSet<String>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {
        HashMap<String, LocationStats> stats = new HashMap<String, LocationStats>();
        Set<String> countries = new HashSet<String>();
        HashMap<String, ArrayList<ArrayList<Integer>>> pastRecordsMap = new HashMap<String, ArrayList<ArrayList<Integer>>>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(covid19URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvReader = new StringReader(response.body());
        Iterable<CSVRecord> result = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);

        for (CSVRecord row : result) {
            LocationStats stat = new LocationStats();
            ArrayList<Integer> record = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> pastRecord;

            String country = row.get("Country");
            int confirmed = Integer.parseInt(row.get("Confirmed")), recovered =Integer.parseInt(row.get("Recovered")), deaths = Integer.parseInt(row.get("Deaths"));
            int dayCount = 0;

            countries.add(country);
            stat.setCountry(country);
            stat.setConfirmedCases(confirmed);
            stat.setRecoveredCases(recovered);
            stat.setDeaths(deaths);

            if(pastRecordsMap.containsKey(country)){
                pastRecord = pastRecordsMap.get(country);
                dayCount = (pastRecord.get(pastRecord.size() - 1)).get(0) + 1;
            }
            else
                pastRecord = new ArrayList<ArrayList<Integer>>();

            record.add(dayCount);
            record.add(confirmed);
            record.add(recovered);
            record.add(deaths);

            pastRecord.add(record);

            pastRecordsMap.put(country, pastRecord);

            stat.setPastRecord(pastRecord.stream().map(  u  ->  u.stream().mapToInt(i->i).toArray()  ).toArray(int[][]::new));
            stats.put(country, stat);
        }
        this.allStats = stats;
    }

    public HashMap<String, LocationStats> getAllStats() {
        return allStats;
    }

    public Set<String> getAllCountries() {
        return allCountries;
    }
}
