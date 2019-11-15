package com.sky.library;

import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BookServiceTest {

    private BookService bookService;

    @Rule
    public ExpectedException expectedEx = none();

    @Before
    public void setup() {
        final BookRepositoryStub bookRepositoryStub = new BookRepositoryStub();
        bookService = new BookServiceImpl(bookRepositoryStub);
    }

    //tests for retrieveBook
    @Test
    public void shouldThrowExceptionWhenBookReferenceNotStartedWithPrefix() throws Exception {
        expectedEx.expect(BookNotFoundException.class);
        expectedEx.expectMessage("Exception, informing the client that book reference must begin with BOOK");
        bookService.retrieveBook("INVALID-TEXT");
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldThrowBookNotFoundExceptionWhenBookNotAvailableInStore() throws Exception {
        bookService.retrieveBook("BOOK-999");
    }

    @Test
    public void shouldReturnTheGruffaloBookWhenAvailableInStore() throws Exception {
        final Book book = bookService.retrieveBook("BOOK-GRUFF472");
        assertEquals("The Gruffalo book", book.getTitleWithBookPostfix());
    }

    @Test
    public void shouldReturnWinnieThePoohBookWhenAvailableInStore() throws Exception {
        final Book book = bookService.retrieveBook("BOOK-POOH222");
        assertEquals("Winnie The Pooh book", book.getTitleWithBookPostfix());
    }

    @Test
    public void shouldReturnTheWindInTheWillowsBookWhenAvailableInStore() throws Exception {
        final Book book = bookService.retrieveBook("BOOK-WILL987");
        assertEquals("The Wind In The Willows book", book.getTitleWithBookPostfix());
    }

    //tests for getBookSummary
    @Test
    public void shouldReturnExceptionMessageWhenBookReferenceNotStartedWithPrefix() throws Exception {
        expectedEx.expect(BookNotFoundException.class);
        expectedEx.expectMessage("Exception, informing the client that book reference must begin with BOOK");
        bookService.getBookSummary("INVALID-TEXT");
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldReturnBookNotFoundExceptionWhenBookNotAvailableInStore() throws Exception {
        bookService.getBookSummary("BOOK-999");
    }

    @Test
    public void shouldReturnTheGruffaloBookSummary() throws Exception {
        final String theGruffaloBookSummary = bookService.getBookSummary("BOOK-GRUFF472");
        assertEquals("[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.", theGruffaloBookSummary);
    }

    @Test
    public void shouldReturnWinnieThePoohBookSummary() throws Exception {
        final String winnieThePoohBookSummary = bookService.getBookSummary("BOOK-POOH222");
        assertEquals("[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends...", winnieThePoohBookSummary);
    }

    @Test
    public void shouldReturnTheWindInTheWillowsBookSummary() throws Exception {
        final String theWindInTheWillowsBookSummary = bookService.getBookSummary("BOOK-WILL987");
        assertEquals("[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside...", theWindInTheWillowsBookSummary);
    }

}
