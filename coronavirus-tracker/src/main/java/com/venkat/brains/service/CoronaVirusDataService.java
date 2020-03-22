package com.venkat.brains.service;

import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.venkat.brains.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private List<LocationStats> gobal = new ArrayList<>();

	private static String virus_data_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusdata() throws ParseException, Exception {
		List<LocationStats> newStates = new ArrayList<>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet request = new HttpGet(virus_data_URL);
			CloseableHttpResponse response = httpClient.execute(request);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					StringReader cvsBodyReader = new StringReader(result);
					Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsBodyReader);
					for (CSVRecord record : records) {
						LocationStats LocationStats = new LocationStats();
						LocationStats.setCountry(record.get("Country/Region"));
						LocationStats.setState(record.get("Province/State"));
						int currentDay = Integer.parseInt(record.get(record.size() - 1));
						int prevDay = Integer.parseInt(record.get(record.size() - 2));
						LocationStats.setLatesTotalCases(currentDay);
						LocationStats.setDiffromPrevDay(currentDay - prevDay);
						newStates.add(LocationStats);
					}
					this.gobal = newStates;
				}
			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
	}

	public List<LocationStats> getGobal() {
		return gobal;
	}
}
