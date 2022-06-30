package com.univocity.articles.csvcomparison.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.Reader;
import java.util.*;

public class JacksonParser extends AbstractParser {

	protected JacksonParser() {

		super("Jackson CSV parser");
	}

	@Override
	public void processRows(final Reader input) throws Exception {

        CsvMapper csvMapper = new CsvMapper().enable(CsvParser.Feature.WRAP_AS_ARRAY);

        MappingIterator<List<String>> iterator = csvMapper
            .readerForListOf(String.class)
            .readValues(input);

		while (iterator.hasNext()) {
			process(iterator.nextValue());
		}

	}

	@Override
	public List<String[]> parseRows(final Reader input) throws Exception {

		CsvMapper csvMapper = new CsvMapper().enable(CsvParser.Feature.WRAP_AS_ARRAY);

        MappingIterator<List<String>> iterator = csvMapper
            .readerForListOf(String.class)
            .readValues(input);

		List<String[]> values = new ArrayList<>();
		while (iterator.hasNext()) {
			values.add(iterator.nextValue().toArray(new String[0]));
		}

		return values;
	}

}
