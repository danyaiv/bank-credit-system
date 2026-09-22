package edu.rutmiit.demo.events;

public final class RoutingKeys {
    public static final String EXCHANGE = "bank.events";

    public static final String CLIENT_CREATED = "client.created";
    public static final String CLIENT_UPDATED = "client.updated";
    public static final String CLIENT_DELETED = "client.deleted";

    public static final String LOAN_CREATED = "loan.created";
    public static final String LOAN_UPDATED = "loan.updated";

    public static final String LOAN_ENRICHED = "loan.enriched";

    public static final String ALL_EVENTS = "#";
}