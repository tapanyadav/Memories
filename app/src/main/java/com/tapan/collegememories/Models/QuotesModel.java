package com.tapan.collegememories.Models;

public class QuotesModel {

    String quote,quoteWriter;

    public QuotesModel() {
    }

    public QuotesModel(String quote, String quoteWriter) {
        this.quote = quote;
        this.quoteWriter = quoteWriter;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQuoteWriter() {
        return quoteWriter;
    }

    public void setQuoteWriter(String quoteWriter) {
        this.quoteWriter = quoteWriter;
    }
}
