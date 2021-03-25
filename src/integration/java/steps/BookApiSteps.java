package steps;

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
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookApiSteps extends SpringIntegration {

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    private BookDetails bookDetails;
    private ResponseEntity<Book> lastHttpResponse;
    private Book lastResponseBody;

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
            bookDetails.setReleaseDate(date);
        }
    }

    @When("^the client sends a request to save the book$")
    public void whenTheClientSendsARequestToSaveTheBook() {
        lastHttpResponse = executePost("/book", bookDetails);
        assertNotNull(lastHttpResponse);
        lastResponseBody = SpringIntegration.objectMapper()
                .convertValue(lastHttpResponse.getBody(), Book.class);
    }

    @Then("^the client receives status code (\\d+)$")
    public void thenTheClientReceivesStatusCode(int statusCode) {
        assertEquals(HttpStatus.CREATED.value(), statusCode);
    }

    @And("^added book has name '(.+)'$")
    public void andAddedBookHasMatchingName(String bookName) {
        assertEquals(bookName, lastResponseBody.getName());
    }

    @And("^added book has author '(.+)'$")
    public void andAddedBookHasMatchingAuthor(String bookAuthor) {
        assertEquals(bookAuthor, lastResponseBody.getAuthor());
    }

    @And("^added book has release date '(.+)'$")
    public void andAddedBookHasMatchingReleaseDate(String releaseDate) throws ParseException {
        Date expectedDate = simpleDateFormat.parse(releaseDate);
        assertEquals(expectedDate, lastResponseBody.getReleaseDate());
    }
    
    private boolean isNull(String s) {
        return "null".equals(s.toLowerCase(Locale.ROOT));
    }
}
