/**
 * 
 */
package de.i3mainz.openballoonmap.converter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import de.i3mainz.openballoonmap.model.EventBalloon;

/**
 * @author Nikolai Bock
 *
 */
public class CSVMessageConverter extends AbstractHttpMessageConverter<Collection<EventBalloon>> {

	private static final String TEXT_CSV = "text/csv";

	public CSVMessageConverter() {
		super(MediaType.valueOf(TEXT_CSV));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return Iterable.class.isAssignableFrom(clazz);
	}

	@Override
	protected Collection<EventBalloon> readInternal(Class<? extends Collection<EventBalloon>> clazz,
			HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		throw new UnsupportedOperationException("readInternal() is not supported yet");
	}

	@Override
	protected void writeInternal(Collection<EventBalloon> t, HttpOutputMessage m)
			throws IOException, HttpMessageNotWritableException {
		m.getHeaders().add(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(EventBalloon.class);
		schema = schema.withColumnSeparator(';');
		ObjectWriter myObjectWriter = mapper.writer(schema);
		myObjectWriter.writeValue(m.getBody(), t);
	}

}
