/**
 * 
 */
package de.i3mainz.openballoonmap.converter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Nikolai Bock
 *
 */
public class CSVMessageConverter extends AbstractHttpMessageConverter<Collection<Map<String, Object>>> {

	private static final String TEXT_CSV = "text/csv";

	public CSVMessageConverter() {
		super(MediaType.valueOf(TEXT_CSV));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return Iterable.class.isAssignableFrom(clazz);
	}

	@Override
	protected Collection<Map<String, Object>> readInternal(Class<? extends Collection<Map<String, Object>>> clazz,
			HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		throw new UnsupportedOperationException("readInternal() is not supported yet");
	}

	@Override
	protected void writeInternal(Collection<Map<String, Object>> t, HttpOutputMessage m)
			throws IOException, HttpMessageNotWritableException {
		m.getHeaders().add(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

		try (final Writer writer = new OutputStreamWriter(m.getBody())) {
			final CsvBeanWriter beanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
			t.forEach(i -> {
				String[] header = i.keySet().toArray(new String[t.size()]);
				try {
					beanWriter.writeHeader(header);
					beanWriter.write(i, header);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			beanWriter.flush();
		}

	}

}
