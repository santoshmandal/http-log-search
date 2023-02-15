package com.jpmorgan.log.aggregator.httplogsearch;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;
import com.jpmorgan.log.aggregator.httplogsearch.model.ParsedLine;
import com.jpmorgan.log.aggregator.httplogsearch.service.LogLineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class HttpLogSearchApplicationTests {

	@Autowired
	private MockMvc mvc;


	@Autowired
	private LogLineService logLineService;

	private final String baseURI = "/v1/logs?";

	@Test
	public void testGetLogsSuccess() throws Exception
	{
		createLogLines();

		mvc.perform(get(baseURI)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0]").value(logLineData[3]));
	}

	@Test
	public void testGetLogWithUserSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "user=test")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0]").value(logLineData[2]));
	}

	@Test
	public void testGetLogWithUserNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "user=nouser")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithMethodSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "method=GET")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0]").value(logLineData[2]));
	}

	@Test
	public void testGetLogWithMethodNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "method=Delete")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithInvalidMethodNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "method=Random")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithCodeSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "code=200")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]").value(logLineData[1]));
	}

	@Test
	public void testGetLogWithCodeNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "code=404")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	@Test
	public void testGetLogWithInvalidCodeNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "code=fdshjdf")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithCodeAndUserSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "code=400&user=test")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]").value(logLineData[0]));
	}

	@Test
	public void testGetLogWithCodeAndUserNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "code=404&user=test")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithCodeAndMethodSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "code=200&method=POST")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]").value(logLineData[1]));
	}

	@Test
	public void testGetLogWithCodeAndMethodNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "code=404&method=POST")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithUserAndMethodSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=GET")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0]").value(logLineData[2]));
	}

	@Test
	public void testGetLogWithUserAndMethodNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=POST")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithUserAndMethodAndCodeSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=GET&code=500")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]").value(logLineData[2]));
	}

	@Test
	public void testGetLogWithUserAndMethodAndCodeNoResults() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=POST&code=200")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithUserAndMethodAndCodeInvalidInput() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=POST&code=4000")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogWithUserAndMethodAndCodeInvalidInput2() throws Exception
	{
		mvc.perform(get(baseURI + "user=test&method=POST&code=Test")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testGetLogsWithPageSuccess() throws Exception
	{
		mvc.perform(get(baseURI + "page=0")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0]").value(logLineData[3]));
	}

	@Test
	public void testGetLogsWithPageNoData() throws Exception
	{
		mvc.perform(get(baseURI + "page=1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	private final String[] logLineData = {
			"23.59.50.157 - test [18/Jul/2000 02:12:31 +0000] \"GET /photos/90 HTTP/1.0\" 400 97",
			"23.59.50.157 - test1 [10/Jul/2000 02:12:31 +0000] \"POST /photos/90 HTTP/1.0\" 200 97",
			"23.59.50.157 - test [20/Jul/2000 02:12:31 +0000] \"GET /photos/90 HTTP/1.0\" 500 97",
			"23.59.50.157 - test2 [22/Jul/2000 02:12:31 +0000] \"POST /photos/90 HTTP/1.0\" 502 97"
	};

	private void createLogLines(){
		List<LogLine> logLinesList = new ArrayList<>();
		ParsedLine parsedLine = new ParsedLine(logLineData[0]);
		LogLine logLine = new LogLine(parsedLine);
		logLinesList.add(logLine);
		ParsedLine parsedLine1 = new ParsedLine(logLineData[1]);
		LogLine logLine1 = new LogLine(parsedLine1);
		logLinesList.add(logLine1);
		ParsedLine parsedLine2 = new ParsedLine(logLineData[2]);
		LogLine logLine2 = new LogLine(parsedLine2);
		logLinesList.add(logLine2);
		ParsedLine parsedLine3 = new ParsedLine(logLineData[3]);
		LogLine logLine3 = new LogLine(parsedLine3);
		logLinesList.add(logLine3);
		logLineService.saveAll(logLinesList);

	}

}
