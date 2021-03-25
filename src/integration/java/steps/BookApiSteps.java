package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import configuration.SpringIntegration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.vaidas.library.model.GlobalConstants.DEFAULT_DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookApiSteps extends SpringIntegration {

    private BookDetails bookDetails;
    private ResponseEntity<Object> lastHttpResponse;
    private Book lastResponseBodyBook;
    private Map<String, String> lastResponseErrorList;

    @Given("^the client is providing book details$")
    public void givenTheClientIsProvidingBookDetails() {
        bookDetails = new BookDetails();
    }

    @And("^provided book name is '(.+)'$")
    public void andProvidedBookNameIs(String bookName) {
        if (!isNull(bookName)) {
            bookDetails.setName(bookName);
        }
    }

    @And("^provided book author is '(.+)'$")
    public void andProvidedBookAuthorIs(String bookAuthor) {
        if (!isNull(bookAuthor)) {
            bookDetails.setAuthor(bookAuthor);
        }
    }

    @And("^provided book release date is '(.+)'$")
    public void andProvidedBookReleaseDateIs(String releaseDate) throws ParseException {
        if (!isNull(releaseDate)) {
            Date date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(releaseDate);
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
            bookDetails.setReleaseDate(localDate);
        }
    }

    @When("^the client sends a request to save the book$")
    public void whenTheClientSendsARequestToSaveTheBook() throws JsonProcessingException {
        Map<String, String> body = new HashMap<>();
        body.put("name", bookDetails.getName());
        body.put("author", bookDetails.getAuthor());
        LocalDate originalReleaseDate = bookDetails.getReleaseDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        if (originalReleaseDate != null) {
            body.put("releaseDate", originalReleaseDate.format(formatter));
        }
        lastHttpResponse = executePost("/book", body);
        assertNotNull(lastHttpResponse);

        if (HttpStatus.BAD_REQUEST.equals(lastHttpResponse.getStatusCode())) {
            lastResponseErrorList = SpringIntegration.objectMapper()
                    .convertValue(lastHttpResponse.getBody(), new TypeReference<>() {});
        } else {
            lastResponseBodyBook = SpringIntegration.objectMapper()
                    .convertValue(lastHttpResponse.getBody(), Book.class);
        }
    }

    @Then("^the client receives status code (\\d+)$")
    public void thenTheClientReceivesStatusCode(int statusCode) {
        assertEquals(statusCode, lastHttpResponse.getStatusCode().value());
    }

    @And("^added book should have name '(.+)'$")
    public void andAddedBookHasMatchingName(String bookName) {
        assertEquals(bookName, lastResponseBodyBook.getName());
    }

    @And("^added book should have author '(.+)'$")
    public void andAddedBookHasMatchingAuthor(String bookAuthor) {
        assertEquals(bookAuthor, lastResponseBodyBook.getAuthor());
    }

    @And("^added book should have release date '(.+)'$")
    public void andAddedBookHasMatchingReleaseDate(String releaseDate) throws ParseException {
        Date date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(releaseDate);
        LocalDate expectedDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        assertEquals(expectedDate, lastResponseBodyBook.getReleaseDate());
    }

    @And("^error should specify field '(.+)' with message '(.+)'$")
    public void andErrorShouldSpecifyField(String fieldName, String expectedMessage) {
        String errorMessage = lastResponseErrorList.get(fieldName);
        assertNotNull(lastResponseErrorList.get(fieldName));
        assertEquals(expectedMessage, errorMessage);
    }
    
    private boolean isNull(String s) {
        return "null".equals(s.toLowerCase(Locale.ROOT));
    }
}
