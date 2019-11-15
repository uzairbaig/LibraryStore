package com.sky.library;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookServiceImpl implements BookService {

    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private static final String REVIEW_NINE_WORD_REGEX = "^((?:\\S+\\s+){8}\\S+)";
    private static final String REMOVE_TRAILING_COMMA_REGEX = ",$";
    private static final Pattern PATTERN = compile(REVIEW_NINE_WORD_REGEX);

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(final String bookReference) throws BookNotFoundException {

        checkBookReferencePrefix(bookReference);
        return findBookByReference(bookReference);
    }

    @Override
    public String getBookSummary(final String bookReference) throws BookNotFoundException {

        checkBookReferencePrefix(bookReference);
        final Book book = findBookByReference(bookReference);
        final String review = formatBookReview(book.getReview());
        return format("[%s] %s - %s", book.getReference(), book.getTitle(), review);
    }

    private void checkBookReferencePrefix(final String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
            throw new BookNotFoundException("Exception, informing the client that book reference must begin with BOOK");
        }
    }

    private Book findBookByReference(final String bookReference) throws BookNotFoundException {
        final Book book = bookRepository.retrieveBook(bookReference);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    private String formatBookReview(final String review) {
        final Matcher matcher = PATTERN.matcher(review);
        if (matcher.find()) {
            return format("%s...", matcher.group().replaceAll(REMOVE_TRAILING_COMMA_REGEX, ""));
        }
        return review;
    }
}
